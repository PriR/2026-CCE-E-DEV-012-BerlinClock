package com.berlinclock.domain.rule;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.berlinclock.domain.model.TimeOfDay;
import org.junit.jupiter.api.Test;

class FiveMinutesRuleTest {

    private final LampRule rule = new FiveMinutesRule();

    @Test
    void noLampLitBelowFiveMinutes() {
        assertLamps(0, "OOOOOOOOOOO");
        assertLamps(4, "OOOOOOOOOOO");
    }

    @Test
    void firstTwoLampsAreYellow() {
        assertLamps(5, "YOOOOOOOOOO");
        assertLamps(10, "YYOOOOOOOOO");
    }

    @Test
    void thirdLampIsRedAtAQuarterOfAnHour() {
        assertLamps(15, "YYROOOOOOOO");
    }

    @Test
    void lampsFourAndFiveAreYellow() {
        assertLamps(20, "YYRYOOOOOOO");
        assertLamps(25, "YYRYYOOOOOO");
    }

    @Test
    void sixthLampIsRedAtHalfAnHour() {
        assertLamps(30, "YYRYYROOOOO");
    }

    @Test
    void lampsSevenAndEightAreYellow() {
        assertLamps(35, "YYRYYRYOOOO");
        assertLamps(40, "YYRYYRYYOOO");
    }

    @Test
    void ninthLampIsRedAtThreeQuartersOfAnHour() {
        assertLamps(45, "YYRYYRYYROO");
    }

    @Test
    void lampsTenAndElevenAreYellow() {
        assertLamps(50, "YYRYYRYYRYO");
        assertLamps(55, "YYRYYRYYRYY");
    }

    @Test
    void lastMinuteOfTheHourLightsTheSameLampsAsFiftyFive() {
        assertLamps(59, "YYRYYRYYRYY");
    }

    private void assertLamps(int minutes, String expected) {
        assertEquals(expected, rule.lampsAt(new TimeOfDay(0, minutes, 0)).asText(),
                "minutes=" + minutes);
    }
}
