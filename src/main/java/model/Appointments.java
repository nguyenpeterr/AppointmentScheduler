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

    public Appointments() {
        this(0, null, null, null, null, LocalDateTime.now(), LocalDateTime.now(), null, null, null, null, -1, -1 ,-1);
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return this.location;
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

    public String getStart() {
        return start.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
    }

    public LocalDateTime getStartDateTimeLocal() {
        return this.start;
    }

    public ZonedDateTime getStartTimeZoned() {
        return this.start.atZone(ZoneId.systemDefault());
    }

    public void setStart(ZonedDateTime start) {
        ZoneId localZoneId = ZoneId.systemDefault();
        LocalDateTime startLocalDateTime = start.withZoneSameInstant(localZoneId).toLocalDateTime();
        setStart(startLocalDateTime);
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public String getEnd() {
        return this.end.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
    }

    public LocalDateTime getEndTimeLocal() {
        return this.end;
    }

    public ZonedDateTime getEndTimeZoned() {
        return this.end.atZone(ZonedDateTime.now().getZone());
    }

    public void setEnd(LocalDateTime endDateTimeLocal) {
        end = endDateTimeLocal;
    }

    public void setEnd(ZonedDateTime end) {
        ZoneId localZoneId = ZoneId.systemDefault();
        LocalDateTime endDateTimeLocal = end.withZoneSameInstant(localZoneId).toLocalDateTime();
        setEnd(endDateTimeLocal);
    }


    @Override
    public ZonedDateTime getCreateDate() {
        return createDate;
    }

    @Override
    public void setCreateDate(ZonedDateTime createDate) {
        this.createDate = createDate;
    }

    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    @Override
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    @Override
    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getContactId() {
        return this.contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    @Override
    public String toString() {
        return (getType());
    }
}

