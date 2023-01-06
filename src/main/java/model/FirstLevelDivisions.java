package model;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

public class FirstLevelDivisions extends Date {
    private int divisionId;
    private String division;
    private int countryId;


    public FirstLevelDivisions(int divisionId, String division, ZonedDateTime createDate, String createdBy, ZonedDateTime lastUpdate, String lastUpdatedBy, int countryId) {
        super(createDate, createdBy, lastUpdate, lastUpdatedBy);
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }

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
}
