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
     * Getters and setters for the FirstLevelDivisions constructor
     * @return
     */
    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    @Override
    public String toString() {
        return (" [" + Integer.toString(divisionId) + "] " + division + " ");
    }
}
