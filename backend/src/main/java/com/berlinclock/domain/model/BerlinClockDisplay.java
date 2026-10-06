package com.berlinclock.domain.model;

/** The lamps of the clock, from the top row to the bottom row, and the time they show. */
public record BerlinClockDisplay(
        TimeOfDay time,
        LampRow seconds,
        LampRow fiveHours,
        LampRow oneHours,
        LampRow fiveMinutes,
        LampRow oneMinutes) {
}
