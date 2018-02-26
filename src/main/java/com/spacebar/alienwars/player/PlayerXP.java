package com.spacebar.alienwars.player;

import java.io.Serializable;

public interface PlayerXP extends Serializable {

    long getXp();

    void addXp(long xp);

    int getLevel();

    int getHealth();

    int getAvailableHealth();

    void subtractHealth(int health);

    long getMaxXp();

    void levelUp();

    boolean canLevelUp();

    int getEnemyCount();

}
