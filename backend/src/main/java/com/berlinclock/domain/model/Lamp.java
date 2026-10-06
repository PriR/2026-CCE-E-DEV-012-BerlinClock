package com.berlinclock.domain.model;

/** The state of a single lamp: off, or lit in one of the clock's two colours. */
public enum Lamp {
    OFF('O'),
    YELLOW('Y'),
    RED('R');

    private final char symbol;

    Lamp(char symbol) {
        this.symbol = symbol;
    }

    public char symbol() {
        return symbol;
    }
}
