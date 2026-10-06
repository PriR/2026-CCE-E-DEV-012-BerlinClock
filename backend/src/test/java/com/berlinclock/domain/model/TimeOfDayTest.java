package com.berlinclock.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class TimeOfDayTest {

    @Test
    void exposesItsHoursMinutesAndSeconds() {
        TimeOfDay time = new TimeOfDay(13, 17, 1);

        assertEquals(13, time.hours());
        assertEquals(17, time.minutes());
        assertEquals(1, time.seconds());
    }

    @Test
    void midnightAndTheLastSecondOfTheDayAreValid() {
        new TimeOfDay(0, 0, 0);
        new TimeOfDay(23, 59, 59);
    }

    @Test
    void twentyFourHundredIsTheValidEndOfDay() {
        new TimeOfDay(24, 0, 0);
    }

    @Test
    void hoursOutsideZeroToTwentyFourAreRejected() {
        assertRejected(-1, 0, 0);
        assertRejected(25, 0, 0);
    }

    @Test
    void minutesOutsideZeroToFiftyNineAreRejected() {
        assertRejected(0, -1, 0);
        assertRejected(0, 60, 0);
    }

    @Test
    void secondsOutsideZeroToFiftyNineAreRejected() {
        assertRejected(0, 0, -1);
        assertRejected(0, 0, 60);
    }

    @Test
    void twentyFourHoursIsOnlyValidWithZeroMinutesAndSeconds() {
        assertRejected(24, 1, 0);
        assertRejected(24, 0, 1);
    }

    @Test
    void rejectionMessagesNameTheOffendingPart() {
        assertEquals("hours must be between 0 and 24: 25",
                assertThrows(InvalidTimeException.class, () -> new TimeOfDay(25, 0, 0)).getMessage());
        assertEquals("minutes must be between 0 and 59: 60",
                assertThrows(InvalidTimeException.class, () -> new TimeOfDay(0, 60, 0)).getMessage());
        assertEquals("seconds must be between 0 and 59: 60",
                assertThrows(InvalidTimeException.class, () -> new TimeOfDay(0, 0, 60)).getMessage());
        assertEquals("24 hours is only valid as 24:00:00",
                assertThrows(InvalidTimeException.class, () -> new TimeOfDay(24, 0, 1)).getMessage());
    }

    @Test
    void parsesAnHhMmSsString() {
        assertEquals(new TimeOfDay(23, 59, 59), TimeOfDay.parse("23:59:59"));
        assertEquals(new TimeOfDay(24, 0, 0), TimeOfDay.parse("24:00:00"));
        assertEquals(new TimeOfDay(0, 0, 0), TimeOfDay.parse("00:00:00"));
    }

    @Test
    void anEmptyStringIsRejected() {
        assertMalformed("");
    }

    @Test
    void aTimeWithoutSecondsIsRejected() {
        assertMalformed("12:00");
    }

    @Test
    void partsWithoutLeadingZerosAreRejected() {
        assertMalformed("1:2:3");
    }

    @Test
    void nonDigitsAreRejected() {
        assertMalformed("ab:cd:ef");
    }

    @Test
    void anExtraPartIsRejected() {
        assertMalformed("12:00:00:00");
    }

    @Test
    void aWrongSeparatorIsRejected() {
        assertMalformed("12-00-00");
    }

    @Test
    void surroundingSpacesAreRejected() {
        assertMalformed(" 12:00:00");
    }

    @Test
    void aWellFormedButOutOfRangeStringIsRejected() {
        assertThrows(InvalidTimeException.class, () -> TimeOfDay.parse("25:00:00"));
    }

    @Test
    void malformedStringsExplainTheExpectedFormat() {
        assertEquals("time must use the HH:mm:ss format",
                assertThrows(InvalidTimeException.class, () -> TimeOfDay.parse("noon")).getMessage());
    }

    @Test
    void aNullStringIsRejected() {
        assertThrows(InvalidTimeException.class, () -> TimeOfDay.parse(null));
    }

    @Test
    void formatsAsHhMmSssWithLeadingZeros() {
        assertEquals("01:02:03", new TimeOfDay(1, 2, 3).toString());
        assertEquals("24:00:00", new TimeOfDay(24, 0, 0).toString());
    }

    private static void assertRejected(int hours, int minutes, int seconds) {
        assertThrows(InvalidTimeException.class, () -> new TimeOfDay(hours, minutes, seconds),
                hours + ":" + minutes + ":" + seconds);
    }

    private static void assertMalformed(String text) {
        assertThrows(InvalidTimeException.class, () -> TimeOfDay.parse(text), "\"" + text + "\"");
    }
}
