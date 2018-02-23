package com.spacebar.alienwars.display.cli.impl;

import com.spacebar.alienwars.display.DisplayType;
import com.spacebar.alienwars.display.cli.AbstractCLIDisplay;
import com.spacebar.alienwars.io.IOStream;
import com.spacebar.alienwars.screen.Screen;

public class Help extends AbstractCLIDisplay {

    public static final String header = "" +
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
