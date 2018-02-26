package com.spacebar.alienwars.spaceship.cli;

import com.spacebar.alienwars.spaceship.Spaceship;
import com.spacebar.alienwars.spaceship.SpaceshipFactory;
import com.spacebar.alienwars.spaceship.SpaceshipType;

import static com.spacebar.alienwars.spaceship.SpaceshipType.*;

public class CLISpaceshipFactory implements SpaceshipFactory {

    @Override
    public Spaceship createSpaceship(SpaceshipType spaceshipType) {
        if (spaceshipType != null) {
            switch (spaceshipType) {

                case ORION:
                    return new CLISpaceship(spaceshipType, "(-^-)","(~*~)");

                case STARFLEET:
                    return new CLISpaceship(spaceshipType, "[-/-^-\\-]","[~/~*~)\\~]");

                case OPTIMUS:
                    return new CLISpaceship(spaceshipType, "(-|-^-|-)","(~|~*~|~)");

                case CYCLON:
                    return new CLISpaceship(spaceshipType, "\\-o_o-/","\\~*_*~/");

                case DESTROYER:
                    return new CLISpaceship(spaceshipType, "{-\\-o_o-/-}","*~\\~*_*~/~*");
            }
        }
        return null;
    }

}
