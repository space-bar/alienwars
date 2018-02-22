package com.spacebar.alienwars.display.cli.impl;

import com.spacebar.alienwars.display.DisplayType;
import com.spacebar.alienwars.display.Displayable;
import com.spacebar.alienwars.display.cli.AbstractCLIDisplay;
import com.spacebar.alienwars.game.Game;
import com.spacebar.alienwars.game.cli.CLIGame;
import com.spacebar.alienwars.io.IOStream;
import com.spacebar.alienwars.player.Player;
import com.spacebar.alienwars.player.PlayerType;
import com.spacebar.alienwars.screen.Screen;

import java.util.InputMismatchException;

public class NewGame extends AbstractCLIDisplay {

    public static final String header = "" +
            "__________.____       _____ _____.___._____________________ \n" +
            "\\______   \\    |     /  _  \\\\__  |   |\\_   _____/\\______   \\\n" +
            " |     ___/    |    /  /_\\  \\/   |   | |    __)_  |       _/\n" +
            " |    |   |    |___/    |    \\____   | |        \\ |    |   \\\n" +
            " |____|   |_______ \\____|__  / ______|/_______  / |____|_  /\n" +
            "                  \\/       \\/\\/               \\/         \\/ ";

    public NewGame() {
        super(DisplayType.NEW_GAME);
    }

    @Override
    public void display(Screen screen) {
        IOStream r = screen.getIOStream();
        drawHeader(screen, header.split("\\n"));
        drawBody(screen,
                "Hmmmm so you wanna give it a spin." +
                        "Enter a name, not lesser than 3 characters." +
                        "Or just hit enter to go back to Main Menu\n");
        drawFooter(screen, APP_LOGO.split("\\n"));
        r.writeLine("Enter Name :");

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
