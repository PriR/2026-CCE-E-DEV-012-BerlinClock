package com.berlinclock.domain.rule;

import com.berlinclock.domain.model.Lamp;
import com.berlinclock.domain.model.LampRow;
import com.berlinclock.domain.model.TimeOfDay;

/** Four yellow lamps, each worth one of the minutes left over after the five-minute row. */
public final class OneMinutesRule implements LampRule {

    private static final int LAMPS = 4;
    private static final int MINUTES_PER_FIVE_MINUTE_LAMP = 5;

    @Override
    public LampRow lampsAt(TimeOfDay time) {
        return LampRow.uniform(LAMPS, time.minutes() % MINUTES_PER_FIVE_MINUTE_LAMP, Lamp.YELLOW);
    }
}
