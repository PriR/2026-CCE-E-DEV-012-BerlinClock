package com.berlinclock.domain.rule;

import com.berlinclock.domain.model.Lamp;
import com.berlinclock.domain.model.LampRow;
import com.berlinclock.domain.model.TimeOfDay;
import java.util.ArrayList;
import java.util.List;

/** Eleven lamps, each worth five minutes; every third one is red to mark a quarter of an hour. */
public final class FiveMinutesRule implements LampRule {

    private static final int LAMPS = 11;
    private static final int MINUTES_PER_LAMP = 5;
    private static final int LAMPS_PER_QUARTER_HOUR = 3;

    @Override
    public LampRow lampsAt(TimeOfDay time) {
        int litLamps = time.minutes() / MINUTES_PER_LAMP;

        List<Lamp> lamps = new ArrayList<>();
        for (int position = 1; position <= LAMPS; position++) {
            lamps.add(position <= litLamps ? litLampAt(position) : Lamp.OFF);
        }
        return new LampRow(lamps);
    }

    /** Positions are one-based: the third lamp is the first quarter-hour mark. */
    private static Lamp litLampAt(int position) {
        return position % LAMPS_PER_QUARTER_HOUR == 0 ? Lamp.RED : Lamp.YELLOW;
    }
}
