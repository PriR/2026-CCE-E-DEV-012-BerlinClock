package com.berlinclock.domain.rule;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.berlinclock.domain.model.TimeOfDay;
import org.junit.jupiter.api.Test;

class SecondsRuleTest {

    private final LampRule rule = new SecondsRule();

    @Test
    void lampIsLitOnEvenSeconds() {
        assertLamp(0, "Y");
        assertLamp(2, "Y");
        assertLamp(30, "Y");
        assertLamp(58, "Y");
    }

    @Test
    void lampIsOffOnOddSeconds() {
        assertLamp(1, "O");
        assertLamp(57, "O");
        assertLamp(59, "O");
    }

    private void assertLamp(int seconds, String expected) {
        assertEquals(expected, rule.lampsAt(new TimeOfDay(0, 0, seconds)).asText(),
                "seconds=" + seconds);
    }
}
