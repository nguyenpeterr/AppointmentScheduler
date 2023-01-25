package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.FirstLevelDivisions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Hashtable;

/**
 * DBFirstLevelDivisions class handles the SQL statements for First Level Divisions and data handling between the database and the application
 *
 */
public abstract class DBFirstLevelDivisions {

    /**
     * Creates a list of divisions, runs through the database and grabs the data based on column name
     * @param rs Takes the prepared statement execute query as parameter
     * @return Returns a list of divisions
     */
    public static ObservableList<FirstLevelDivisions> resultSet(ResultSet rs) {
        ObservableList<FirstLevelDivisions> divisionsList = FXCollections.observableArrayList();
        try {
            while (rs.next()) {
                int divisionId = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                ZonedDateTime createDate = ZonedDateTime.of(rs.getTimestamp("Create_Date").toLocalDateTime(), ZoneId.systemDefault());
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdate = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int countryId = rs.getInt("Country_ID");
                FirstLevelDivisions fld = new FirstLevelDivisions(divisionId, division, createDate, createdBy, lastUpdate, lastUpdatedBy, countryId);
                divisionsList.add(fld);

            }
        }
            catch (SQLException e){
                e.printStackTrace();
            }
            return divisionsList;
    }

    /**
     * Executes the SQL statement to get all from the First_Level_Divisions table
     * @return List of divisions
     * @throws SQLException
     */
    public static ObservableList<FirstLevelDivisions> getAllDivisions() throws SQLException {
        String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        return resultSet(rs);
    }

    /**
     * Executes the SQL statement to get all US divisions
     * @return Returns a list of US states
     */
    public static ObservableList<String> getUSDivision() {
        ObservableList<String> dList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE Division_ID BETWEEN 1 AND 54";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String divisionUS = rs.getString("Division");
                dList.add(divisionUS);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return dList;
    }

    /**
     * Executes the SQL stateent to get all Canadian divisions
     * @return Returns a list of Canadian provinces
     */
    public static ObservableList<String> getCADivision() {
        ObservableList<String> dList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE Division_ID BETWEEN 60 AND 72";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String divisionCA = rs.getString("Division");
                dList.add(divisionCA);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return dList;
    }

    /**
     * Executes the SQL statement to grab all UK divisions
     * @return Returns a list of UK regions
     */
    public static ObservableList<String> getUKDivision() {
        ObservableList<String> dList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS WHERE Division_ID BETWEEN 101 AND 104";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String divisionUK = rs.getString("Division");
                dList.add(divisionUK);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return dList;
    }

    /**
     * Creates a hash table to match division ID to division
     * @return Hash table of divisions
     */
    public static Hashtable<Integer, String> hashAllDivisions() {
        Hashtable<Integer, String> divisionHashTable = new Hashtable<>();
        try {
            String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("Division_ID");
                String division = rs.getString("Division");

                divisionHashTable.put(id, division);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divisionHashTable;
    }

    /**
     * Creates a hash table to match division ID to division
     * @return Hash table of division IDs
     */
    public static Hashtable<String, Integer> hashAllDivisionIds() {
        Hashtable<String, Integer> divisionHashTable = new Hashtable<>();
        try {
            String sql = "SELECT * FROM FIRST_LEVEL_DIVISIONS";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String division = rs.getString("Division");
                int id = rs.getInt("Division_ID");

                divisionHashTable.put(division, id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return divisionHashTable;
    }
}
