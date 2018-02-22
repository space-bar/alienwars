package com.spacebar.alienwars.spaceship;

public enum SpaceshipType {

    ORION(false),

    STARFLEET(false),

    CYCLON(true),

    DESTROYER(true);

    private final boolean alien;

    SpaceshipType(boolean alien) {
        this.alien = alien;
    }

    public boolean isAlien() {
        return alien;
    }
}
