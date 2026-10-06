package com.berlinclock.domain.rule;

import com.berlinclock.domain.model.Lamp;
import com.berlinclock.domain.model.LampRow;
import com.berlinclock.domain.model.TimeOfDay;

/** Four red lamps, each worth one of the hours left over after the five-hour row. */
public final class OneHoursRule implements LampRule {

    private static final int LAMPS = 4;
    private static final int HOURS_PER_FIVE_HOUR_LAMP = 5;

    @Override
    public LampRow lampsAt(TimeOfDay time) {
        return LampRow.uniform(LAMPS, time.hours() % HOURS_PER_FIVE_HOUR_LAMP, Lamp.RED);
    }
}
