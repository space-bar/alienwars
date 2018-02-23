package com.spacebar.alienwars.display.cli.impl;

import com.spacebar.alienwars.display.DisplayType;
import com.spacebar.alienwars.display.cli.AbstractCLIDisplay;
import com.spacebar.alienwars.io.IOStream;
import com.spacebar.alienwars.screen.Screen;

public class LoadSavedGame extends AbstractCLIDisplay {

    public static final String header = "" +
            ".____                     .___   ________                       \n" +
            "|    |    _________     __| _/  /  _____/_____    _____   ____  \n" +
            "|    |   /  _ \\__  \\   / __ |  /   \\  ___\\__  \\  /     \\_/ __ \\ \n" +
            "|    |__(  <_> ) __ \\_/ /_/ |  \\    \\_\\  \\/ __ \\|  Y Y  \\  ___/ \n" +
            "|_______ \\____(____  /\\____ |   \\______  (____  /__|_|  /\\___  >\n" +
            "        \\/         \\/      \\/          \\/     \\/      \\/     \\/";

    public LoadSavedGame() {
        super(DisplayType.LOAD_SAVED_GAME);
    }

    @Override
    public void display(Screen screen) {
        IOStream r = screen.getIOStream();
        drawHeader(screen, header.split(NEW_LINE));

        drawFooter(screen, APP_LOGO.split(NEW_LINE));
        r.writeLine("Enter Number:");

        readInput(screen);
    }

    private void readInput(Screen screen) {
        this.readInput(screen, (String input) -> {
        });
    }
}
