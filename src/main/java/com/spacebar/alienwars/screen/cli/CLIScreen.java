package com.spacebar.alienwars.screen.cli;

import com.spacebar.alienwars.display.cli.CLIDisplayFactory;
import com.spacebar.alienwars.game.Game;
import com.spacebar.alienwars.game.cli.CLIGame;
import com.spacebar.alienwars.io.cli.CLIIOStream;
import com.spacebar.alienwars.player.PlayerFactory;
import com.spacebar.alienwars.player.cli.CLIPlayerFactory;
import com.spacebar.alienwars.screen.AbstractScreen;
import com.spacebar.alienwars.display.DisplayFactory;
import com.spacebar.alienwars.io.IOStream;
import com.spacebar.alienwars.spaceship.SpaceshipFactory;
import com.spacebar.alienwars.spaceship.cli.CLISpaceshipFactory;
import com.spacebar.alienwars.weapon.WeaponFactory;
import com.spacebar.alienwars.weapon.cli.CLIWeaponFactory;

public class CLIScreen extends AbstractScreen {

    private IOStream ioStream;

    private SpaceshipFactory spaceshipFactory;

    private DisplayFactory displayFactory;

    private PlayerFactory playerFactory;

    private WeaponFactory weaponFactory;

    private Game game;

    public CLIScreen(int width, int height) {
        super(width, height);
    }

    public IOStream getIOStream() {
        if (ioStream == null) {
            ioStream = new CLIIOStream();
        }
        return ioStream;
    }

    @Override
    public DisplayFactory getDisplayFactory() {
        if (displayFactory == null) {
            displayFactory = new CLIDisplayFactory();
        }

        return displayFactory;
    }

    public SpaceshipFactory getSpaceshipFactory() {
        if (spaceshipFactory == null) {
            spaceshipFactory = new CLISpaceshipFactory();
        }
        return spaceshipFactory;
    }

    @Override
    public PlayerFactory getPlayerFactory() {
        if (playerFactory == null) {
            playerFactory = new CLIPlayerFactory();
        }
        return playerFactory;
    }

    @Override
    public WeaponFactory getWeaponFactory() {
        if (weaponFactory == null) {
            weaponFactory = new CLIWeaponFactory();
        }
        return weaponFactory;
    }

    @Override
    public Game getGame() {
        if (game == null) {
            game = new CLIGame(null, null);
        }
        return game;
    }
}
