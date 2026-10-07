package com.berlinclock.infrastructure;

import com.berlinclock.application.CurrentTime;
import com.berlinclock.domain.model.TimeOfDay;
import java.time.Clock;
import java.time.LocalTime;
import org.springframework.stereotype.Component;

/** Adapter that tells the application the time according to a {@link Clock}. */
@Component
public class SystemCurrentTime implements CurrentTime {

    private final Clock clock;

    public SystemCurrentTime(Clock clock) {
        this.clock = clock;
    }

    @Override
    public TimeOfDay now() {
        LocalTime now = LocalTime.now(clock);
        return new TimeOfDay(now.getHour(), now.getMinute(), now.getSecond());
    }
}
