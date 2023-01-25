package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Appointments;
import model.Customers;
import util.TimeZones;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * DBAppointments class handles the SQL statements and data handling for Appointments between the database and the application
 *
 */
public abstract class DBAppointments {
    /**
     * Formats the time to a pattern
     */
    private static final DateTimeFormatter zdtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
    private static final DateTimeFormatter ldtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Creates a list of appointments, runs through the database and grabs the data based on column name
     * @param rs Takes the prepared statement execute query as parameter
     * @return returns the list of appointments
     */
    public static ObservableList<Appointments> resultSet(ResultSet rs) {
        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
        try {
            while (rs.next()) {
                Appointments newAppointment = new Appointments();
                newAppointment.setAppointmentId(rs.getInt("Appointment_ID"));
                newAppointment.setTitle(rs.getString("Title"));
                newAppointment.setDescription(rs.getString("Description"));
                newAppointment.setLocation(rs.getString("Location"));
                newAppointment.setType(rs.getString("Type"));

                String startDateTime = rs.getString("Start") + " UTC";
                String endDateTime = rs.getString("End") + " UTC";
                ZonedDateTime startZDT = ZonedDateTime.parse(startDateTime, zdtFormatter);
                ZonedDateTime endZDT = ZonedDateTime.parse(endDateTime, zdtFormatter);
                newAppointment.setStart(startZDT);
                newAppointment.setEnd(endZDT);
                newAppointment.setCustomerId(rs.getInt("Customer_ID"));
                newAppointment.setUserId(rs.getInt("User_ID"));
                newAppointment.setContactId(rs.getInt("Contact_ID"));
                String createDate = rs.getString("Create_Date") + " UTC";
                ZonedDateTime createZDT = ZonedDateTime.parse(createDate, zdtFormatter);
                newAppointment.setCreateDate(createZDT);
                newAppointment.setCreatedBy(rs.getString("Created_By"));
                newAppointment.setLastUpdate(rs.getTimestamp("Last_Update"));
                newAppointment.setLastUpdatedBy(rs.getString("Last_Updated_By"));
                appointmentsList.add(newAppointment);

            }
            } catch (SQLException e) {
                e.printStackTrace();
        }
            return appointmentsList;
    }

    /**
     * Executes the prepared SQL statement of selecting all from the appointments table
     * @return The list of appointments
     * @throws SQLException
     */
    public static ObservableList<Appointments> getAllAppointments() throws SQLException {

        String sql = "SELECT * FROM APPOINTMENTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return resultSet(rs);
    }

    /**
     * Executes the prepared SQL statement of selecting all from the appointments table where customer ID matches
     * the given customer ID
     * @param id Customer ID
     * @return Returns the list of appointments matching the given customer ID
     * @throws SQLException
     */
    public static ObservableList<Appointments> getCustomerAppts(int id) throws SQLException {
        String sql = "SELECT * FROM APPOINTMENTS WHERE Customer_ID = " + id;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return resultSet(rs);
    }

    /**
     * Executes the prepared SQL statement to grab appointments by date
     * @return List of appointments by date
     * @throws SQLException
     */
    public static ObservableList<Appointments> getMonthAppts() throws SQLException {
        String sql = "SELECT * FROM APPOINTMENTS WHERE Start_Date= ";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return resultSet(rs);
    }

    /**
     * Executes the SQL statement to get all appointments by type
     * @param type Appointment Type
     * @return List of appointments by type
     * @throws SQLException
     */
    public static ObservableList<Appointments> getSortedAppointments(String type) throws SQLException {
        String sql = "SELECT * FROM APPOINTMENTS WHERE Type = " + "'" + type + "'";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return resultSet(rs);
    }

    /**
     * Executes the SQL statement to get all appointments by Contact ID
     * @param id Contact ID
     * @return List of appointments by contact ID
     * @throws SQLException
     */
    public static ObservableList<Appointments> getContactAppts(int id) throws SQLException {
        String sql = "SELECT * APPOINTMENTS WHERE Contact_ID = " + id;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return resultSet(rs);
    }

    /**
     * Removes the selected appointment matching the appointment ID
     * @param appointmentId Appointment ID
     * @throws SQLException
     */
    public static void removeAppointment(int appointmentId) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID = " + appointmentId;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ps.execute();
    }


    /**
     * Adds a new appointment into the database
     * @param appointment Appointment to add
     */
    public static void addAppointment(Appointments appointment) {
        if(appointment != null) {
            try {
                String sql = "INSERT INTO APPOINTMENTS (Appointment_ID, Title, Description, Location, Type, Start, End, Create_Date, " +
                        "Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement ps = JDBC.connection.prepareStatement(sql);
                ps.setInt(1, appointment.getAppointmentId());
                ps.setString(2, appointment.getTitle());
                ps.setString(3,appointment.getDescription());
                ps.setString(4, appointment.getLocation());
                ps.setString(5, appointment.getType());
                ZonedDateTime startZoned = appointment.getStartTimeZoned();
                ZonedDateTime endZoned = appointment.getEndTimeZoned();
                startZoned = startZoned.withZoneSameInstant(ZoneId.of("UTC"));
                endZoned = endZoned.withZoneSameInstant(ZoneId.of("UTC"));
                String start = startZoned.toLocalDateTime().format(ldtFormatter);
                String end = endZoned.toLocalDateTime().format(ldtFormatter);
                ps.setString(6, start);
                ps.setString(7, end);
                ps.setTimestamp(8, TimeZones.timestamp(appointment.getCreateDate()));
                ps.setString(9, "admin");
                ps.setTimestamp(10, appointment.getLastUpdate());
                ps.setString(11, "admin");
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

    /**
     * Updates an existing appointment in the database
     * @param appointment Appointment to update
     */
    public static void updateAppointment(Appointments appointment) {
        try{
            String sql = "UPDATE APPOINTMENTS SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, " + "" +
                    "Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = " +
                    appointment.getAppointmentId();
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, appointment.getTitle());
            ps.setString(2, appointment.getDescription());
            ps.setString(3, appointment.getLocation());
            ps.setString(4, appointment.getType());
            ZonedDateTime startZoned = appointment.getStartTimeZoned();
            ZonedDateTime endZoned = appointment.getEndTimeZoned();
            startZoned = startZoned.withZoneSameInstant(ZoneId.of("UTC"));
            endZoned = endZoned.withZoneSameInstant(ZoneId.of("UTC"));
            String start = startZoned.toLocalDateTime().format(ldtFormatter);
            String end = endZoned.toLocalDateTime().format(ldtFormatter);
            ps.setString(5, start);
            ps.setString(6, end);
            ps.setTimestamp(7, TimeZones.timestamp(appointment.getCreateDate()));
            ps.setString(8, "admin");
            ps.setTimestamp(9, appointment.getLastUpdate());
            ps.setString(10, "admin");
            ps.setInt(11, appointment.getCustomerId());
            ps.setInt(12, appointment.getUserId());
            ps.setInt(13, appointment.getContactId());
            ps.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Removes the appointment by customer ID
     * @param appointment Appointment to remove
     * @param customerId Customer ID
     */
    public static void removeCustomerAppts(ObservableList<Appointments> appointment, int customerId) {
        try {
            for (Appointments a : appointment) {
                if(a.getCustomerId() == customerId) {
                    removeAppointment(a.getCustomerId());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Successful");
                    alert.setContentText("Successfully removed!");
                    alert.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Failed");
                    alert.setContentText("Unable to remove!");
                    alert.show();
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Executes the SQL statement to get all appointments except for a specific appointment
     * @param id Appointment ID to not grab
     * @return List of appointments without the given appointment ID
     */
    public static ObservableList<Appointments> getAllAppointmentsExcept(int id) {
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM APPOINTMENTS WHERE Appointment_ID != " + id;
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            rs = ps.executeQuery();
        }
        catch(SQLException sqlE) {
            sqlE.printStackTrace();
        }
        return resultSet(rs);
    }

    /**
     * Auto-generates the appointment ID
     * @return Returns a unique appointment ID
     */
    public static int generateAppointmentId() {
        int genApptId = -1;
        try {
            String sql = "SELECT MAX(Appointment_ID) as MAX_APPOINTMENT_ID FROM APPOINTMENTS";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                genApptId = rs.getInt("MAX_APPOINTMENT_ID") + 1;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return genApptId;
    }


}
