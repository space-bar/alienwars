package com.spacebar.alienwars.screen.cli;

import com.spacebar.alienwars.display.cli.CLIDisplayFactory;
import com.spacebar.alienwars.game.Game;
import com.spacebar.alienwars.game.cli.CLIGame;
import com.spacebar.alienwars.io.cli.CLIIOStream;
import com.spacebar.alienwars.screen.AbstractScreen;
import com.spacebar.alienwars.display.DisplayFactory;
import com.spacebar.alienwars.io.IOStream;

public class CLIScreen extends AbstractScreen {

    private IOStream ioStream;


    private DisplayFactory displayFactory;

    private Game game;

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

    @Override
    public Game getGame() {
        if (game == null) {
            game = new CLIGame(null,null);
        }
        return null;
    }
}
