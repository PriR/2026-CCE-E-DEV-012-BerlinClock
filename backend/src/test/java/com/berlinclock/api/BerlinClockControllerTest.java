package com.berlinclock.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.berlinclock.application.BerlinClockServiceImpl;
import com.berlinclock.application.CurrentTime;
import com.berlinclock.domain.model.TimeOfDay;
import com.berlinclock.domain.service.BerlinClock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(BerlinClockController.class)
@Import(BerlinClockServiceImpl.class)
class BerlinClockControllerTest {

    @TestConfiguration
    static class FixedTimeConfig {

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
    private MockMvc mockMvc;

    @Test
    void returnsTheLampsForTheRequestedTime() throws Exception {
        mockMvc.perform(get("/api/berlin-clock").param("time", "23:59:59"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                                  {
                                                      "seconds": "O",
                                                      "fiveHours": "RRRR",
                                                      "oneHours": "RRRO",
                                                      "fiveMinutes": "YYRYYRYYRYY",
                                                      "oneMinutes": "YYYY"
                                                  }
                                                  """, true));
    }

    @Test
    void acceptsTwentyFourHundred() throws Exception {
        mockMvc.perform(get("/api/berlin-clock").param("time", "24:00:00"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                                  {
                                                      "seconds": "Y",
                                                      "fiveHours": "RRRR",
                                                      "oneHours": "RRRR",
                                                      "fiveMinutes": "OOOOOOOOOOO",
                                                      "oneMinutes": "OOOO"
                                                  }
                                                  """, true));
    }

    @Test
    void usesTheCurrentTimeWhenNoTimeIsGiven() throws Exception {
        mockMvc.perform(get("/api/berlin-clock"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                                  {
                                                      "seconds": "O",
                                                      "fiveHours": "RROO",
                                                      "oneHours": "RRRO",
                                                      "fiveMinutes": "YYROOOOOOOO",
                                                      "oneMinutes": "YYOO"
                                                  }
                                                  """, true));
    }

    @Test
    void rejectsAnInvalidTimeWithBadRequest() throws Exception {
        mockMvc.perform(get("/api/berlin-clock").param("time", "25:00:00"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                                                  { "detail": "hours must be between 0 and 24: 25" }
                                                  """));
    }

    @Test
    void rejectsAMalformedTimeWithBadRequest() throws Exception {
        mockMvc.perform(get("/api/berlin-clock").param("time", "noon"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("""
                                                  { "detail": "time must use the HH:mm:ss format" }
                                                  """));
    }
}
