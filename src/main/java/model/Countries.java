package model;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

/**
 * Countries model class
 */
public class Countries extends Date {
    private int countryId;
    private String countryName;

    /**
     * Country constructor
     * @param countryId Country Id
     * @param countryName Country Name
     * @param createDate Create date
     * @param createdBy Created by
     * @param lastUpdate Last update
     * @param lastUpdatedBy Last updated by
     */
    public Countries(int countryId, String countryName, ZonedDateTime createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy) {
        super(createDate, createdBy, lastUpdate, lastUpdatedBy);
        this.countryId = countryId;
        this.countryName = countryName;
    }

    /**
     * Getters and setters for the Countries constructor
     * @return
     */
    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public String toString() {
        return (countryName);
    }
}
