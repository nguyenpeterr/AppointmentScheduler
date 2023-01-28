package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

/**
 * Appointments model class to handle new appointments
 */
public class Appointments extends Date {
    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private ZonedDateTime createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int userId;
    private int customerId;
    private int contactId;

    /**
     * Appointments constructor
     * @param appointmentId Appointment ID that is auto-generated
     * @param title Appointment title
     * @param description Appointment description
     * @param location Appointment location
     * @param type Appointment type
     * @param start Appointment start time
     * @param end Appointment end time
     * @param createDate Create date of the appointment
     * @param createdBy Appointment created by -user-
     * @param lastUpdate Last update of the appointment
     * @param lastUpdatedBy Last updated by -user-
     * @param customerId Customer ID that's the Foreign Key to the appointment
     * @param userId User ID that is linked to the user logged in (or manually selected)
     * @param contactId Contact ID linked to the Appointment
     */
    public Appointments(int appointmentId, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end,
                        ZonedDateTime createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy, int customerId, int userId, int contactId) {
        super(createDate, createdBy, lastUpdate, lastUpdatedBy);
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;

    }

    /**
     * Default Appointments constructor
     */
    public Appointments() {
        this(0, null, null, null, null, LocalDateTime.now(), LocalDateTime.now(), null, null, null, null, -1, -1 ,-1);
    }

    /**
     * Getter for Appointment ID
     * @return Appointment ID
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * Setter for Appointment ID
     * @param appointmentId Appointment ID
     */
    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    /**
     * Getter for Appointment Title
     * @return Appointment Title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Setter for Appointment Title
     * @param title Appointment Title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Getter for Appointment Description
     * @return Appointment Description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Setter for Appointment Description
     * @param description Appointment Description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for Appointment Location
     * @return Appointment Location
     */
    public String getLocation() {
        return this.location;
    }

    /**
     * Setter for Appointment Location
     * @param location Appointment Location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Getter for Appointment Type
     * @return Appointment Type
     */
    public String getType() {
        return type;
    }

    /**
     * Setter for Appointment Type
     * @param type Appointment Type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Getter for Appointment Start Time which shows the formatted start time
     * @return Returns start time formatted
     */
    public String getStart() {
        return start.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
    }

    /**
     * Getter for Appointment Start time in LocalDateTime
     * @return LocalDateTime of Appointment Start Time
     */
    public LocalDateTime getStartDateTimeLocal() {
        return this.start;
    }

    /**
     * Gets the ZoneId of the system default from the ZonedDateTime
     * @return ZoneId of the system default
     */
    public ZonedDateTime getStartTimeZoned() {
        return this.start.atZone(ZoneId.systemDefault());
    }


    /**
     * Sets the start time to LocalDateTime
     * @param start ZonedDateTime start time
     */
    public void setStart(ZonedDateTime start) {
        ZoneId localZoneId = ZoneId.systemDefault();
        LocalDateTime startLocalDateTime = start.withZoneSameInstant(localZoneId).toLocalDateTime();
        setStart(startLocalDateTime);
    }

    /**
     * Setter for Appointment Start Time
     * @param start LocalDateTime start time
     */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**
     * Gets the formatted end time
     * @return returns end time formatted
     */
    public String getEnd() {
        return this.end.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
    }

    /**
     * Getter for End Time in LocalDateTime
     * @return LocalDateTime End Time
     */
    public LocalDateTime getEndTimeLocal() {
        return this.end;
    }

    /**
     * Gets the end time in ZonedDateTime
     * @return ZonedDateTime of the end time
     */
    public ZonedDateTime getEndTimeZoned() {
        return this.end.atZone(ZonedDateTime.now().getZone());
    }

    /**
     * Setter for End Time in LocalDateTime
     * @param endDateTimeLocal LocalDateTime End Time
     */
    public void setEnd(LocalDateTime endDateTimeLocal) {
        end = endDateTimeLocal;
    }

    /**
     * Sets the end time to LocalDateTime from a ZonedDateTime
     * @param end ZonedDateTime End Time
     */
    public void setEnd(ZonedDateTime end) {
        ZoneId localZoneId = ZoneId.systemDefault();
        LocalDateTime endDateTimeLocal = end.withZoneSameInstant(localZoneId).toLocalDateTime();
        setEnd(endDateTimeLocal);
    }

    /**
     * Getter for Appointment create date in ZonedDateTime
     * @return ZonedDateTimeCreate date
     */
    @Override
    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    /**
     * Setter for Appointment create date
     * @param createDate ZonedDateTime create date
     */
    @Override
    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    /**
     * Getter for Appointmet created by
     * @return Created by
     */
    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Setter for Created by
     * @param createdBy Created by
     */
    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Getter for Appointment last update timestamp
     * @return Timestamp for appointment last update
     */
    @Override
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    /**
     * Setter for Appointment last update
     * @param lastUpdate Timestamp of last update
     */
    @Override
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Getter for appointment last updated by
     * @return Last updated by
     */
    @Override
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    /**
     * Setter for Appointment last updated by
     * @param lastUpdatedBy Last updated by
     */
    @Override
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Getter for User ID
     * @return User ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Setter for User ID
     * @param userId User ID
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Getter for Customer Id
     * @return Customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Setter for Customer ID
     * @param customerId Customer ID
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Getter for Contact ID
     * @return Contact ID
     */
    public int getContactId() {
        return this.contactId;
    }

    /**
     * Setter for Contact ID
     * @param contactId Contact ID
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    /**
     * String override for printing to show Type
     * @return String of Appointment Type
     */
    @Override
    public String toString() {
        return (getType());
    }
}

