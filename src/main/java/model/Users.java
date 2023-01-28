package model;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

/**
 * Users class to handle new and existing users
 */
public class Users extends Date {
    private int userId;
    private String userName;
    private String userPassword;

    /**
     * Users constructor
     *
     * @param userId User ID
     * @param userName Username
     * @param userPassword Password
     * @param createDate Create Date
     * @param createdBy Created By
     * @param lastUpdate Last update
     * @param lastUpdatedBy Last updated by
     */
    public Users(int userId, String userName, String userPassword, ZonedDateTime createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy) {
        super(createDate, createdBy, lastUpdate, lastUpdatedBy);
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
    }

    /**
     * Getter for User ID
     * @return User ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Setter for User ID
     * @param userId User ID
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Getter for Username
     * @return Username
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Setter for username
     * @param userName Username
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Getter for User Password
     * @return User Password
     */
    public String getUserPassword() {
        return userPassword;
    }

    /**
     * Setter for user password
     * @param userPassword User password
     */
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    /**
     * Override to string to display in ComboBoxes
     * @return  String of User ID and Username
     */
    @Override
    public String toString() {
        return (" [" + Integer.toString(userId) + "] " + userName + " ");
    }
}
