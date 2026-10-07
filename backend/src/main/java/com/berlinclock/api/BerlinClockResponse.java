package com.berlinclock.api;

import com.berlinclock.domain.model.BerlinClockDisplay;

/**
 * Response of {@code GET /api/berlin-clock}: one text per row of lamps
 * ({@code O} off, {@code Y} yellow, {@code R} red). Its shape is described by the
 * {@code BerlinClockResponse} schema in {@code openapi/berlin-clock.yaml}.
 */
public record BerlinClockResponse(String seconds,
                                  String fiveHours,
                                  String oneHours,
                                  String fiveMinutes,
                                  String oneMinutes) {

    BerlinClockResponse(BerlinClockDisplay clock) {
        this(clock.seconds().asText(),
             clock.fiveHours().asText(),
             clock.oneHours().asText(),
             clock.fiveMinutes().asText(),
             clock.oneMinutes().asText()
        );
    }
}
