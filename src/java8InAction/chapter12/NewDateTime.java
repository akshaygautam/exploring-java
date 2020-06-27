package java8InAction.chapter12;

import static java.lang.System.out;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;
import static java.time.temporal.TemporalAdjusters.nextOrSame;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class NewDateTime {
    public static void main(final String[] args) {
        workingWithLocalDate();
        workingWithLocalTime();
        workingWithLocalDateTime();
        workingWithInstant();
        workingWithDurationAndPeriod();
        workingWithDateTimeAdjusters();
        workingWithDateTimeFormatter();
        workingWithDateTimeZones();
    }

    public static void workingWithLocalDate() {
        out.println("*******workingWithLocalDate********");
        LocalDate localDate = LocalDate.of(2020, 6, 20);
        out.println(localDate);
        out.println(localDate.getMonth());
        out.println(localDate.getDayOfMonth());
        out.println(localDate.getDayOfYear());
        out.println(localDate.isLeapYear());

        localDate = LocalDate.now();
        out.println(localDate);
        out.println(localDate.get(ChronoField.DAY_OF_MONTH));

        localDate = LocalDate.parse("1994-09-22");

        out.println(localDate);
        out.println(localDate.get(ChronoField.DAY_OF_MONTH));
    }

    public static void workingWithLocalTime() {
        out.println("*******workingWithLocalTime********");
        LocalTime localTime = LocalTime.of(12, 38, 40);
        out.println(localTime);
        out.println(localTime.getHour());
        out.println(localTime.getMinute());
        out.println(localTime.getSecond());
        out.println(localTime.getLong(ChronoField.CLOCK_HOUR_OF_DAY));

        localTime = LocalTime.parse("22:32:45");
        out.println(localTime);
        out.println(localTime.getHour());
        out.println(localTime.getMinute());
        out.println(localTime.getSecond());
        out.println(localTime.getLong(ChronoField.CLOCK_HOUR_OF_DAY));
    }

    public static void workingWithLocalDateTime() {
        out.println("*******workingWithLocalDateTime********");
        LocalDateTime dateTime = LocalDateTime.now();
        out.println(dateTime);
    }

    public static void workingWithInstant() {
        out.println("*******workingWithInstant********");
        Instant instant = Instant.now();
        out.println(instant);

        instant = Instant.ofEpochSecond(400 * 10000);
        out.println(instant);
    }

    public static void workingWithDurationAndPeriod() {
        out.println("*******workingWithDurationAndPeriod********");
        Duration duration = Duration.between(LocalDateTime.of(2012, 5, 30, 12, 39), LocalDateTime.now());
        out.println(duration.get(ChronoUnit.SECONDS));

        Period period = Period.between(LocalDate.of(2012, 5, 30), LocalDate.now());
        out.println(period.toTotalMonths() / 12);

    }

    public static void workingWithDateTimeAdjusters() {
        out.println("*******workingWithDateTimeAdjusters********");
        LocalDate date = LocalDate.now();
        LocalDateTime dateTime = LocalDateTime.now();

        out.println(date.plusDays(10));
        out.println(date.plusWeeks(2));
        out.println(dateTime.minusYears(20));
        out.println(date.getEra());
        out.println(date.lengthOfMonth());
        out.println(date.with(nextOrSame(DayOfWeek.FRIDAY)));
        out.println(date.with(lastDayOfMonth()));
        out.println(date.with(lastDayOfYear()));
    }

    public static void workingWithDateTimeFormatter() {
        out.println("*******workingWithDateTimeFormatter********");
        LocalDateTime localDateTime = LocalDateTime.now().minusYears(1);
        out.println(localDateTime.format(DateTimeFormatter.BASIC_ISO_DATE));
        out.println(localDateTime.format(DateTimeFormatter.ISO_DATE));
        out.println(localDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/mm/yy hh:mm");
        out.println(localDateTime.format(formatter));

        DateTimeFormatter italianFormatter = new DateTimeFormatterBuilder().appendText(ChronoField.DAY_OF_MONTH)
                .appendLiteral(". ").appendText(ChronoField.MONTH_OF_YEAR).appendLiteral(" ")
                .appendText(ChronoField.YEAR).parseCaseInsensitive().toFormatter(Locale.ITALIAN);

        out.println(localDateTime.format(italianFormatter));
    }

    public static void workingWithDateTimeZones() {
        out.println("*******workingWithDateTimeZones********");
        ZoneId romeZoneId = ZoneId.of("Europe/Rome");
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = LocalDate.now();
        out.println(localDateTime);
        out.println(localDateTime.atZone(romeZoneId));

        out.println(localDate);
        out.println(localDate.atStartOfDay(romeZoneId));

        ZoneOffset newYorkOffset = ZoneOffset.of("-05:00");

        out.println(localDate.atStartOfDay(newYorkOffset));
        out.println(localDateTime.atZone(newYorkOffset));
    }
}