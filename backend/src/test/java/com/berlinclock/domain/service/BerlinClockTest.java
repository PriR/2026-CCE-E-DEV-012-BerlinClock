package com.berlinclock.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.berlinclock.domain.model.BerlinClockDisplay;
import com.berlinclock.domain.model.Lamp;
import com.berlinclock.domain.model.LampRow;
import com.berlinclock.domain.model.TimeOfDay;
import com.berlinclock.domain.rule.LampRule;
import java.util.List;
import org.junit.jupiter.api.Test;

class BerlinClockTest {

    private final BerlinClock clock = BerlinClock.standard();

    @Test
    void midnightHasOnlyTheSecondsLampLit() {
        assertEquals(List.of("Y", "OOOO", "OOOO", "OOOOOOOOOOO", "OOOO"),
                rows(clock.display(new TimeOfDay(0, 0, 0))));
    }

    @Test
    void lastSecondOfTheDay() {
        assertEquals(List.of("O", "RRRR", "RRRO", "YYRYYRYYRYY", "YYYY"),
                rows(clock.display(new TimeOfDay(23, 59, 59))));
    }

    @Test
    void thirteenSeventeenAndOneSecond() {
        assertEquals(List.of("O", "RROO", "RRRO", "YYROOOOOOOO", "YYOO"),
                rows(clock.display(new TimeOfDay(13, 17, 1))));
    }

    @Test
    void endOfDayAsTwentyFourHundredLightsAllHourLamps() {
        assertEquals(List.of("Y", "RRRR", "RRRR", "OOOOOOOOOOO", "OOOO"),
                rows(clock.display(new TimeOfDay(24, 0, 0))));
    }

    @Test
    void theDisplayKnowsWhichTimeItShows() {
        TimeOfDay time = new TimeOfDay(13, 17, 1);

        assertEquals(time, clock.display(time).time());
    }

    @Test
    void eachRowComesFromItsOwnRule() {
        BerlinClock clock = new BerlinClock(
                rule(1), rule(2), rule(3), rule(4), rule(5));

        BerlinClockDisplay display = clock.display(new TimeOfDay(0, 0, 0));

        assertEquals(1, display.seconds().lamps().size());
        assertEquals(2, display.fiveHours().lamps().size());
        assertEquals(3, display.oneHours().lamps().size());
        assertEquals(4, display.fiveMinutes().lamps().size());
        assertEquals(5, display.oneMinutes().lamps().size());
    }

    private static LampRule rule(int size) {
        return time -> LampRow.uniform(size, 0, Lamp.RED);
    }

    private static List<String> rows(BerlinClockDisplay display) {
        return List.of(
                display.seconds().asText(),
                display.fiveHours().asText(),
                display.oneHours().asText(),
                display.fiveMinutes().asText(),
                display.oneMinutes().asText());
    }
}
