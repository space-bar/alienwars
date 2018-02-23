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

    public About() {
        super(DisplayType.ABOUT);
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
