package com.spacebar.alienwars.display.cli.impl;

import com.spacebar.alienwars.display.DisplayType;
import com.spacebar.alienwars.screen.Screen;
import com.spacebar.alienwars.display.cli.AbstractCLIDisplay;

import java.util.InputMismatchException;

public class About extends AbstractCLIDisplay {

    public static final String HEADER = "" +
            " _____ ___.                  __   \n" +
            "  /  _  \\\\_ |__   ____  __ ___/  |_ \n" +
            " /  /_\\  \\| __ \\ /  _ \\|  |  \\   __\\\n" +
            "/    |    \\ \\_\\ (  <_> )  |  /|  |  \n" +
            "\\____|__  /___  /\\____/|____/ |__|  \n" +
            "        \\/    \\/                    ";

    public About() {
        super(DisplayType.ABOUT);
    }

    @Override
    public void display(Screen screen) {
        drawHeader(screen, HEADER.split(NEW_LINE));
        drawBody(screen, false, RABBIT.split(NEW_LINE));
        drawBody(screen, false, APP_LOGO.split(NEW_LINE));
        drawFooter(screen, "@copyright spacebar Inc.");

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
