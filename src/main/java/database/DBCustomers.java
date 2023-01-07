package database;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import model.Customers;
import util.TimeZones;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public abstract class DBCustomers {
    public static ObservableList<Customers> resultSet(ResultSet rs) {
        ObservableList<Customers> customerList = FXCollections.observableArrayList();
        try {
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

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customerList;
    }

        public static ObservableList<Customers> getAllCustomers() throws SQLException {
        String sql = "SELECT * FROM CUSTOMERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return resultSet(rs);
    }

    public static ObservableList<Customers> getAllCustomers(int id) throws SQLException {
        String sql = "SELECT * FROM CUSTOMERS WHERE Customer_ID = " + id;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
                return resultSet(rs);
    }

    public static void addCustomer(Customers customer) {
        if(customer != null) {
            try {
                String sql = "INSERT INTO CUSTOMERS (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date " +
                        "Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement ps = JDBC.connection.prepareStatement(sql);
                ps.setInt(1, customer.getCustomerId());
                ps.setString(2, customer.getCustomerName());
                ps.setString(3, customer.getCustomerAddress());
                ps.setString(4, customer.getCustomerPostalCode());
                ps.setString(5, customer.getCustomerPhoneNumber());
                ps.setTimestamp(6, TimeZones.timestamp(customer.getCreateDate()));
                ps.setString(7, customer.getCreatedBy());
                ps.setTimestamp(8, TimeZones.timestamp(customer.getLastUpdate()));
                ps.setString(9, customer.getLastUpdatedBy());
                ps.setInt(10, customer.getDivisionId());
                ps.execute();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void removeCustomer(int id) {
        try {
            String sql = "DELETE FROM CUSTOMERS WHERE Customer_ID = " + id;
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateCustomer(Customers customer) {
        try {
            String sql = "UPDATE CUSTOMERS SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Create_Date = ? " +
                    "Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ?" + customer.getCustomerId();
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getCustomerAddress());
            ps.setString(3, customer.getCustomerPostalCode());
            ps.setString(4, customer.getCustomerPhoneNumber());;
            ps.setTimestamp(5, TimeZones.timestamp(customer.getCreateDate()));
            ps.setString(6, customer.getCreatedBy());
            ps.setTimestamp(7, TimeZones.timestamp(customer.getLastUpdate()));
            ps.setString(8, customer.getLastUpdatedBy());
            ps.setInt(9, customer.getDivisionId());
            ps.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
