package com.spacebar.alienwars.spaceship.cli;

import com.spacebar.alienwars.spaceship.AbstractSpaceship;
import com.spacebar.alienwars.spaceship.SpaceshipType;

import java.awt.*;

public class CLISpaceship extends AbstractSpaceship {
    private String onDestroyDisplay;

    public CLISpaceship(SpaceshipType type) {
        this(type, "/-^-\\");
    }

    public CLISpaceship(SpaceshipType type, String display) {
        super(type, display);
    }

    public CLISpaceship(SpaceshipType type, String display, String onDestroyDisplay) {
        super(type, display);
        this.onDestroyDisplay = onDestroyDisplay;
    }

    @Override
    public String getDisplay() {
        if (this.isDestroyed() && onDestroyDisplay != null)
            return onDestroyDisplay;
        return super.getDisplay();
    }
}
