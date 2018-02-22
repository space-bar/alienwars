package com.spacebar.alienwars.display.cli.impl;

import com.spacebar.alienwars.display.DisplayType;
import com.spacebar.alienwars.io.IOStream;
import com.spacebar.alienwars.screen.Screen;
import com.spacebar.alienwars.display.cli.AbstractCLIDisplay;

import java.util.InputMismatchException;

public class About extends AbstractCLIDisplay {

    public static final String header = "" +
            " _____ ___.                  __   \n" +
            "  /  _  \\\\_ |__   ____  __ ___/  |_ \n" +
            " /  /_\\  \\| __ \\ /  _ \\|  |  \\   __\\\n" +
            "/    |    \\ \\_\\ (  <_> )  |  /|  |  \n" +
            "\\____|__  /___  /\\____/|____/ |__|  \n" +
            "        \\/    \\/                    \\033[0m";

    @Override
    public void display(Screen screen) {
        IOStream r = screen.getIOStream();
        drawHeader(screen, header.split("\\n"));
        drawBody(screen,
                "What do you want to do ?",
                "Type a number and hit enter.",
                " ",
                "1. Start New Game",
                "2. Load Saved Game",
                "3. Back",
                "4. Help",
                "5. Exit");
        drawFooter(screen, APP_LOGO.split("\\n"));
        r.writeLine("Enter Menu Number :");

        readInput(screen);
    }


    private void readInput(Screen screen) {
        this.readIntInput(screen, (no) -> {
            switch (no) {
                case 1:
                    screen.getDisplayExplorer().next(screen, DisplayType.NEW_GAME);
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    throw new InputMismatchException();
            }
        });
    }
}
