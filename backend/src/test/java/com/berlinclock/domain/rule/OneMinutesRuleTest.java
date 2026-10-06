package com.berlinclock.domain.rule;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.berlinclock.domain.model.TimeOfDay;
import org.junit.jupiter.api.Test;

class OneMinutesRuleTest {

    private final LampRule rule = new OneMinutesRule();

    @Test
    void noLampLitOnMultiplesOfFiveMinutes() {
        assertLamps(0, "OOOO");
        assertLamps(5, "OOOO");
        assertLamps(30, "OOOO");
    }

    @Test
    void oneLampLitWhenOneMinuteRemains() {
        assertLamps(1, "YOOO");
    }

    @Test
    void twoLampsLitWhenTwoMinutesRemain() {
        assertLamps(2, "YYOO");
    }

    @Test
    void threeLampsLitWhenThreeMinutesRemain() {
        assertLamps(3, "YYYO");
        assertLamps(13, "YYYO");
    }

    @Test
    void fourLampsLitWhenFourMinutesRemain() {
        assertLamps(4, "YYYY");
        assertLamps(9, "YYYY");
        assertLamps(59, "YYYY");
    }

    private void assertLamps(int minutes, String expected) {
        assertEquals(expected, rule.lampsAt(new TimeOfDay(0, minutes, 0)).asText(),
                "minutes=" + minutes);
    }
}
