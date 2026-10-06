package com.berlinclock.domain.service;

import com.berlinclock.domain.model.BerlinClockDisplay;
import com.berlinclock.domain.model.TimeOfDay;
import com.berlinclock.domain.rule.FiveHoursRule;
import com.berlinclock.domain.rule.FiveMinutesRule;
import com.berlinclock.domain.rule.LampRule;
import com.berlinclock.domain.rule.OneHoursRule;
import com.berlinclock.domain.rule.OneMinutesRule;
import com.berlinclock.domain.rule.SecondsRule;

/** Shows a time on the Berlin Clock by asking one rule per row which lamps to light. */
public final class BerlinClock {

    private final LampRule seconds;
    private final LampRule fiveHours;
    private final LampRule oneHours;
    private final LampRule fiveMinutes;
    private final LampRule oneMinutes;

    public BerlinClock(
            LampRule seconds,
            LampRule fiveHours,
            LampRule oneHours,
            LampRule fiveMinutes,
            LampRule oneMinutes) {
        this.seconds = seconds;
        this.fiveHours = fiveHours;
        this.oneHours = oneHours;
        this.fiveMinutes = fiveMinutes;
        this.oneMinutes = oneMinutes;
    }

    /** The clock with the standard Berlin Clock rules. */
    public static BerlinClock standard() {
        return new BerlinClock(
                new SecondsRule(),
                new FiveHoursRule(),
                new OneHoursRule(),
                new FiveMinutesRule(),
                new OneMinutesRule());
    }

    public BerlinClockDisplay display(TimeOfDay time) {
        return new BerlinClockDisplay(
                time,
                seconds.lampsAt(time),
                fiveHours.lampsAt(time),
                oneHours.lampsAt(time),
                fiveMinutes.lampsAt(time),
                oneMinutes.lampsAt(time));
    }
}
