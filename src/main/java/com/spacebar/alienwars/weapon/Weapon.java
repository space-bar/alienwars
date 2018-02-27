package com.spacebar.alienwars.weapon;

import java.io.Serializable;

public interface Weapon extends Serializable {

    boolean fire();

    boolean canFire();

    int getRounds();

    int getAvaliableRounds();

    void reload();

    WeaponType getWeaponType();

}
