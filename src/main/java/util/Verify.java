package util;

import database.*;
import exception.AppointmentOverlapException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
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

/**
 * Verify class is used to verify data from the text fields for a valid input
 * Variables are assigned with regex's taken from https://regexlib.com/
 * Please note that the current data from the database does not satisfy the regex's for address format requirement
 * given by the assignment. (i.e. data does not have the city names, assignment asks for city/province name in the format)
 * Also, the phone and postal for the UK address in the data does NOT satisfy the regex's in a proper format.
 */
public abstract class Verify {
    private static String name = "[a-zA-Z.-]+\\s[a-zA-Z.-]+";
    private static String address = "^(\\d+) ?([A-Za-z](?= ))? (.*?) ([^ ]+?) ?((?<= )APT)? ?((?<= )\\d*)?$";
    private static String intTen = "[0-9]{1,10}";
    private static String email = "\"(^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$){3,50}\"";
    private static String postal = "^[0-9]{5}([\s-]{1}[0-9]{4})?$";
    private static String phone = "((\\(\\d{3}\\)?)|(\\d{3}))([\\s-./]?)(\\d{3})([\\s-./]?)(\\d{4})" +
            "|^(\\+\\d{1,3}( )?)?(\\d{3}[ ]?)(\\d{2}[ ]?){2}\\d{2}$";

    /**
     * Checks to see if the input is within 50 chars
     * @param input String input from user
     * @param verify String to verify
     * @return True if valid, false if input is more than 50 chars
     */
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

    /**
     * Checks to see if user input is a valid name
     * @param verify String to verify
     * @return True if valid, false if invalid
     */
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

    /**
     * Checks to see if user input is a valid address
     * @param verify String to verify
     * @return True if valid, false if invalid
     */
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

    /**
     * Checks to see if user input is a valid phone number
     * @param verify String to verify
     * @return True if valid, false if invalid
     */
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

    /**
     * Checks to see if user input is a valid postal
     * @param verify String to verify
     * @return True if valid, false if invalid
     */
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

    /**
     * Checks to see if the chosen appointment times are overlapped
     * @param start Start time (original)
     * @param end End time (original)
     * @param start2 Start time (copy)
     * @param end2 End time (copy)
     * @return True if there is no overlap
     */
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

    /**
     * Checks to see if the appointment dates overlap
     * @param start Start date/time
     * @param end End date/time
     * @param id Appointment ID
     * @return Return true if it's available
     */
    public static boolean isAvailableDate(ZonedDateTime start, ZonedDateTime end, int id) {
        ObservableList<Appointments> allAppointments = FXCollections.observableArrayList();
        allAppointments = DBAppointments.getAllAppointmentsExcept(id);
        for (Appointments a : allAppointments) {
            if (isBetween(TimeZones.EST(start.toLocalDateTime()), TimeZones.EST(end.toLocalDateTime()), TimeZones.EST(a.getStartDateTimeLocal()), TimeZones.EST(a.getEndTimeZoned().toLocalDateTime()))) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Appointment Overlap");
                alert.show();
                throw new AppointmentOverlapException(new RuntimeException());
            }
        }
        return true;
    }

    /**
     * Checks to see if the input integer is positive
     * @param verify String to verify
     * @return True if valid
     */
    public static boolean validInt(String verify) {
        try {
            Integer.parseInt(verify);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Input must be a positive integer.");
            return false;
        }
        return true;
    }

    /**
     * Checks to see if the user ID is valid
     * @param verify User ID
     * @return True if valid
     */
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

    /**
     * Checks to see if there is a valid customer
     * @param verify Customer ID
     * @return True if valid
     */
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


    /**
     * Checks to see if the chosen appointment times are within business hours
     * @param startTime Start time (local)
     * @param endTime End time (local)
     * @param startEST Start time (EST)
     * @param endEST End time (EST)
     * @return True if valid
     */
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
            alert.setContentText("Start date/time must be before end date/time.");
            alert.show();
        }
        return false;
    }
}

