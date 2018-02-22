package com.spacebar.alienwars.weapon;

public interface WeaponFactory {

    Weapon createWeapon(WeaponType weaponType, int rounds);
}
