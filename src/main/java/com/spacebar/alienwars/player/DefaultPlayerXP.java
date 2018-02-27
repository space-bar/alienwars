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
        int d2= (int) Math.pow(level, level);
        int dl =  Math.max(d2, 2);
        int dx = this.xp > 3 ? (int) (this.xp % 3) : 2;
        dx = dx < 2 ? 2 : dx;

        this.level = level;
        this.maxXp = dl * 10L * dx;
        this.maxXp = (xp + 10) > this.maxXp ? xp + this.maxXp : this.maxXp;
        this.maxXp = (long) (this.maxXp + Math.sqrt(this.maxXp));
        this.health = level + (level>1?level % 2:0);
        this.availableHealth = this.health;
        this.enemyCount = dl + 1;
        this.enemyCount = (this.enemyCount > d2 ? (this.enemyCount - d2) / 2 : 0) + this.enemyCount;
    }
}
