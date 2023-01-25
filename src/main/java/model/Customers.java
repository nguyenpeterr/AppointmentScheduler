package model;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

/**
 * Customers model class to handle new and existing Customers
 */
public class Customers extends Date {
    private int customerId;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhoneNumber;
    private int divisionId;
    private String appointmentType;

    /**
     * Customers constructor
     *
     * @param customerId Customer ID
     * @param customerName Customer Name
     * @param customerAddress Customer Address
     * @param customerPostalCode Customer Postal COde
     * @param customerPhoneNumber Customer Phone Number
     * @param createDate Create date of the customer
     * @param createdBy Created by
     * @param lastUpdate Customer data last update
     * @param lastUpdatedBy Customer data last updated by
     * @param divisionId Division ID
     */
    public Customers(int customerId, String customerName, String customerAddress, String customerPostalCode, String customerPhoneNumber, ZonedDateTime createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int divisionId) {
        super(createDate, createdBy, lastUpdate, lastUpdatedBy);
            this.customerId = customerId;
            this.customerName = customerName;
            this.customerAddress = customerAddress;
            this.customerPostalCode = customerPostalCode;
            this.customerPhoneNumber = customerPhoneNumber;
            this.divisionId = divisionId;
    }

    /**
     * Getters and setters for Customers constructor
     * @return
     */
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




    @Override
    public String toString() {
        return (" [" + Integer.toString(customerId) + "] " + customerName + " ");
    }

}
