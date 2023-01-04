package model;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

public class Customers extends Date {
    private int customerId;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhoneNumber;

    public Customers(int customerId, String customerName, String customerAddress, String customerPostalCode, String customerPhoneNumber, ZonedDateTime createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy) {
        super(createDate, createdBy, lastUpdate, lastUpdatedBy);
            this.customerId = customerId;
            this.customerName = customerName;
            this.customerAddress = customerAddress;
            this.customerPostalCode = customerPostalCode;
            this.customerPhoneNumber = customerPhoneNumber;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    public void setCustomerPostalCode(String customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }
}
