package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DBFirstLevelDivisions {
    public static ObservableList<String> getAllDivisions() throws SQLException {
        ObservableList<String> divisionsList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int divisionId = rs.getInt("Division_ID");
            String division = rs.getString("Division");
//            ZonedDateTime createDate = ZonedDateTime.of(rs.getTimestamp("Create_Date").toLocalDateTime(), ZoneId.systemDefault());

        }
        return divisionsList;
    }
}
