package com.spacebar.alienwars.display.cli.impl;

import com.spacebar.alienwars.display.DisplayType;
import com.spacebar.alienwars.screen.Screen;
import com.spacebar.alienwars.display.cli.AbstractCLIDisplay;


import java.util.InputMismatchException;

public class Home extends AbstractCLIDisplay {

    public static final String HEADER = "" +
            " __      __       .__                               \n" +
            "/  \\    /  \\ ____ |  |   ____  ____   _____   ____  \n" +
            "\\   \\/\\/   // __ \\|  | _/ ___\\/  _ \\ /     \\_/ __ \\ \n" +
            " \\        /\\  ___/|  |_\\  \\__(  <_> )  Y Y  \\  ___/ \n" +
            "  \\__/\\  /  \\___  >____/\\___  >____/|__|_|  /\\___  >\n" +
            "       \\/       \\/          \\/            \\/     \\/";

    public Home() {
        super(DisplayType.HOME);
    }

    public void display(Screen screen) {
        drawHeader(screen, HEADER.split("\\n"));
        drawBody(screen,
                "What do you want to do ?",
                "Type a number and hit enter.",
                " ",
                "1. Start New Game",
                "2. Load Saved Game",
                "3. Help",
                "4. About",
                "",
                "Just type EXIT to quit", WHITE_SPACE);
        drawFooter(screen, APP_LOGO.split("\\n"));

        readInput(screen);
    }

    private void readInput(Screen screen) {
        screen.getIOStream().writeLine("Enter Menu Number :");
        this.readInput(screen, (String input) -> {

            switch (input) {
                case "1":
                    screen.getDisplayExplorer().next(screen, DisplayType.NEW_GAME);
                    break;
                case "2":
                    screen.getDisplayExplorer().next(screen, DisplayType.LOAD_SAVED_GAME);
                    break;
                case "3":
                    screen.getDisplayExplorer().next(screen, DisplayType.HELP);
                    break;
                case "4":
                    screen.getDisplayExplorer().next(screen, DisplayType.ABOUT);
                    break;
                default:
                    invalidInput(screen);

            }
        });
    }

    private void invalidInput(Screen screen) {
        screen.getIOStream().writeLine("I can do this all day");
        throw new InputMismatchException();
    }
}
