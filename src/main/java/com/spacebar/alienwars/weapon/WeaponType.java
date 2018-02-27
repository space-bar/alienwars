package com.spacebar.alienwars.weapon;

public enum WeaponType {

    PLASMA(8),
    LASER(10),
    BALISTIC_MISSILE(11);

    private int reloadRounds;

    WeaponType(int reloadRounds) {
        this.reloadRounds = reloadRounds;
    }

    public int getReloadRounds() {
        return reloadRounds;
    }
}
