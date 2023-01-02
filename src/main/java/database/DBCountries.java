package database;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import model.Country;


import javax.xml.transform.Result;
import java.sql.*;

public class DBCountries {
    public static ObservableList<Country> getAllCountries() throws SQLException {
        ObservableList<Country> clist = FXCollections.observableArrayList();

        String sql = "SELECT * FROM COUNTRIES";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            int countryId = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            Country country = new Country(countryId, countryName);
            clist.add(country);
        }
        return clist;
    }
}
