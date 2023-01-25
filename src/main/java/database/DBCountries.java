package database;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import model.Countries;


import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * DBCountries class handles the SQL statements for Countries and data handling between the database and the application
 *
 */
public abstract class DBCountries {
    /**
     * Executes the SQL statement to get all from Countries table
     * @return List of countries
     * @throws SQLException
     */
    public static ObservableList<Countries> getAllCountries() throws SQLException {
        ObservableList<Countries> countryList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM COUNTRIES";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int countryId = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            ZonedDateTime createDate = ZonedDateTime.of(rs.getTimestamp("Create_Date").toLocalDateTime(), ZoneId.systemDefault());
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            Countries countries = new Countries(countryId, countryName, createDate, createdBy, lastUpdate, lastUpdatedBy);
            countryList.add(countries);
        }
        return countryList;
    }

    /**
     * Executes the SQL statement to get all from Contacts table
     * @return List of country names only
     * @throws SQLException
     */
    public static ObservableList<String> getAllCountryNames() throws SQLException {
        ObservableList<String> countryList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM COUNTRIES";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            String countryName = rs.getString("Country");
            countryList.add(countryName);
        }
        return countryList;
    }


}
