package com.spacebar.alienwars.game;

import com.spacebar.alienwars.player.PlayerXP;
import com.spacebar.alienwars.spaceship.Spaceship;

public class DefaultXPLogic implements XPLogic {
    private long moveTimer;
    private int moveCount;
    private boolean headShot;
    private boolean hit;
    //
    private final long startTimer = System.currentTimeMillis();
    private int moveCounter;
    private int headShotCounter;

    public long getMoveTimer() {
        return moveTimer;
    }

    public int getMoveCount() {
        return moveCount;
    }

    public boolean isHeadShot() {
        return headShot;
    }

    public long getStartTimer() {
        return startTimer;
    }

    public int getMoveCounter() {
        return moveCounter;
    }

    public int getHeadShotCounter() {
        return headShotCounter;
    }

    public void addMove() {
        this.moveCount++;
        this.moveCounter++;
    }

    public void evaluateHit(Spaceship enemySpaceship, int shotX) {
        int spaceshipSize = enemySpaceship.getDisplay().length();
        int x = enemySpaceship.getCoordinate().x;
        int head = spaceshipSize / 2;
        if (x + head == shotX || (spaceshipSize % 2 == 0 ? x + head + 1 == shotX : false)) {
            headShotCounter++;
            headShot = true;
        } else {
            headShot = false;
        }
        hit = true;
    }

    public void computeXP(PlayerXP playerXP) {
        if (!hit)
            return;
        long xp = playerXP.getMaxXp() / playerXP.getEnemyCount();
        long mod = playerXP.getMaxXp() % playerXP.getEnemyCount();
        xp = xp + (mod > 0 ? 1 : 0);
        if (moveCount <= 2) {
            playerXP.addXp(Math.max((xp / 4), 1));
        }
        if (headShot) {
            playerXP.addXp(Math.max((xp / 4) + 1, 1));
        }
//move within 15sec
        if (moveTimer > 0 && System.currentTimeMillis() - moveTimer <= (1000 * 15)) {
            playerXP.addXp(Math.max((xp / 4) - 1, 1));
        }
        //bonus for hit
        playerXP.addXp(Math.max((xp / 2) + 1, 1));
    }

    public void reset() {
        moveCount = 0;
        headShot = false;
        hit = false;
        moveTimer = System.currentTimeMillis();
    }
}
