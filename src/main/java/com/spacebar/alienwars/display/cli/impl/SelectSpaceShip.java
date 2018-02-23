package com.spacebar.alienwars.display.cli.impl;

import com.spacebar.alienwars.display.DisplayType;
import com.spacebar.alienwars.display.cli.AbstractCLIDisplay;
import com.spacebar.alienwars.game.Game;
import com.spacebar.alienwars.game.cli.CLIGame;
import com.spacebar.alienwars.io.IOStream;
import com.spacebar.alienwars.player.Player;
import com.spacebar.alienwars.player.PlayerType;
import com.spacebar.alienwars.screen.Screen;

public class SelectSpaceShip extends AbstractCLIDisplay {

    public static final String header = "" +
            " _________                           _________.__    .__        \n" +
            " /   _____/__________    ____  ____  /   _____/|  |__ |__|_____  \n" +
            " \\_____  \\\\____ \\__  \\ _/ ___\\/ __ \\ \\_____  \\ |  |  \\|  \\____ \\ \n" +
            " /        \\  |_> > __ \\\\  \\__\\  ___/ /        \\|   Y  \\  |  |_> >\n" +
            "/_______  /   __(____  /\\___  >___  >_______  /|___|  /__|   __/ \n" +
            "        \\/|__|       \\/     \\/    \\/        \\/      \\/   |__|    ";

    public SelectSpaceShip() {
        super(DisplayType.SELECT_SPACE_SHIP);
    }

    @Override
    public void display(Screen screen) {
        IOStream r = screen.getIOStream();
        drawHeader(screen, header.split("\\n"));
        drawBody(screen,
                "OK " + screen.getGame().getCharacterPlayer().getPlayerName(),
                "lets get you a spaceship",
                "Select a spaceship for the list below",
                "Enter a number and hit enter.",
                " ",
                "1. Play",
                "2. Load Saved Game",
                "3. Help",
                "4. About",
                "5. Back");
        drawFooter(screen, APP_LOGO.split("\\n"));
        r.writeLine("Enter :");

        readInput(screen);
    }

    private void readInput(Screen screen) {
        this.readInput(screen, (String name) -> {
            name = name != null ? name.trim() : null;

            if (name == null || name.isEmpty()) {
                screen.getDisplayExplorer().previous(screen);
            } else if (name.length() > 2) {
                Player player = screen.getPlayerFactory().createPlayer(PlayerType.CHARACTER, name);
                Game game = new CLIGame(player, null);

                screen.getDisplayExplorer().next(screen, DisplayType.SELECT_SPACE_SHIP);
            } else {
                screen.getIOStream().writeLine("Enter Name :");
            }
        });
    }
}
