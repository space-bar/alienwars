package com.spacebar.alienwars.display.cli.impl;

import com.spacebar.alienwars.display.DisplayType;
import com.spacebar.alienwars.display.cli.AbstractCLIDisplay;
import com.spacebar.alienwars.game.AbstractGame;
import com.spacebar.alienwars.game.Game;
import com.spacebar.alienwars.screen.Screen;
import com.spacebar.alienwars.util.FileUtils;
import com.spacebar.alienwars.util.GameUtils;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Properties;

public class SaveGame extends AbstractCLIDisplay {
    public static final String HEADER = "" +
            "  _________                         ________                          \n" +
            " /   _____/_____  ___  __  ____    /  _____/ _____     _____    ____  \n" +
            " \\_____  \\ \\__  \\ \\  \\/ /_/ __ \\  /   \\  ___ \\__  \\   /     \\ _/ __ \\ \n" +
            " /        \\ / __ \\_\\   / \\  ___/  \\    \\_\\  \\ / __ \\_|  Y Y  \\\\  ___/ \n" +
            "/_______  /(____  / \\_/   \\___  >  \\______  /(____  /|__|_|  / \\___  >\n" +
            "        \\/      \\/            \\/          \\/      \\/       \\/      \\/ ";

    public SaveGame() {
        super(DisplayType.HELP);
    }

    @Override
    public void display(Screen screen) {
        String gameName = screen.getGame().getName();

        drawHeader(screen, HEADER.split(NEW_LINE));
        drawBody(screen, RABBIT.split(NEW_LINE));
        drawBody(screen, "Save your Game ", gameName != null ? "Press Enter to use your Player Name:" + gameName:WHITE_SPACE,
                "Enter a name not lesser than 3 characters", WHITE_SPACE,
                "[Just type EXIT to quit Or hit Back without saving]", WHITE_SPACE);

        drawFooter(screen, APP_LOGO.split(NEW_LINE));

        readInput(screen);
    }

    private void readInput(Screen screen) {
        screen.getIOStream().write(NEW_LINE + "Save Game As:");

        this.readInput(screen, (String input) -> {
            input = trimValue(input);
            Game game = screen.getGame();
            if (input.isEmpty() && game.getName() != null) {
                doSaveGame(screen, input);
            } else if (input.length() > 2) {
                doSaveGame(screen, input);
            } else {
                screen.getIOStream().writeLine("I'm a program, I got all the time");
                throw new InputMismatchException();
            }
        });
    }

    private void doSaveGame(Screen screen, String input) {
        input = trimValue(input);
        Game game = screen.getGame();
        if (GameUtils.getManifest().contains(input)) {
            screen.getIOStream().writeLine("Opps! so seems you have already used this name previously");
            throw new InputMismatchException();
        }
        if (game instanceof AbstractGame) {
            ((AbstractGame) game).setName(input);
        }
        saveGame(input, game);
        screen.getDisplayExplorer().previous(screen);
    }



    private void saveGame(String name, Game game) {
        try {
            String fileName = String.valueOf(System.currentTimeMillis());
            GameUtils.getManifest().put(name, fileName);

            FileUtils.writeAsObject(fileName, game);
            FileUtils.writeManifest(GameUtils.getManifest());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
