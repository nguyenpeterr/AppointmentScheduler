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
     * Getters and setters of the Contacts constructor
     * @return
     */
    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    @Override
    public String toString() {
        return (Integer.toString(contactId));
    }

}
