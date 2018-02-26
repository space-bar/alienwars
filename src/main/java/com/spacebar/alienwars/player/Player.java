package com.spacebar.alienwars.player;

import com.spacebar.alienwars.spaceship.Spaceship;

import java.io.Serializable;

public interface Player extends Serializable {

    String getPlayerName();

    PlayerType getPlayerType();

    Spaceship getSpaceship();

    void setSpaceship(Spaceship spaceship);

    PlayerXP getPlayerXP();
}
