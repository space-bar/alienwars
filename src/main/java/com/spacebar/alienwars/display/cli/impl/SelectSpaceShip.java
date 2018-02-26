package com.spacebar.alienwars.display.cli.impl;

import com.spacebar.alienwars.display.DisplayType;
import com.spacebar.alienwars.display.cli.AbstractCLIDisplay;
import com.spacebar.alienwars.screen.Screen;
import com.spacebar.alienwars.spaceship.Spaceship;
import com.spacebar.alienwars.spaceship.SpaceshipType;
import com.spacebar.alienwars.weapon.Weapon;
import com.spacebar.alienwars.weapon.WeaponType;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.stream.IntStream;

public class SelectSpaceShip extends AbstractCLIDisplay {

    public static final String HEADER = "" +
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

        drawHeader(screen, HEADER.split(NEW_LINE));

        drawBody(screen, buildBody(screen.getGame().getCharacterPlayer().getPlayerName(), getCharacterSpaceShips(screen)));

        drawFooter(screen, APP_LOGO.split(NEW_LINE));

        readInput(screen);
    }

    private String[] buildBody(String playerName, Spaceship[] ships) {
        String[] instructions = {"OK " + playerName,
                "lets get you a spaceship",
                "Select a spaceship for the list below",
                "Enter a number and hit enter.", WHITE_SPACE,
                "[EXIT to quit]", "[HOME to goto the Start Menu]", "[BACK to goto previous ]",
                WHITE_SPACE
        };
        String[] shipContents = new String[ships.length];
        IntStream.range(0, ships.length).forEach(index -> {
            Spaceship spaceship = ships[index];
            shipContents[index] = (index + 1) + "." + WHITE_SPACE + spaceship.getSpaceshipType().name() + " " + spaceship.getDisplay();
        });

        String[] body = new String[instructions.length + shipContents.length];
        System.arraycopy(instructions, 0, body, 0, instructions.length);
        System.arraycopy(shipContents, 0, body, instructions.length, shipContents.length);

        return body;
    }

    private Spaceship[] characterSpaceShips;

    private Spaceship[] getCharacterSpaceShips(Screen screen) {
        if (characterSpaceShips == null) {
            characterSpaceShips = Arrays.stream(SpaceshipType.values())
                    .filter(spaceshipType -> !spaceshipType.isAlien())
                    .map(spaceshipType -> screen.getSpaceshipFactory().createSpaceship(spaceshipType))
                    .toArray(size -> new Spaceship[size]);
        }
        return characterSpaceShips;
    }

    private void readInput(Screen screen) {
        screen.getIOStream().writeLine("Enter Number :");
        this.readInput(screen, (String input) -> {
            input = input != null ? input.trim() : null;

            if (input == null || input.isEmpty()) {
                screen.getDisplayExplorer().previous(screen);
            } else {
                Integer value = convertToInt(input);
                Spaceship[] spaceships = getCharacterSpaceShips(screen);
                if (value != null && value > 0 && spaceships != null && value <= spaceships.length) {
                    buildSpaceship(screen, spaceships[value - 1].getSpaceshipType());
                    screen.getDisplayExplorer().next(screen, DisplayType.PLAY_GAME);
                } else {
                    screen.getIOStream().write("Come on! stop kidding around...");
                    throw new InputMismatchException();
                }
            }
        });
    }

    private void buildSpaceship(Screen screen, SpaceshipType spaceshipType) {
        Spaceship spaceship = screen.getSpaceshipFactory().createSpaceship(spaceshipType);
        screen.getGame().getCharacterPlayer().setSpaceship(spaceship);

        WeaponType[] weaponTypes = WeaponType.values();
        Random random = new Random();
        int x = random.nextInt(weaponTypes.length);
        Weapon weapon = screen.getWeaponFactory().createWeapon(weaponTypes[x], 20);

        spaceship.setWeapon(weapon);
    }
}
