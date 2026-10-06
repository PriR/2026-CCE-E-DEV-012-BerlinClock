package com.berlinclock.domain.rule;

import com.berlinclock.domain.model.Lamp;
import com.berlinclock.domain.model.LampRow;
import com.berlinclock.domain.model.TimeOfDay;

/** The single yellow lamp blinks: lit on even seconds, off on odd seconds. */
public final class SecondsRule implements LampRule {

    @Override
    public LampRow lampsAt(TimeOfDay time) {
        boolean evenSecond = time.seconds() % 2 == 0;
        return LampRow.uniform(1, evenSecond ? 1 : 0, Lamp.YELLOW);
    }
}
