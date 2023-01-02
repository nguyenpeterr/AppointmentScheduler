package database;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import model.Country;


import javax.xml.transform.Result;
import java.sql.*;
import java.time.ZonedDateTime;

public class DBCountries {
    public static ObservableList<String> getAllCountries() throws SQLException {
        ObservableList<String> clist = FXCollections.observableArrayList();

        String sql = "SELECT * FROM COUNTRIES";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            String countryName = rs.getString("Country");
            clist.add(countryName);
        }
        return clist;
    }


}
