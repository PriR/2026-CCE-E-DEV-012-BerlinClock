package com.berlinclock.api;

import com.berlinclock.api.generated.model.BerlinClockResponse;
import com.berlinclock.domain.model.BerlinClockDisplay;

final class BerlinClockResponseMapper {

    private BerlinClockResponseMapper() {
    }

    static BerlinClockResponse toResponse(BerlinClockDisplay clock) {
        return new BerlinClockResponse()
                .seconds(clock.seconds().asText())
                .fiveHours(clock.fiveHours().asText())
                .oneHours(clock.oneHours().asText())
                .fiveMinutes(clock.fiveMinutes().asText())
                .oneMinutes(clock.oneMinutes().asText());
    }
}