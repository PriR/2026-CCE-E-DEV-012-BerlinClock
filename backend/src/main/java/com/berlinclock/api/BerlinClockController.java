package com.berlinclock.api;

import com.berlinclock.api.generated.BerlinClockApi;
import com.berlinclock.api.generated.model.BerlinClockResponse;
import com.berlinclock.application.BerlinClockService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implements the API generated from {@code openapi/berlin-clock.yaml}: the path and the
 * {@code time} parameter come from the contract.
 */
@AllArgsConstructor
@RestController
public class BerlinClockController implements BerlinClockApi {

    private final BerlinClockService berlinClockService;

    @Override
    public BerlinClockResponse getBerlinClock(String time) {
        var berlinClock = time == null ? berlinClockService.now() : berlinClockService.at(time);
        return BerlinClockResponseMapper.toResponse(berlinClock);
    }
}
