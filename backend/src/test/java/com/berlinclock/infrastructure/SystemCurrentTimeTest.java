package com.berlinclock.infrastructure;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.berlinclock.domain.model.TimeOfDay;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import org.junit.jupiter.api.Test;

class SystemCurrentTimeTest {

    @Test
    void readsTheTimeOfDayFromTheClock() {
        Clock clock = Clock.fixed(Instant.parse("2026-10-05T13:17:01Z"), ZoneOffset.UTC);

        assertEquals(new TimeOfDay(13, 17, 1), new SystemCurrentTime(clock).now());
    }

    @Test
    void usesTheZoneOfTheClock() {
        Clock clock = Clock.fixed(Instant.parse("2026-10-05T23:30:00Z"), ZoneOffset.ofHours(2));

        assertEquals(new TimeOfDay(1, 30, 0), new SystemCurrentTime(clock).now());
    }
}
