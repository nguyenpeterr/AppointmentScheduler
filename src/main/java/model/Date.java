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
     * Getters and setters for Date constructor
     * @return
     */
    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }
}
