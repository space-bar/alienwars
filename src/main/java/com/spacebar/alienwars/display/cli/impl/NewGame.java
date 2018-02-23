package com.spacebar.alienwars.display.cli.impl;

import com.spacebar.alienwars.display.DisplayType;
import com.spacebar.alienwars.display.cli.AbstractCLIDisplay;
import com.spacebar.alienwars.game.Game;
import com.spacebar.alienwars.game.cli.CLIGame;
import com.spacebar.alienwars.io.IOStream;
import com.spacebar.alienwars.player.Player;
import com.spacebar.alienwars.player.PlayerType;
import com.spacebar.alienwars.player.cli.CLIPlayer;
import com.spacebar.alienwars.screen.Screen;
import com.spacebar.alienwars.spaceship.Spaceship;
import com.spacebar.alienwars.spaceship.SpaceshipType;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
                Player character = createNewCharacter(screen, name);
                Player aliens[] = createNewAliens(screen, 3);
                Game game = new CLIGame(character, aliens);

                screen.setGame(game);
                screen.getDisplayExplorer().next(screen, DisplayType.SELECT_SPACE_SHIP);
            } else {
                screen.getIOStream().writeLine("Opps Enter Name :");
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
