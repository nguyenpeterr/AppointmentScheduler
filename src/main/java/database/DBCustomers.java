package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public abstract class DBCustomers {
    public static ObservableList<Customers> getAllCustomers() throws SQLException {
        ObservableList<Customers> customerList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM CUSTOMERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int customerId = rs.getInt("Customer_ID");
            String customerName = rs.getString("Customer_Name");
            String customerAddress = rs.getString("Address");
            String customerPostalCode = rs.getString("Postal_Code");
            String customerPhoneNumber = rs.getString("Phone");
            ZonedDateTime createDate = ZonedDateTime.of(rs.getTimestamp("Create_Date").toLocalDateTime(), ZoneId.systemDefault());
            String createdBy = rs.getString("Created_By");
            ZonedDateTime lastUpdate = ZonedDateTime.of(rs.getTimestamp("Last_Update").toLocalDateTime(), ZoneId.systemDefault());
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int divisionId = rs.getInt("Division_ID");
            Customers customer = new Customers(customerId, customerName, customerAddress, customerPostalCode, customerPhoneNumber,
                    createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId);
            customerList.add(customer);
        }
        return customerList;
    }
}
