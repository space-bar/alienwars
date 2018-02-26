package com.spacebar.alienwars.util;

import com.spacebar.alienwars.game.Game;
import com.spacebar.alienwars.game.cli.CLIGame;
import com.spacebar.alienwars.player.Player;
import com.spacebar.alienwars.player.PlayerType;
import com.spacebar.alienwars.screen.Screen;
import com.spacebar.alienwars.spaceship.Spaceship;
import com.spacebar.alienwars.spaceship.SpaceshipType;
import com.spacebar.alienwars.weapon.Weapon;
import com.spacebar.alienwars.weapon.WeaponType;

import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.Random;
import java.util.stream.IntStream;

public final class GameUtils {
    private static final String[] ALIEN_NAMES = {"DARK VADER", "CRUSHER", "SKY BLAZER", "INVADER"};

    private static final SpaceshipType[] ALIEN_SPACESHIP_TYPES = Arrays.stream(SpaceshipType.values())
            .filter(spaceshipType -> spaceshipType.isAlien())
            .toArray(size -> new SpaceshipType[size]);

    private GameUtils() {
    }

    private static Properties MANIFEST;

    public static Properties getManifest() {
        if (MANIFEST == null) {
            try {
                MANIFEST = FileUtils.readManifest();
            } catch (IOException ex) {
                // if reading manifest fails lets still play the game
                MANIFEST = new Properties();
            }
        }
        return MANIFEST;
    }

    public static Spaceship createSpaceship(Screen screen, SpaceshipType spaceshipType) {
        Spaceship spaceship = screen.getSpaceshipFactory().createSpaceship(spaceshipType);

        WeaponType[] weaponTypes = WeaponType.values();
        int x = new Random().nextInt(weaponTypes.length);
        Weapon weapon = screen.getWeaponFactory().createWeapon(weaponTypes[x], 20);
        spaceship.setWeapon(weapon);
        return spaceship;
    }

    public static Game createGame(Screen screen, String characterName) {
        Player character = createCharacter(screen, characterName);
        if (character.getPlayerXP().canLevelUp()) {
            character.getPlayerXP().levelUp();
        }
        Player[] aliens = createAliens(screen, character.getPlayerXP().getEnemyCount());
        return new CLIGame(character, aliens);
    }

    public static Player createCharacter(Screen screen, String name) {
        return screen.getPlayerFactory().createPlayer(PlayerType.CHARACTER, name);
    }


    public static Player[] createAliens(Screen screen, int count) {
        Player[] players = new Player[count];
        Random random = new Random();
        IntStream.range(0, count).forEach(index -> {
            int alienNameIndex = random.nextInt(ALIEN_NAMES.length);
            int shipIndex = random.nextInt(ALIEN_SPACESHIP_TYPES.length);
            Player player = screen.getPlayerFactory().createPlayer(PlayerType.ALIEN, ALIEN_NAMES[alienNameIndex]);
            player.setSpaceship(screen.getSpaceshipFactory().createSpaceship(ALIEN_SPACESHIP_TYPES[shipIndex]));
            players[index] = player;
        });
        return players;
    }


}
