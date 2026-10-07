package com.berlinclock.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.berlinclock.domain.model.TimeOfDay;
import com.berlinclock.domain.service.BerlinClock;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class BerlinClockResponseTest {

    private final BerlinClock clock = BerlinClock.standard();

    @Test
    void carriesOnlyTheDisplay() {
        List<String> components = Arrays.stream(BerlinClockResponse.class.getRecordComponents())
                .map(component -> component.getName())
                .toList();

        assertEquals(List.of("seconds", "fiveHours", "oneHours", "fiveMinutes", "oneMinutes"), components);
    }

    @Test
    void carriesOneTextPerRow() {
        BerlinClockResponse response = responseFor(new TimeOfDay(23, 59, 59));

        assertEquals(new BerlinClockResponse("O", "RRRR", "RRRO", "YYRYYRYYRYY", "YYYY"), response);
    }

    @Test
    void showsTheEndOfTheDay() {
        BerlinClockResponse response = responseFor(new TimeOfDay(24, 0, 0));

        assertEquals(new BerlinClockResponse("Y", "RRRR", "RRRR", "OOOOOOOOOOO", "OOOO"), response);
    }

    private BerlinClockResponse responseFor(TimeOfDay time) {
        return new BerlinClockResponse(clock.display(time));
    }
}
