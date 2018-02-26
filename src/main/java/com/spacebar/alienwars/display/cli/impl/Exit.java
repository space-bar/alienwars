package com.spacebar.alienwars.display.cli.impl;
import com.spacebar.alienwars.display.DisplayType;
import com.spacebar.alienwars.display.cli.AbstractCLIDisplay;
import com.spacebar.alienwars.io.IOStream;
import com.spacebar.alienwars.screen.Screen;

public class Exit extends AbstractCLIDisplay {

    public static final String HEADER = "" +
            " ________                  ._____________               \n" +
            " /  _____/  ____   ____   __| _/\\______   \\___.__. ____  \n" +
            "/   \\  ___ /  _ \\ /  _ \\ / __ |  |    |  _<   |  |/ __ \\ \n" +
            "\\    \\_\\  (  <_> |  <_> ) /_/ |  |    |   \\\\___  \\  ___/ \n" +
            " \\______  /\\____/ \\____/\\____ |  |______  // ____|\\___  >\n" +
            "        \\/                   \\/         \\/ \\/         \\/";

    public Exit() {
        super(DisplayType.EXIT);
    }

    @Override
    public void display(Screen screen) {
        IOStream r = screen.getIOStream();
        drawHeader(screen, HEADER.split(NEW_LINE));
        drawBody(screen, RABBIT.split(NEW_LINE));
        r.writeLine(WHITE_SPACE);
        drawFooter(screen, APP_LOGO.split(NEW_LINE));
        System.exit(0);
    }

}
