package com.berlinclock.api;

import com.berlinclock.api.generated.model.BerlinClockResponse;
import com.berlinclock.domain.model.TimeOfDay;
import com.berlinclock.domain.service.BerlinClock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BerlinClockResponseMapperTest {

    private final BerlinClock clock = BerlinClock.standard();

    @Test
    void carriesOneTextPerRow() {
        BerlinClockResponse response = responseFor(new TimeOfDay(23, 59, 59));

        assertEquals("O", response.getSeconds());
        assertEquals("RRRR", response.getFiveHours());
        assertEquals("RRRO", response.getOneHours());
        assertEquals("YYRYYRYYRYY", response.getFiveMinutes());
        assertEquals("YYYY", response.getOneMinutes());
    }

    @Test
    void showsTheEndOfTheDay() {
        BerlinClockResponse response = responseFor(new TimeOfDay(24, 0, 0));

        assertEquals("Y", response.getSeconds());
        assertEquals("RRRR", response.getFiveHours());
        assertEquals("RRRR", response.getOneHours());
        assertEquals("OOOOOOOOOOO", response.getFiveMinutes());
        assertEquals("OOOO", response.getOneMinutes());
    }

    private BerlinClockResponse responseFor(TimeOfDay time) {
        return BerlinClockResponseMapper.toResponse(clock.display(time));
    }
}
