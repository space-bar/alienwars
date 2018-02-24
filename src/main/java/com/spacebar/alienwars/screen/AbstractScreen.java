package com.spacebar.alienwars.screen;


import com.spacebar.alienwars.display.DefaultDisplayExplorer;
import com.spacebar.alienwars.display.DisplayExplorer;
import com.spacebar.alienwars.game.Game;
import com.spacebar.alienwars.player.DefaultPlayerFactory;
import com.spacebar.alienwars.player.PlayerFactory;
import com.spacebar.alienwars.weapon.DefaultWeaponFactory;
import com.spacebar.alienwars.weapon.WeaponFactory;

public abstract class AbstractScreen implements Screen {

    private final int width;

    private final int height;


    private Game game;


    private DisplayExplorer displayExplorer;

    private PlayerFactory playerFactory;

    private WeaponFactory weaponFactory;


    public AbstractScreen(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public DisplayExplorer getDisplayExplorer() {
        if (displayExplorer == null) {
            displayExplorer = new DefaultDisplayExplorer();
        }
        return displayExplorer;
    }

    @Override
    public PlayerFactory getPlayerFactory() {
        if (playerFactory == null) {
            playerFactory = new DefaultPlayerFactory();
        }
        return playerFactory;
    }

    @Override
    public WeaponFactory getWeaponFactory() {
        if (weaponFactory == null) {
            weaponFactory = new DefaultWeaponFactory();
        }
        return weaponFactory;
    }
    @Override
    public Game getGame() {
        return game;
    }

    @Override
    public void setGame(Game game) {
        this.game = game;
    }
}
