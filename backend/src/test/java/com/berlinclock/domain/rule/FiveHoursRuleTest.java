package com.berlinclock.domain.rule;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.berlinclock.domain.model.TimeOfDay;
import org.junit.jupiter.api.Test;

class FiveHoursRuleTest {

    private final LampRule rule = new FiveHoursRule();

    @Test
    void noLampBelowFiveHours() {
        assertLamps(0, "OOOO");
        assertLamps(4, "OOOO");
    }

    @Test
    void oneLampFromFiveHours() {
        assertLamps(5, "ROOO");
        assertLamps(9, "ROOO");
    }

    @Test
    void twoLampsFromTenHours() {
        assertLamps(10, "RROO");
        assertLamps(14, "RROO");
    }

    @Test
    void threeLampsFromFifteenHours() {
        assertLamps(15, "RRRO");
        assertLamps(19, "RRRO");
    }

    @Test
    void allFourLampsFromTwentyHoursAndAtEndOfDay() {
        assertLamps(20, "RRRR");
        assertLamps(23, "RRRR");
        assertLamps(24, "RRRR");
    }

    private void assertLamps(int hours, String expected) {
        assertEquals(expected, rule.lampsAt(new TimeOfDay(hours, 0, 0)).asText(), "hours=" + hours);
    }
}
