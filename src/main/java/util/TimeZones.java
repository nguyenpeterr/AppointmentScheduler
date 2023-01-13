package util;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

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



    public static String getLocalTime(ZonedDateTime time) {
        return localTime.format(timeFormatter);
    }

    public static String getLocalTime() {
        return localTime.format(timeFormatter);
    }

    public static String getLocalDate(ZonedDateTime time) {
        return localDate.format(dateFormatter);
    }

    public static String getLocalDateTime() {
        return localDateTime.format(dateTimeFormatter);
    }

    public static ZoneId getLocalTimeZone() {
        return localZoneId;
    }

    public static String getMyZDT() {
        return myZDT.format(zonedDateFormatter);
    }

    public static String convertToUTC() {
        return utcZDT.format(zonedDateFormatter);
    }

    public static String getUTCTime() {
        return utcZDT.toLocalTime().format(timeFormatter);
    }

    public static ZoneId getUTCZone() {
        return utcZoneId;
    }

    public static String convertToLocal() {
        myZDT = ZonedDateTime.ofInstant(utcZDT.toInstant(), localZoneId);
        return myZDT.format(zonedDateFormatter);
    }

    public static Timestamp timestamp(ZonedDateTime timestamp) {
        return Timestamp.valueOf(sqlFormat.format(timestamp));
    }

    public static ZonedDateTime toLocal(ZonedDateTime time) {
        return ZonedDateTime.of(time.toLocalDateTime(), ZoneId.systemDefault());
    }

    public static ZonedDateTime toLocal(Timestamp time) {
        return ZonedDateTime.of(time.toLocalDateTime(), ZoneId.systemDefault());
    }

    public static LocalTime setLocalTime(int hour, int min) {
        return LocalTime.of(hour, min, 0);
    }

    public static ZonedDateTime EST(ZonedDateTime time) {
        return ZonedDateTime.of(time.toLocalDateTime(), ZoneId.of("America/New_York"));
    }

    public static LocalTime EST(LocalTime time) {
        ZoneId system = ZoneId.systemDefault();
        ZoneId est = ZoneId.of("America/New_York");
        LocalDateTime today = LocalDateTime.of(LocalDate.now(), time);
        ZonedDateTime systemDateTime = ZonedDateTime.of(today, system);
        ZonedDateTime estDateTime = systemDateTime.withZoneSameInstant(est);
        return estDateTime.toLocalTime();
    }

    public static ZonedDateTime combinedDateTime(LocalDate date, LocalTime time) {
        if (date != null && time != null) {
            LocalDateTime combinedTime = LocalDateTime.of(date, time);
            ZonedDateTime combinedLocalTime = ZonedDateTime.of(combinedTime, ZoneId.of("America/New_York"));
            return ZonedDateTime.of(combinedLocalTime.toLocalDateTime(), ZoneId.of("UTC"));
        }
        return ZonedDateTime.now();
    }

    public static Timestamp timestampUTC() {
        return Timestamp.valueOf(sqlFormat.format(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("UTC"))));
    }

    public static String reportEST(ZonedDateTime time) {
        return zonedDateFormatter.format(ZonedDateTime.of(time.toLocalDateTime(), ZoneId.of("America/New_York")));
    }
}
