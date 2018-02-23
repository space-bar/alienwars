package com.spacebar.alienwars.weapon;

public abstract class AbstractWeapon implements Weapon {

    private final WeaponType weaponType;

    private final int rounds;

    private int avaliableRounds;

    public AbstractWeapon(WeaponType weaponType) {
        this(weaponType, 0);
    }

    public AbstractWeapon(WeaponType weaponType, int rounds) {
        if (weaponType == null) {
            throw new IllegalArgumentException();
        }
        this.weaponType = weaponType;
        this.rounds = rounds;
        this.avaliableRounds = rounds;
    }

    @Override
    public boolean fire() {
        if (canFire() && this.avaliableRounds > 0) {
            this.avaliableRounds--;
        }
        return false;
    }

    @Override
    public boolean canFire() {
        return getAvaliableRounds() > 0;
    }

    @Override
    public int getRounds() {
        return rounds;
    }

    @Override
    public int getAvaliableRounds() {
        return this.avaliableRounds;
    }
}
