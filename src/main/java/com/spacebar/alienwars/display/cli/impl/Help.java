package com.spacebar.alienwars.display.cli.impl;

import com.spacebar.alienwars.display.DisplayType;
import com.spacebar.alienwars.display.cli.AbstractCLIDisplay;
import com.spacebar.alienwars.screen.Screen;

import java.util.InputMismatchException;

public class Help extends AbstractCLIDisplay {

    public static final String HEADER = "" +
            " ___ ___         .__          \n" +
            " /   |   \\   ____ |  | ______  \n" +
            "/    ~    \\_/ __ \\|  | \\____ \\ \n" +
            "\\    Y    /\\  ___/|  |_|  |_> >\n" +
            " \\___|_  /  \\___  >____/   __/ \n" +
            "       \\/       \\/     |__|";

    public Help() {
        super(DisplayType.HELP);
    }

    @Override
    public void display(Screen screen) {
        drawHeader(screen, HEADER.split(NEW_LINE));

        drawBody(screen, false, RABBIT.split(NEW_LINE));

        drawBody(screen, false, "There is an Alien invasion on your planet");
        drawTagGroup(screen, 1,
WHITE_SPACE,
                " HOME       | Goto the home main Menu",
                " BACK       | Goto the prevoius display",
                " EXIT       | terminate the Game application");
        drawFooter(screen, APP_LOGO.split(NEW_LINE));

        readInput(screen);
    }

    private void readInput(Screen screen) {
        screen.getIOStream().write(NEW_LINE + "Enter:");
        this.readInput(screen, (String input) -> {
            screen.getIOStream().writeLine("Enter:");
            throw new InputMismatchException();
        });
    }
}
