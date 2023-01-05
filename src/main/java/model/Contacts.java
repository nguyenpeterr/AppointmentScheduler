package model;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

public class Contacts extends Date {
    private int contactId;
    private String contactName;
    private String contactEmail;

    public Contacts(int contactId, String contactName, String contactEmail, ZonedDateTime createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy) {
        super(createDate, createdBy, lastUpdate, lastUpdatedBy);
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

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
}
