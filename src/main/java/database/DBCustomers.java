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
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * DBCustomers class handles the SQL statements for Customers and data handling between the database and the application
 *
 */
public abstract class DBCustomers {
    /**
     * Creates a list of customers, runs through the database and grabs the data based on column name
     * @param rs Takes the prepared statement execute query as parameter
     * @return returns the list of customers
     */
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
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
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

    /**
     * Executes the SQL statement to get all from the customers table
     * @return List of customers
     * @throws SQLException
     */
        public static ObservableList<Customers> getAllCustomers() throws SQLException {
        String sql = "SELECT * FROM CUSTOMERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return resultSet(rs);
    }

    /**
     * Executes the SQL statement to get all customers based on given customer ID
     * @param id Customer ID
     * @return List of customers based on customer ID
     * @throws SQLException
     */
    public static ObservableList<Customers> getAllCustomers(int id) throws SQLException {
        String sql = "SELECT * FROM CUSTOMERS WHERE Customer_ID = " + id;
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
                return resultSet(rs);
    }

    /**
     * Executes the SQL statement to get all customers based on appointment type
     * Inner joins the apartment table to link appointments to customer by customer ID
     * @param type Appointment type
     * @return List of Customers that match the given appointment type
     * @throws SQLException
     */
    public static ObservableList<Customers> getCustomerByType(String type) throws SQLException {
        String sql = "SELECT Customers.*, Appointments.Type FROM CUSTOMERS INNER JOIN APPOINTMENTS ON Customers.Customer_ID = Appointments.Customer_ID WHERE Appointments.Type = " + '"' + type + '"';
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return resultSet(rs);
    }

    /**
     * Executes the SQL statement to get all customers by month
     * Inner joins appointment table
     * @param month Month to filter the customers
     * @return List of customers based on given month
     * @throws SQLException
     */
    public static ObservableList<Customers> getCustomerByMonth(String month) throws SQLException {
        String sql = "SELECT Customers.*, Appointments.*, MONTHNAME(Appointments.Start) as month FROM CUSTOMERS INNER JOIN APPOINTMENTS ON Customers.Customer_ID = Appointments.Customer_ID WHERE UPPER(MONTHNAME(Appointments.Start)) = " + '"' + month + '"';
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return resultSet(rs);
    }

    /**
     * Executes the SQL statement to get all customers based on country
     * Inner joins the FirstLevelDivisions table to customers to match Customer ID
     * Inner joins the FirstLevelDivisions table to Countries table to match by Country ID
     * @param country Given country to filter
     * @return List of customers based on given country
     * @throws SQLException
     */
    public static ObservableList<Customers> getCustomerByCountry(String country) throws SQLException {
        String sql = "SELECT Customers.*, Countries.*, First_Level_Divisions.* FROM CUSTOMERS INNER JOIN First_Level_Divisions ON Customers.Division_ID = First_Level_Divisions.Division_ID INNER JOIN Countries ON First_Level_Divisions.Country_ID = Countries.Country_ID WHERE Countries.Country = " + '"' + country + '"';
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return resultSet(rs);
    }

    /**
     * Adds a new customer to the database
     * @param customer New customer object
     */
    public static void addCustomer(Customers customer) {
        if(customer != null) {
            try {
                String sql = "INSERT INTO CUSTOMERS (Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, " +
                        "Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (?,?,?,?,?,?,?,?,?,?)";
                PreparedStatement ps = JDBC.connection.prepareStatement(sql);
                ps.setInt(1, customer.getCustomerId());
                ps.setString(2, customer.getCustomerName());
                ps.setString(3, customer.getCustomerAddress());
                ps.setString(4, customer.getCustomerPostalCode());
                ps.setString(5, customer.getCustomerPhoneNumber());
                ps.setTimestamp(6, TimeZones.timestamp(customer.getCreateDate()));
                ps.setString(7, "admin");
                ps.setTimestamp(8, customer.getLastUpdate());
                ps.setString(9, "admin");
                ps.setInt(10, customer.getDivisionId());
                ps.execute();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Removes the customer from the database
     * @param id Customer ID
     */
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

    /**
     * Updates an existing customer in the database
     * @param customer Customer to remove
     */
    public static void updateCustomer(Customers customer) {
        try {
            String sql = "UPDATE CUSTOMERS SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Create_Date = ?, " +
                    "Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = "+ customer.getCustomerId();
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, customer.getCustomerName());
            ps.setString(2, customer.getCustomerAddress());
            ps.setString(3, customer.getCustomerPostalCode());
            ps.setString(4, customer.getCustomerPhoneNumber());;
            ps.setTimestamp(5, TimeZones.timestamp(customer.getCreateDate()));
            ps.setString(6, "admin");
            ps.setTimestamp(7, customer.getLastUpdate());
            ps.setString(8, "admin");
            ps.setInt(9, customer.getDivisionId());
            ps.execute();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Auto-generates a customer ID
     * @return Returns a unique customer ID
     */
    public static int generateCustomerId() {
        int genCustId = -1;
        try {
            String sql = "SELECT MAX(Customer_ID) as MAX_CUSTOMER_ID FROM CUSTOMERS";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                genCustId = rs.getInt("MAX_CUSTOMER_ID") + 1;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return genCustId;
    }
}
