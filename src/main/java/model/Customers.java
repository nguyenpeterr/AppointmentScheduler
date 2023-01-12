package model;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

public class Customers extends Date {
    private int customerId;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhoneNumber;
    private int divisionId;

    public Customers(int customerId, String customerName, String customerAddress, String customerPostalCode, String customerPhoneNumber, ZonedDateTime createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int divisionId) {
        super(createDate, createdBy, lastUpdate, lastUpdatedBy);
            this.customerId = customerId;
            this.customerName = customerName;
            this.customerAddress = customerAddress;
            this.customerPostalCode = customerPostalCode;
            this.customerPhoneNumber = customerPhoneNumber;
            this.divisionId = divisionId;
    }

    public int getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return this.customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPostalCode() {
        return this.customerPostalCode;
    }

    public void setCustomerPostalCode(String customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }

    public String getCustomerPhoneNumber() {
        return this.customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }


    public int getDivisionId() {
        return this.divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

}
