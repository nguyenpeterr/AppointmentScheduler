package util;

import database.*;
import exception.AppointmentOverlapException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.*;

import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public abstract class Verify {
    private static String name = "[a-zA-Z]+\\s[a-zA-Z]+";
    private static String address = "^(\\d+) ?([A-Za-z](?= ))? (.*?) ([^ ]+?) ?((?<= )APT)? ?((?<= )\\d*)?$";
    private static String intTen = "[0-9]{1,10}";
    private static String email = "\"(^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$){3,50}\"";
    private static String phone = "^(\\+\\d{1,3}( )?)?((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?){2}\\d{3}$"
            + "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";
    private static String postal = "^\\d{5}-\\d{4}|\\d{5}|[A-Z]\\d[A-Z] \\d[A-Z]\\d$(([A-Z]{1,2}[0-9][0-9A-Z]?)\\ ([0-9][A-Z]{2}))|(GIR\\ 0AA)";

    public static boolean isVarCharFifty(String input, String verify) {
        Pattern p = Pattern.compile("^.{1,50}$");
        Matcher m = p.matcher(verify);
        if(m.matches()) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText(input + " must be in between 1 and 50 characters!");
            alert.show();
            return false;
        }
    }

    public static boolean isVarCharOneHundred(String input, String verify) {
        Pattern p = Pattern.compile("^.{1,100}$");
        Matcher m = p.matcher(verify);
        if(m.matches()) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText(input + " must be in between 1 and 100 characters!");
            alert.show();
            return false;
        }
    }

    public static boolean validName(String verify) {
        Pattern p = Pattern.compile(name, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(verify);
        if(m.matches()) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText("Name must have two or more characters separated by a space!");
            alert.show();
            return false;
        }
    }

    public static boolean validAddress(String verify) {
        Pattern p = Pattern.compile(address, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(verify);
        if(m.matches()) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText("Address must be in the format of:\n U.S. Address; 123 ABC Street, White Pains \n" +
                    "Canadian Address: 123 ABC Street, Newmarket \n" +
                    "UK Address: 123 ABC Street, Greenwich, London");
            alert.show();
            return false;
        }
    }

    public static boolean validPhone(String verify) {
        Pattern p = Pattern.compile(phone);
        Matcher m = p.matcher(verify);
        if(m.matches()) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText("Please enter a valid phone number!");
            alert.show();
            return false;
        }
    }

    public static boolean validEmail(String verify) {
        Pattern p = Pattern.compile(email, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(verify);
        if(m.matches()) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText("Email must in the format abc123@sample.com");
            alert.show();
            return false;
        }
    }

    public static boolean validPostal(String verify) {
        Pattern p = Pattern.compile(postal, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(verify);
        if(m.matches()) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText("Please enter a valid Postal Code for your Country.");
            alert.show();
            return false;
        }
    }

    public static boolean lessThanTen(String input, String verify) {
        Pattern p = Pattern.compile(intTen);
        Matcher m = p.matcher(verify);
        if(m.matches()) {
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Input Error");
            alert.setContentText("Input must be 10 numbers or less.");
            alert.show();
            return false;
        }
    }

    public static boolean isBetween(ZonedDateTime start, ZonedDateTime end, ZonedDateTime start2, ZonedDateTime end2) {
        if (start.isAfter(start2) && start.isBefore(end2)) {
            return true;
        }
        if (end.isAfter(start2) && end.isBefore(end2)) {
            return true;
        }
        if (start.isEqual(start2) || start.isEqual(end2)) {
            return true;
        }
        if (end.equals(end2) || end.equals(start2)) {
            return true;
        }
        return false;
    }

    public static boolean isAvailableDate(ZonedDateTime start, ZonedDateTime end, int id) {
        ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
        allAppointments = DBAppointments.getAllAppointmentsExcept(id);
        for (Appointments a : allAppointments) {
            if (isBetween(TimeZones.EST(start), TimeZones.EST(end), TimeZones.EST(a.getStart()), TimeZones.EST(a.getEnd()))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Appointment Overlap");
                alert.show();
                throw new AppointmentOverlapException(new RuntimeException());
            }
        }
        return true;
    }

    public static boolean validInt(String verify) {
        try {
            Integer.parseInt(verify);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Input must be a positive integer.");
            alert.show();
            return false;
        }
        return true;
    }

    public static boolean validUserId(int verify) {
        ObservableList<Users> user = FXCollections.observableArrayList();
        List<Integer> userId = new ArrayList<>();
        try {
            user = DBUsers.getAllUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Users u : user) {
            userId.add(u.getUserId());
        }
        if(userId.contains(verify)) {
            return true;
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText("User ID does not exist in the database.");
        return false;
    }

    public static boolean validCustomer(int verify) {
        ObservableList<Customers> customer = FXCollections.observableArrayList();
        List<Integer> customerId = new ArrayList<>();
        try {
            customer = DBCustomers.getAllCustomers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for(Customers c : customer) {
            customerId.add(c.getCustomerId());
        }
        if(customerId.contains(verify)) {
            return true;
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText("Customer ID does not exist in the database.");
        return false;
    }



    public static boolean validTime(ZonedDateTime startTime, ZonedDateTime endTime, LocalTime startEST,
                                      LocalTime endEST)
    {
        if (startTime.isBefore(endTime)) {
            if (startEST.getHour() >= 8 && endEST.getHour() <= 22) {
                return true;
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please choose an appointment time between 0800 and 2200 EST.");
                alert.show();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Start time must be before end time.");
            alert.show();
        }
        return false;
    }
}

