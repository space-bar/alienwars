package com.spacebar.alienwars.spaceship.cli;

import com.spacebar.alienwars.spaceship.Spaceship;
import com.spacebar.alienwars.spaceship.SpaceshipFactory;
import com.spacebar.alienwars.spaceship.SpaceshipType;

import static com.spacebar.alienwars.spaceship.SpaceshipType.*;

public class CLISpaceshipFactory implements SpaceshipFactory {

    @Override
    public Spaceship createSpaceship(SpaceshipType spaceshipType) {
        if (spaceshipType != null) {
            //String display = null;
            switch (spaceshipType) {

                case ORION:
                    return new CLISpaceship(SpaceshipType.ORION, "(-^-)");

                case STARFLEET:
                    return new CLISpaceship(SpaceshipType.STARFLEET, "/-^-\\");

                case CYCLON:
                    return new CLISpaceship(SpaceshipType.CYCLON, "\\|-^-|/");

                case DESTROYER:
                    return new CLISpaceship(SpaceshipType.DESTROYER, "\\-\\/-/");
            }
            /*return display != null
                    ? new CLISpaceship(spaceshipType, display)
                    : new CLISpaceship(spaceshipType);*/
        }
        return null;
    }

   /* private Spaceship createCyclon() {
        new CLISpaceship(SpaceshipType.CYCLON);
        return new AbstractSpaceship(SpaceshipType.CYCLON) {

            @Override
            public String getDescription() {
                return super.getDescription();
            }
        };
    }*/
}
