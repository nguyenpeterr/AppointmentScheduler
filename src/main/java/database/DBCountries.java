package database;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;


import java.sql.*;

public abstract class DBCountries {
    public static ObservableList<String> getAllUserNames() throws SQLException {
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
