package com.spacebar.alienwars.game;

import com.spacebar.alienwars.player.PlayerXP;
import com.spacebar.alienwars.spaceship.Spaceship;

import java.io.Serializable;

public interface XPLogic extends Serializable {
    long getStartTimer();

    int getMoveCounter();

    int getHeadShotCounter();

    void addMove();

    void evaluateHit(Spaceship enemySpaceship, int shotX);

    void computeXP(PlayerXP playerXP);

    void reset();
}
