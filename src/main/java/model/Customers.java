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
     * Getter for Customer ID
     * @return Customer ID
     */
    public int getCustomerId() {
        return this.customerId;
    }

    /**
     * Setter for Customer ID
     * @param customerId Customer ID
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Getter for Customer Name
     * @return Customer Name
     */
    public String getCustomerName() {
        return this.customerName;
    }

    /**
     * Setter for Customer Name
     * @param customerName Customer Name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Getter for Customer Address
     * @return Customer Address
     */
    public String getCustomerAddress() {
        return this.customerAddress;
    }

    /**
     * Setter for Customer Address
     * @param customerAddress Customer Address
     */
    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    /**
     * Getter for Customer Postal Code
     * @return Customer Postal Code
     */
    public String getCustomerPostalCode() {
        return this.customerPostalCode;
    }

    /**
     * Setter for Customer Postal Code
     * @param customerPostalCode Postal Code
     */
    public void setCustomerPostalCode(String customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }

    /**
     * Getter for Customer Phone Number
     * @return Customer Phone Number
     */
    public String getCustomerPhoneNumber() {
        return this.customerPhoneNumber;
    }

    /**
     * Setter for Customer Phone Number
     * @param customerPhoneNumber Phone number
     */
    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    /**
     * Getter for Division ID
     * @return Division ID
     */
    public int getDivisionId() {
        return this.divisionId;
    }

    /**
     * Setter for Division ID
     * @param divisionId Division ID
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     * String override to display Customer ID and Customer Name as strings in ComboBoxes
     * @return String of Customer ID and Customer Name
     */
    @Override
    public String toString() {
        return (" [" + Integer.toString(customerId) + "] " + customerName + " ");
    }

}
