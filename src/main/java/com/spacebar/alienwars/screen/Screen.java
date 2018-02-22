package com.spacebar.alienwars.screen;


import com.spacebar.alienwars.display.DisplayExplorer;
import com.spacebar.alienwars.display.DisplayFactory;
import com.spacebar.alienwars.game.Game;
import com.spacebar.alienwars.io.IOStream;
import com.spacebar.alienwars.player.PlayerFactory;
import com.spacebar.alienwars.spaceship.SpaceshipFactory;
import com.spacebar.alienwars.weapon.WeaponFactory;

public interface Screen {

    int getWidth();

    int getHeight();

    boolean isWindows();

    IOStream getIOStream();


    DisplayExplorer getDisplayExplorer();

    DisplayFactory getDisplayFactory();

    SpaceshipFactory getSpaceshipFactory();

    PlayerFactory getPlayerFactory();

    WeaponFactory getWeaponFactory();

    Game getGame();
}
