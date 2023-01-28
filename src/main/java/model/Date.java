package model;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

/**
 * Date model class to handle local date and zoned date times for Create and Last update
 */
public class Date {
    private ZonedDateTime createDate;
    private Timestamp lastUpdate;
    private String createdBy;
    private String lastUpdatedBy;

    /**
     * Date constructor
     * @param createDate Create date
     * @param createdBy Created by
     * @param lastUpdate Last update
     * @param lastUpdatedBy Last updated by
     */
    public Date (ZonedDateTime createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy) {
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Getter for Create Date
     * @return ZonedDateTime of create date
     */
    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Setter for Create Date
     * @param createDate ZonedDateTime of create date
     */
    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Getter for Last Update
     * @return Timestamp of last update
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Setter for last update
     * @param lastUpdate Timestamp of last update
     */
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Getter for created by
     * @return Created by
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Setter for created by
     * @param createdBy Created by
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Getter for last updated by
     * @return Last updated by
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Setter for last updated by
     * @param lastUpdatedBy Last updated by
     */
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
}
