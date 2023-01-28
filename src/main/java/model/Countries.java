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
     * Getter for Country ID
     * @return Country ID
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Setter for Country ID
     * @param countryId Country ID
     */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     * Getter for Country Name
     * @return Country Name
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Setter for Country Name
     * @param countryName Country Name
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * String override for Country Name to display in ComboBox
     * @return String of Country Name
     */
    @Override
    public String toString() {
        return (countryName);
    }
}
