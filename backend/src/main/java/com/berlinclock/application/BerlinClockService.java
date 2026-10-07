package com.berlinclock.application;

import com.berlinclock.domain.model.BerlinClockDisplay;

/** Input port: read the Berlin Clock for a given time, or for the current time. */
public interface BerlinClockService {

    /** @param time an {@code HH:mm:ss} time; 24:00:00 is the end of the day */
    BerlinClockDisplay at(String time);

    BerlinClockDisplay now();
}
