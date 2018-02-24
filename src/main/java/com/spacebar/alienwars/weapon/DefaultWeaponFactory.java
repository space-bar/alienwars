package com.spacebar.alienwars.weapon;

public class DefaultWeaponFactory implements WeaponFactory {

    @Override
    public Weapon createWeapon(WeaponType weaponType, int rounds) {
        if (weaponType != null) {
            return new DefaultWeapon(weaponType, rounds);
        }
        return null;
    }
}
