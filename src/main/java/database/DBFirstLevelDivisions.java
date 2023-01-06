package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public abstract class DBFirstLevelDivisions {
    public static ObservableList<FirstLevelDivisions> getAllDivisions() throws SQLException {
        ObservableList<FirstLevelDivisions> divisionsList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int divisionId = rs.getInt("Division_ID");
            String division = rs.getString("Division");
            ZonedDateTime createDate = ZonedDateTime.of(rs.getTimestamp("Create_Date").toLocalDateTime(), ZoneId.systemDefault());
            String createdBy = rs.getString("Created_By");
            ZonedDateTime lastUpdate = ZonedDateTime.of(rs.getTimestamp("Last_Update").toLocalDateTime(), ZoneId.systemDefault());
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int countryId = rs.getInt("Country_ID");
            FirstLevelDivisions fld = new FirstLevelDivisions(divisionId, division, createDate, createdBy, lastUpdate, lastUpdatedBy, countryId);
            divisionsList.add(fld);

        }
        return divisionsList;
    }
}
