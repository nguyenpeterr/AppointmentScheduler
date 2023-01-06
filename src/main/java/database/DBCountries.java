package database;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;


import java.sql.*;

public abstract class DBCountries {
    public static ObservableList<String> getAllCountries() throws SQLException {
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
