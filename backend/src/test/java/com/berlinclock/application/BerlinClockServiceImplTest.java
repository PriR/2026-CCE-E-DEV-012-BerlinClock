package com.berlinclock.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.berlinclock.domain.model.BerlinClockDisplay;
import com.berlinclock.domain.model.InvalidTimeException;
import com.berlinclock.domain.model.TimeOfDay;
import com.berlinclock.domain.service.BerlinClock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@SpringBootTest(classes = BerlinClockServiceImplTest.TestConfig.class)
class BerlinClockServiceImplTest {

    @Configuration
    @Import(BerlinClockServiceImpl.class)
    static class TestConfig {

        @Bean
        BerlinClock berlinClock() {
            return BerlinClock.standard();
        }

        @Bean
        CurrentTime currentTime() {
            return () -> new TimeOfDay(13, 17, 1);
        }
    }

    @Autowired
    private BerlinClockService berlinClockService;

    @Test
    void showsTheRequestedTime() {
        BerlinClockDisplay display = berlinClockService.at("23:59:59");

        assertEquals("O", display.seconds().asText());
        assertEquals("RRRR", display.fiveHours().asText());
        assertEquals("RRRO", display.oneHours().asText());
        assertEquals("YYRYYRYYRYY", display.fiveMinutes().asText());
        assertEquals("YYYY", display.oneMinutes().asText());
    }

    @Test
    void showsTheCurrentTimeFromTheCurrentTimePort() {
        BerlinClockDisplay display = berlinClockService.now();

        assertEquals("RROO", display.fiveHours().asText());
        assertEquals("RRRO", display.oneHours().asText());
        assertEquals("YYROOOOOOOO", display.fiveMinutes().asText());
        assertEquals("YYOO", display.oneMinutes().asText());
    }

    @Test
    void rejectsAnInvalidRequestedTime() {
        assertThrows(InvalidTimeException.class, () -> berlinClockService.at("25:00:00"));
    }
}
