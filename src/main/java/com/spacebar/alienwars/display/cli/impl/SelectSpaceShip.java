package com.spacebar.alienwars.display.cli.impl;

import com.spacebar.alienwars.display.DisplayType;
import com.spacebar.alienwars.display.cli.AbstractCLIDisplay;
import com.spacebar.alienwars.game.Game;
import com.spacebar.alienwars.game.cli.CLIGame;
import com.spacebar.alienwars.io.IOStream;
import com.spacebar.alienwars.player.Player;
import com.spacebar.alienwars.player.PlayerType;
import com.spacebar.alienwars.screen.Screen;
import com.spacebar.alienwars.spaceship.Spaceship;
import com.spacebar.alienwars.spaceship.SpaceshipType;
import com.sun.org.apache.bcel.internal.generic.NEW;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.stream.IntStream;

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
        drawHeader(screen, header.split(NEW_LINE));

        drawBody(screen, buildBody(screen.getGame().getCharacterPlayer().getPlayerName()));

        drawFooter(screen, APP_LOGO.split(NEW_LINE));

        readInput(screen);
    }

    private String[] buildBody(String playerName) {
        String[] instructions = {"OK " + playerName,
                "lets get you a spaceship",
                "Select a spaceship for the list below",
                "Enter a number and hit enter.",
                "Or just hit enter to go back to Main Menu"};
        String[] ships = new String[CHARACTER_SPACESHIP_TYPES.length];
        IntStream.range(0, CHARACTER_SPACESHIP_TYPES.length).forEach(index -> {
            ships[0] = (index + 1) + "." + WHITE_SPACE + CHARACTER_SPACESHIP_TYPES[index].name();
        });

        String[] body = new String[instructions.length + ships.length + 1];
        System.arraycopy(instructions, 0, body, 0, instructions.length);
        System.arraycopy(ships, 0, body, instructions.length, ships.length);

        return body;
    }

    private void readInput(Screen screen) {
        screen.getIOStream().writeLine("Enter Number :");
        this.readInput(screen, (String input) -> {
            input = input != null ? input.trim() : null;

            if (input == null || input.isEmpty()) {
                screen.getDisplayExplorer().previous(screen);
            } else if (input.length() > 2) {
                Integer value = convertToInt(input);
                if (value != null && value > 0 && value <= CHARACTER_SPACESHIP_TYPES.length) {
                    assignSpaceship(screen, CHARACTER_SPACESHIP_TYPES[value - 1]);
                    screen.getDisplayExplorer().next(screen, DisplayType.PLAY_GAME);
                } else {
                    screen.getIOStream().writeLine("Come on! stop kidding arround");
                    throw new InputMismatchException();
                }
            } else {

                throw new InputMismatchException();
            }
        });
    }

    private void assignSpaceship(Screen screen, SpaceshipType spaceshipType) {
        Player player = screen.getGame().getCharacterPlayer();
        Spaceship spaceship = screen.getSpaceshipFactory().createSpaceship(spaceshipType);
        player.setSpaceship(spaceship);
    }

    private static final SpaceshipType[] CHARACTER_SPACESHIP_TYPES = Arrays.stream(SpaceshipType.values())
            .filter(spaceshipType -> !spaceshipType.isAlien())
            .toArray(size -> new SpaceshipType[size]);
}
