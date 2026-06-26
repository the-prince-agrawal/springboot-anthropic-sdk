package com.prince.anthropic.sdk.enums;

public enum Temperature {

    LESS_CREATIVE(0.2),
    BALANCED(0.5),
    MORE_CREATIVE(0.9);

    private final double value;

    Temperature(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}