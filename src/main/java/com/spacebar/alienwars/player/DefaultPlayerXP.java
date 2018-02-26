package com.spacebar.alienwars.player;


public class DefaultPlayerXP implements PlayerXP {

    private long xp;

    private long maxXp;

    private int level;

    private int health;

    private int availableHealth;

    private int enemyCount;


    @Override
    public long getXp() {
        return xp;
    }

    @Override
    public void addXp(long xp) {
        this.xp = Math.max(0, Math.addExact(this.xp, xp));
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getHealth() {
        return health;
    }

    public int getAvailableHealth() {
        return availableHealth;
    }

    @Override
    public void subtractHealth(int health) {
        this.availableHealth = Math.max(0, Math.subtractExact(this.availableHealth, health));
    }

    @Override
    public long getMaxXp() {
        return maxXp;
    }

    @Override
    public boolean canLevelUp() {
        return xp >= maxXp || level == 0;
    }

    @Override
    public int getEnemyCount() {
        return enemyCount;
    }

    @Override
    public void levelUp() {
        if (canLevelUp()) {
            initLevel(level + 1);
        }
    }

    private void initLevel(int level) {
        this.level = level;
        this.maxXp = 5 ^ level * 10;
        this.health = level ^ level - 2;
        this.health = this.health < 1 ? 1 : this.health;
        this.availableHealth = this.health;
        this.enemyCount = level ^ level + 1;
    }
}
