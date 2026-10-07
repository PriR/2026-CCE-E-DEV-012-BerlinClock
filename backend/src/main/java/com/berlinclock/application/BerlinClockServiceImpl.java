package com.berlinclock.application;

import com.berlinclock.domain.model.BerlinClockDisplay;
import com.berlinclock.domain.model.TimeOfDay;
import com.berlinclock.domain.service.BerlinClock;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class BerlinClockServiceImpl implements BerlinClockService {

    private final BerlinClock berlinClock;
    private final CurrentTime currentTime;

    @Override
    public BerlinClockDisplay at(String time) {
        return berlinClock.display(TimeOfDay.parse(time));
    }

    @Override
    public BerlinClockDisplay now() {
        return berlinClock.display(currentTime.now());
    }
}
