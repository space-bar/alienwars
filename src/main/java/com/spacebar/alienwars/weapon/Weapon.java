package com.spacebar.alienwars.weapon;

public interface Weapon {

    boolean fire();

    boolean canFire();

    int getRounds();

    int getAvaliableRounds();

}
