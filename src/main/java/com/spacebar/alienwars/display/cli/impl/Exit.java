package com.spacebar.alienwars.display.cli.impl;

import com.spacebar.alienwars.display.DisplayType;
import com.spacebar.alienwars.display.cli.AbstractCLIDisplay;
import com.spacebar.alienwars.game.Game;
import com.spacebar.alienwars.game.cli.CLIGame;
import com.spacebar.alienwars.io.IOStream;
import com.spacebar.alienwars.player.Player;
import com.spacebar.alienwars.screen.Screen;

public class Exit extends AbstractCLIDisplay {

    public static final String header = "" +
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
        drawHeader(screen, header.split(NEW_LINE));

        drawFooter(screen, APP_LOGO.split(NEW_LINE));

    }

}
