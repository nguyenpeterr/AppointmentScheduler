package model;

import java.sql.Timestamp;
import java.time.ZonedDateTime;

public class Appointments extends Date {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;

    public Appointments(int appointmentId, String title, String description, String location, String type, ZonedDateTime createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy) {
        super(createDate, createdBy, lastUpdate, lastUpdatedBy);
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
