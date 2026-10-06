package com.berlinclock.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class LampRowTest {

    @Test
    void lampsSymbolsAreOffYellowAndRed() {
        assertEquals('O', Lamp.OFF.symbol());
        assertEquals('Y', Lamp.YELLOW.symbol());
        assertEquals('R', Lamp.RED.symbol());
    }

    @Test
    void uniformRowLightsTheFirstLampsWithTheGivenColour() {
        assertEquals("RROO", LampRow.uniform(4, 2, Lamp.RED).asText());
    }

    @Test
    void rowWithNoLitLampIsAllOff() {
        assertEquals("OOOO", LampRow.uniform(4, 0, Lamp.YELLOW).asText());
    }

    @Test
    void rowWithEveryLampLitIsAllOn() {
        assertEquals("YYYY", LampRow.uniform(4, 4, Lamp.YELLOW).asText());
    }

    @Test
    void cannotLightMoreLampsThanTheRowHas() {
        assertThrows(IllegalArgumentException.class, () -> LampRow.uniform(4, 5, Lamp.RED));
    }

    @Test
    void cannotLightANegativeNumberOfLamps() {
        assertThrows(IllegalArgumentException.class, () -> LampRow.uniform(4, -1, Lamp.RED));
    }

    @Test
    void rowsWithTheSameLampsAreEqual() {
        assertEquals(LampRow.uniform(4, 2, Lamp.RED), LampRow.uniform(4, 2, Lamp.RED));
    }
}
