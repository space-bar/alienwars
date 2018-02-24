package com.spacebar.alienwars.player;

public interface PlayerXP {

    public long getXp();

    public void addXp(long xp);

    public void subtractXp(long xp);

    public int getLevel();

    public void addLevel(int level);

    public void subtractLevel(int level);

    public int getHealth();

    public void addHealth(int health);

    public void subtractHealth(int health);
}
