package com.berlinclock.domain.rule;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.berlinclock.domain.model.TimeOfDay;
import org.junit.jupiter.api.Test;

class OneHoursRuleTest {

    private final LampRule rule = new OneHoursRule();

    @Test
    void noLampLitOnMultiplesOfFiveHours() {
        assertLamps(0, "OOOO");
        assertLamps(5, "OOOO");
        assertLamps(10, "OOOO");
    }

    @Test
    void oneLampLitWhenOneHourRemains() {
        assertLamps(1, "ROOO");
    }

    @Test
    void twoLampsLitWhenTwoHoursRemain() {
        assertLamps(2, "RROO");
    }

    @Test
    void threeLampsLitWhenThreeHoursRemain() {
        assertLamps(3, "RRRO");
        assertLamps(13, "RRRO");
        assertLamps(23, "RRRO");
    }

    @Test
    void fourLampsLitWhenFourHoursRemain() {
        assertLamps(4, "RRRR");
        assertLamps(9, "RRRR");
    }

    @Test
    void endOfDayLightsAllFourLamps() {
        assertLamps(24, "RRRR");
    }

    private void assertLamps(int hours, String expected) {
        assertEquals(expected, rule.lampsAt(new TimeOfDay(hours, 0, 0)).asText(),
                "hours=" + hours);
    }
}
