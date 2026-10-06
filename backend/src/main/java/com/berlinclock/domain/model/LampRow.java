package com.berlinclock.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/** An immutable row of lamps. The first {@code litCount} lamps are lit, the rest are off. */
public record LampRow(List<Lamp> lamps) {

    public LampRow {
        lamps = List.copyOf(lamps);
    }

    /** A row of {@code size} lamps whose first {@code litCount} lamps are lit in one colour. */
    public static LampRow uniform(int size, int litCount, Lamp litLamp) {
        if (litCount < 0 || litCount > size) {
            throw new IllegalArgumentException(
                    "cannot light " + litCount + " lamps in a row of " + size);
        }
        List<Lamp> lamps = new ArrayList<>(Collections.nCopies(litCount, litLamp));
        lamps.addAll(Collections.nCopies(size - litCount, Lamp.OFF));
        return new LampRow(lamps);
    }

    /** The row as text, one character per lamp: {@code O}, {@code Y} or {@code R}. */
    public String asText() {
        return lamps.stream()
                .map(lamp -> String.valueOf(lamp.symbol()))
                .collect(Collectors.joining());
    }
}
