package com.spacebar.alienwars.spaceship.cli;

import com.spacebar.alienwars.spaceship.AbstractSpaceship;
import com.spacebar.alienwars.spaceship.SpaceshipType;

public class CLISpaceship extends AbstractSpaceship {

    public CLISpaceship(SpaceshipType type) {
        this(type, "/-^-\\");
    }

    public CLISpaceship(SpaceshipType type, String display) {
        super(type);
        setDescription(display);
    }


}
