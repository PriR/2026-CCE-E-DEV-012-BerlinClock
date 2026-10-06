package com.berlinclock.domain.rule;

import com.berlinclock.domain.model.LampRow;
import com.berlinclock.domain.model.TimeOfDay;

/** Decides which lamps of one row of the clock are lit at a given time. */
public interface LampRule {

    LampRow lampsAt(TimeOfDay time);
}
