package com.spacebar.alienwars.spaceship.cli;

import com.spacebar.alienwars.spaceship.AbstractSpaceship;
import com.spacebar.alienwars.spaceship.SpaceshipType;

import java.awt.*;

public class CLISpaceship extends AbstractSpaceship {

    public CLISpaceship(SpaceshipType type) {
        this(type, "/-^-\\");
    }

    public CLISpaceship(SpaceshipType type, String display) {
        super(type);
        setDisplay(display);
        setCoordinate(new Point());
    }

}
