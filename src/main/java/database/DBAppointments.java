package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public abstract class DBAppointments {
    public static ObservableList<Appointments> getAllAppointments() throws SQLException {
        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM APPOINTMENTS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
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
        return appointmentsList;
    }
}
