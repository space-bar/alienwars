package com.spacebar.alienwars.player;

import com.spacebar.alienwars.spaceship.Spaceship;

public interface Player {

    String getPlayerName();

    boolean isAlien();

    Spaceship getSpaceship();
}
