package com.spacebar.alienwars.player;

import com.spacebar.alienwars.spaceship.Spaceship;

public interface Player {

    String getPlayerName();

    PlayerType getPlayerType();

    Spaceship getSpaceship();

    void setSpaceship(Spaceship spaceship);
}
