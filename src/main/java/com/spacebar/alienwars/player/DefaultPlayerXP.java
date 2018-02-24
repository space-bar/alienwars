package com.spacebar.alienwars.player;


public class DefaultPlayerXP implements PlayerXP {

    private long xp;

    private int level;

    private int health;


    @Override
    public long getXp() {
        return xp;
    }

    @Override
    public void addXp(long xp) {
        this.xp = Math.max(0, Math.addExact(this.xp, xp));
    }

    @Override
    public void subtractXp(long xp) {
        this.xp = Math.max(0, Math.subtractExact(this.xp, xp));
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void addLevel(int level) {
        this.level = Math.max(0, Math.addExact(this.level, level));
    }

    @Override
    public void subtractLevel(int level) {
        this.level = Math.max(0, Math.subtractExact(this.level, level));
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void addHealth(int health) {
        this.health= Math.max(0, Math.addExact(this.health, health));
    }

    @Override
    public void subtractHealth(int health) {
        this.health = Math.max(0, Math.subtractExact(this.health, health));
    }
}
