package com.berlinclock.domain.rule;

import com.berlinclock.domain.model.Lamp;
import com.berlinclock.domain.model.LampRow;
import com.berlinclock.domain.model.TimeOfDay;

/** Four red lamps, each worth five hours. */
public final class FiveHoursRule implements LampRule {

    private static final int LAMPS = 4;
    private static final int HOURS_PER_LAMP = 5;

    @Override
    public LampRow lampsAt(TimeOfDay time) {
        return LampRow.uniform(LAMPS, time.hours() / HOURS_PER_LAMP, Lamp.RED);
    }
}
