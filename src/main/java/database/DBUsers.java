package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public abstract class DBUsers {
    public static ObservableList<Users> getAllUsers() throws SQLException {
        ObservableList<Users> userNameList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM USERS";
        PreparedStatement ps = JDBC.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int userId = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String userPassword = rs.getString("Password");
            ZonedDateTime createDate = ZonedDateTime.of(rs.getTimestamp("Create_Date").toLocalDateTime(), ZoneId.systemDefault());
            String createdBy = rs.getString("Created_By");
            Timestamp lastUpdate = rs.getTimestamp("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            Users users = new Users(userId, userName, userPassword, createDate, createdBy, lastUpdate, lastUpdatedBy);
            userNameList.add(users);

        }
        return userNameList;
    }

    public static boolean login(String inputUserName, String inputPassword) {
        try {
            String sql = "SELECT * FROM USERS WHERE USER_NAME = '" + inputUserName + "' AND PASSWORD = '" + inputPassword + "'";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            rs.next();
            if (rs.getString("User_Name").equals(inputUserName) && rs.getString("Password").equals(inputPassword)) {
                return true;
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Login Alert");
        alert.setContentText("Incorrect username or password!");
        alert.show();
        return false;
    }


}
