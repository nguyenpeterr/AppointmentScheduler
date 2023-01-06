package util;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class TimeZones {
    private static ZoneId localZoneId = ZoneId.systemDefault();
    private static LocalDate localDate = LocalDate.now();
    private static LocalTime localTime = LocalTime.now();
    private static LocalDateTime localDateTime = LocalDateTime.now();
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static DateTimeFormatter zonedDateFormatter = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm z");
    private static ZonedDateTime myZDT = ZonedDateTime.of(localDateTime, localZoneId);
    private static ZoneId utcZoneId = ZoneId.of("UTC");
    private static ZonedDateTime utcZDT = ZonedDateTime.ofInstant(myZDT.toInstant(), utcZoneId);



    public static String getLocalTime() {
        return localTime.format(timeFormatter);
    }

    public static String getLocalDate() {
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



}
