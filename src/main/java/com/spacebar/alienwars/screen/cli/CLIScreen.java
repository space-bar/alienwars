package com.spacebar.alienwars.screen.cli;

import com.spacebar.alienwars.display.cli.CLIDisplayFactory;
import com.spacebar.alienwars.io.cli.CLIIOStream;
import com.spacebar.alienwars.screen.AbstractScreen;
import com.spacebar.alienwars.display.DisplayFactory;
import com.spacebar.alienwars.io.IOStream;
import com.spacebar.alienwars.spaceship.SpaceshipFactory;
import com.spacebar.alienwars.spaceship.cli.CLISpaceshipFactory;

public class CLIScreen extends AbstractScreen {

    private IOStream ioStream;

    private SpaceshipFactory spaceshipFactory;

    private DisplayFactory displayFactory;


    public CLIScreen(int width, int height) {
        super(width, height);
    }

    public IOStream getIOStream() {
        if (ioStream == null) {
            ioStream = new CLIIOStream();
        }
        return ioStream;
    }

    @Override
    public DisplayFactory getDisplayFactory() {
        if (displayFactory == null) {
            displayFactory = new CLIDisplayFactory();
        }

        return displayFactory;
    }

    public SpaceshipFactory getSpaceshipFactory() {
        if (spaceshipFactory == null) {
            spaceshipFactory = new CLISpaceshipFactory();
        }
        return spaceshipFactory;
    }

}
