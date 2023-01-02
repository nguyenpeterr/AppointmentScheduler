package model;

public class Country {
    private int countryId;
    private String countryName;

    public Country(int id, String country) {
        this.countryId = id;
        this.countryName = country;
    }

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
}
