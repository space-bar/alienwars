package com.spacebar.alienwars.display.cli.impl;

import com.spacebar.alienwars.display.DisplayType;
import com.spacebar.alienwars.display.cli.AbstractCLIDisplay;
import com.spacebar.alienwars.game.Game;
import com.spacebar.alienwars.game.cli.CLIGame;
import com.spacebar.alienwars.io.IOStream;
import com.spacebar.alienwars.player.Player;
import com.spacebar.alienwars.player.PlayerType;
import com.spacebar.alienwars.screen.Screen;
import com.spacebar.alienwars.spaceship.SpaceshipType;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.stream.IntStream;

public class NewGame extends AbstractCLIDisplay {

    public static final String header = "" +
            "_______                    ________                       \n" +
            " \\      \\   ______  _  __  /  _____/_____    _____   ____  \n" +
            " /   |   \\_/ __ \\ \\/ \\/ / /   \\  ___\\__  \\  /     \\_/ __ \\ \n" +
            "/    |    \\  ___/\\     /  \\    \\_\\  \\/ __ \\|  Y Y  \\  ___/ \n" +
            "\\____|__  /\\___  >\\/\\_/    \\______  (____  /__|_|  /\\___  >\n" +
            "        \\/     \\/                 \\/     \\/      \\/     \\/";

    public NewGame() {
        super(DisplayType.NEW_GAME);
    }

    @Override
    public void display(Screen screen) {
        IOStream r = screen.getIOStream();
        drawHeader(screen, header.split(NEW_LINE));
        drawBody(screen,
                "Hmmmm so you wanna give it a spin.",
                "Enter a name, not lesser than 3 characters.",
                "Or just hit enter to go back to Main Menu");

        drawFooter(screen, APP_LOGO.split(NEW_LINE));

        readInput(screen);
    }

    private void readInput(Screen screen) {
        screen.getIOStream().writeLine("Enter Name :");
        this.readInput(screen, (String input) -> {
            input = input != null ? input.trim() : null;

            if (input == null || input.isEmpty()) {
                screen.getDisplayExplorer().previous(screen);
            } else if (input.length() > 2) {
                Player character = createNewCharacter(screen, input);
                Player aliens[] = createNewAliens(screen, 3);
                Game game = new CLIGame(character, aliens);

                screen.setGame(game);
                screen.getDisplayExplorer().next(screen, DisplayType.SELECT_SPACE_SHIP);
            } else {
                screen.getIOStream().writeLine("Opps! my grandma can come up with a better name");
                throw new InputMismatchException();
            }
        });
    }

    private Player createNewCharacter(Screen screen, String name) {
        Player player = screen.getPlayerFactory().createPlayer(PlayerType.CHARACTER, name);
        return player;
    }


    private Player[] createNewAliens(Screen screen, int count) {
        Player players[] = new Player[count];
        Random random = new Random();
        IntStream.range(0, count).forEach((index) -> {
            int alienNameIndex = random.nextInt(ALIEN_NAMES.length);
            int shipIndex = random.nextInt(ALIEN_SPACESHIP_TYPES.length);
            Player player = screen.getPlayerFactory().createPlayer(PlayerType.ALIEN, ALIEN_NAMES[alienNameIndex]);
            player.setSpaceship(screen.getSpaceshipFactory().createSpaceship(ALIEN_SPACESHIP_TYPES[shipIndex]));
            players[index] = player;
        });
        return players;
    }

    private final static String[] ALIEN_NAMES = {"DARK VADER", "CRUSHER", "SKY BLAZER", "INVADER"};

    private static final SpaceshipType[] ALIEN_SPACESHIP_TYPES = Arrays.stream(SpaceshipType.values())
            .filter(spaceshipType -> spaceshipType.isAlien())
            .toArray(size -> new SpaceshipType[size]);


}
