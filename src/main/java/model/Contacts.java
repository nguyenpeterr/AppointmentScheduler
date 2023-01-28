package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

/**
 * Contacts model class that handles new Contacts
 */
public class Contacts {
    private int contactId;
    private String contactName;
    private String contactEmail;


    /**
     * Contacts constructor
     * @param contactId Contact ID (auto generated)
     * @param contactName Contact name
     * @param contactEmail Contact Email
     */
    public Contacts(int contactId, String contactName, String contactEmail) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * Getter for Contact ID
     * @return Contact ID
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Setter for Contact ID
     * @param contactId Contact ID
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * Getter for Contact Name
     * @return Contact Name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Setter for Contact Name
     * @param contactName Contact Name
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Getter for Contact Email
     * @return Contact Email
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * Setter for Contact Email
     * @param contactEmail Contact Email
     */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    /**
     * String override to show Contact ID for ComboBox display
     * @return String of Contact ID
     */
    @Override
    public String toString() {
        return (Integer.toString(contactId));
    }

}
