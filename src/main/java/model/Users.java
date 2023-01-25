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
     * Getters and setters for Users constructor
     * @return
     */
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public String toString() {
        return (" [" + Integer.toString(userId) + "] " + userName + " ");
    }
}
