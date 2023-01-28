package util;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * TimeZones class is used to format and convert time zones between LocalDateTime and ZonedDateTime
 */
public class TimeZones {
    private static ZoneId localZoneId = ZoneId.systemDefault();
    private static LocalDate localDate = LocalDate.now();
    private static LocalTime localTime = LocalTime.now();
    private static LocalDateTime localDateTime = LocalDateTime.now();
    private static DateTimeFormatter sqlFormat = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss");
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static DateTimeFormatter zonedDateFormatter = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm z");
    private static ZonedDateTime myZDT = ZonedDateTime.of(localDateTime, localZoneId);
    private static ZoneId utcZoneId = ZoneId.of("UTC");
    private static ZonedDateTime utcZDT = ZonedDateTime.ofInstant(myZDT.toInstant(), utcZoneId);


    /**
     * Getter for ZonedDateTime of local time
     * @param time ZonedDateTime
     * @return Formatted ZonedDateTime
     */
    public static String getLocalTime(ZonedDateTime time) {
        return localTime.format(timeFormatter);
    }

    /**
     * Getter for Local Time
     * @return Formatted String of LocalTime.now()
     */
    public static String getLocalTime() {
        return localTime.format(timeFormatter);
    }

    /**
     * Getter for Local Date
     * @param time ZonedDateTime
     * @return Formatted String of LocalDate.now()
     */
    public static String getLocalDate(ZonedDateTime time) {
        return localDate.format(dateFormatter);
    }

    /**
     * Getter for LocalDateTime
     * @return Formatted String of LocalDateTime.now()
     */
    public static String getLocalDateTime() {
        return localDateTime.format(dateTimeFormatter);
    }

    /**
     * Getter for Local Zone ID
     * @return Zone ID of system default
     */
    public static ZoneId getLocalTimeZone() {
        return localZoneId;
    }

    /**
     * Getter for ZonedDateTime of LocalDateTime
     * @return Formatted ZonedDateTime of system default
     */
    public static String getMyZDT() {
        return myZDT.format(zonedDateFormatter);
    }

    /**
     * Converts to UTC Time Zone
     * @return Formatted String ZonedDateTime in UTC
     */
    public static String convertToUTC() {
        return utcZDT.format(zonedDateFormatter);
    }

    /**
     * Gets LocalTime in UTC Time Zone
     * @return Formatted String of LocalTime in UTC
     */
    public static String getUTCTime() {
        return utcZDT.toLocalTime().format(timeFormatter);
    }

    /**
     * Gets UTC Zone ID
     * @return Zone ID in UTC
     */
    public static ZoneId getUTCZone() {
        return utcZoneId;
    }

    /**
     * Grabs the ZoneDateTime as a timestamp
     * @param timestamp ZonedDateTIme
     * @return Formatted timestamp
     */
    public static Timestamp timestamp(ZonedDateTime timestamp) {
        return Timestamp.valueOf(sqlFormat.format(timestamp));
    }

    /**
     * Converts to ZonedDateTime from LocalDateTime in ZoneId.systemDefault()
     * @param time ZonedDateTime
     * @return ZonedDateTime in ZoneId.systemDefault of LocalDateTime
     */
    public static ZonedDateTime toLocal(ZonedDateTime time) {
        return ZonedDateTime.of(time.toLocalDateTime(), ZoneId.systemDefault());
    }

    /**
     * Converts to ZonedDateTime from timestamp in ZoneId.systemDefault()
     * @param time ZonedDateTime
     * @return ZonedDateTime in ZoneId.systemDefault of timestamp
     */
    public static ZonedDateTime toLocal(Timestamp time) {
        return ZonedDateTime.of(time.toLocalDateTime(), ZoneId.systemDefault());
    }

    /**
     * Sets Local Time from hours and mins
     * @param hour hours
     * @param min mins
     * @return Local Time of hours and mins
     */
    public static LocalTime setLocalTime(int hour, int min) {
        return LocalTime.of(hour, min, 0);
    }

    /**
     * ZonedDateTime in EST from LocalDateTime
     * @param time LocalDateTime
     * @return ZonedDateTime in EST
     */
    public static ZonedDateTime EST(LocalDateTime time) {
        return ZonedDateTime.of(time, ZoneId.of("America/New_York"));
    }

    /**
     * Converts the LocalTime to EST
     * @param time Local Time
     * @return EST Date Time
     */
    public static LocalTime EST(LocalTime time) {
        ZoneId system = ZoneId.systemDefault();
        ZoneId est = ZoneId.of("America/New_York");
        LocalDateTime today = LocalDateTime.of(LocalDate.now(), time);
        ZonedDateTime systemDateTime = ZonedDateTime.of(today, system);
        ZonedDateTime estDateTime = systemDateTime.withZoneSameInstant(est);
        return estDateTime.toLocalTime();
    }

    /**
     * Combines the LocalDate and LocalTime to a ZonedDatetime
     * @param date Local Date
     * @param time Local Time
     * @return If date is selected, returns ZonedDateTime of LocalDate and LocalTime, else returns ZonedDateTime.now()
     */
    public static ZonedDateTime combinedDateTime(LocalDate date, LocalTime time) {
        if (date != null && time != null) {
            LocalDateTime combinedTime = LocalDateTime.of(date, time);
            ZonedDateTime combinedLocalTime = ZonedDateTime.of(combinedTime, ZoneId.systemDefault());
            return combinedLocalTime;
        }
        return ZonedDateTime.now();
    }

    /**
     * Timestamp in UTC
     * @return Formatted timestamp in UTC time zone
     */
    public static Timestamp timestampUTC() {
        return Timestamp.valueOf(sqlFormat.format(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("UTC"))));
    }

}
