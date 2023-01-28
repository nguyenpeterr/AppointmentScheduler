package model;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

/**
 * FirstLevelDivisions class to handle the divisions
 */
public class FirstLevelDivisions extends Date {
    private int divisionId;
    private String division;
    private int countryId;


    /**
     * FirstLevelDivisions constructor
     * @param divisionId Division ID
     * @param division Division
     * @param createDate Create Date
     * @param createdBy Created BY
     * @param lastUpdate Last Update
     * @param lastUpdatedBy Last updated by
     * @param countryId Country id
     */
    public FirstLevelDivisions(int divisionId, String division, ZonedDateTime createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int countryId) {
        super(createDate, createdBy, lastUpdate, lastUpdatedBy);
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }

    /**
     * Getter for Division ID
     * @return Division ID
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * Setter for Division ID
     * @param divisionId Division ID
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * Getter for Division
     * @return Division
     */
    public String getDivision() {
        return division;
    }

    /**
     * Setter for Division
     * @param division Division
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * String override to display Division ID and Division as strings in ComboBoxes
     * @return String of Division ID and Division
     */
    @Override
    public String toString() {
        return (" [" + Integer.toString(divisionId) + "] " + division + " ");
    }
}
