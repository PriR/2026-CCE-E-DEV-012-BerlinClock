package com.berlinclock.domain.model;

import java.util.regex.Pattern;

/**
 * A valid time on the Berlin Clock: 00:00:00 to 23:59:59, plus 24:00:00 for the end of the day.
 * An invalid time cannot be constructed.
 */
public record TimeOfDay(int hours, int minutes, int seconds) {

    private static final Pattern HH_MM_SS = Pattern.compile("\\d{2}:\\d{2}:\\d{2}");

    public TimeOfDay {
        requireBetween("hours", hours, 24);
        requireBetween("minutes", minutes, 59);
        requireBetween("seconds", seconds, 59);
        if (hours == 24 && (minutes != 0 || seconds != 0)) {
            throw new InvalidTimeException("24 hours is only valid as 24:00:00");
        }
    }

    public static TimeOfDay parse(String text) {
        if (text == null || !HH_MM_SS.matcher(text).matches()) {
            throw new InvalidTimeException("time must use the HH:mm:ss format");
        }
        return new TimeOfDay(
                Integer.parseInt(text.substring(0, 2)),
                Integer.parseInt(text.substring(3, 5)),
                Integer.parseInt(text.substring(6, 8)));
    }

    private static void requireBetween(String part, int value, int max) {
        if (value < 0 || value > max) {
            throw new InvalidTimeException(part + " must be between 0 and " + max + ": " + value);
        }
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
