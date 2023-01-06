package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableFloatArray;
import javafx.collections.ObservableList;
import model.Appointments;
import util.TimeZones;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public abstract class DBAppointments {

    public static ObservableList<Appointments> resultSet(ResultSet rs) {
        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
        try {
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                ZonedDateTime start = ZonedDateTime.of(rs.getTimestamp("Start").toLocalDateTime(), ZoneId.systemDefault());
                ZonedDateTime end = ZonedDateTime.of(rs.getTimestamp("End").toLocalDateTime(), ZoneId.systemDefault());
                ZonedDateTime createDate = ZonedDateTime.of(rs.getTimestamp("Create_Date").toLocalDateTime(), ZoneId.systemDefault());
                String createdBy = rs.getString("Created_By");
                ZonedDateTime lastUpdate = ZonedDateTime.of(rs.getTimestamp("Last_Update").toLocalDateTime(), ZoneId.systemDefault());
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                Appointments appointment = new Appointments(appointmentId, title, description, location, type, start, end,
                        createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId);
                appointmentsList.add(appointment);
            }
            } catch (SQLException e) {
                e.printStackTrace();
        }
            return appointmentsList;
    }
    public static ObservableList<Appointments> getAllAppointments() throws SQLException {

        String sql = "SELECT * FROM APPOINTMENTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return resultSet(rs);
    }

    public static ObservableList<Appointments> getAllAppointments(int id) throws SQLException {
        String sql = "SELECT * APPOINTMENTS WHERE Appointment_ID = " + id;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return resultSet(rs);
    }

    public static ObservableList<Appointments> getCustomerAppts(int id) throws SQLException {
        String sql = "SELECT * APPOINTMENTS WHERE Customer_ID = " + id;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return resultSet(rs);
    }

    public static ObservableList<Appointments> getContactAppts(int id) throws SQLException {
        String sql = "SELECT * APPOINTMENTS WHERE Contact_ID = " + id;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return resultSet(rs);
    }

    public static void removeAppointment(int appointmentId) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID = " + appointmentId;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.execute();
    }

    public static void addAppointment(Appointments appointment) {
        if(appointment != null) {
            try {
                String sql = "INSERT INTO APPOINTMENTS (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date " +
                        "Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement ps = JDBC.connection.prepareStatement(sql);
                ps.setInt(1, appointment.getAppointmentId());
                ps.setString(2, appointment.getTitle());
                ps.setString(3,appointment.getDescription());
                ps.setString(4, appointment.getLocation());
                ps.setString(5, appointment.getType());
                ps.setTimestamp(6, TimeZones.timestamp(appointment.getStart()));
                ps.setTimestamp(7, TimeZones.timestamp(appointment.getEnd()));
                ps.setTimestamp(8, TimeZones.timestamp(appointment.getCreateDate()));
                ps.setString(9, appointment.getCreatedBy());
                ps.setTimestamp(10, TimeZones.timestamp(appointment.getLastUpdate()));
                ps.setString(11, appointment.getLastUpdatedBy());
                ps.setInt(12, appointment.getCustomerId());
                ps.setInt(13, appointment.getUserId());
                ps.setInt(14, appointment.getContactId());
                ps.execute();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


}
