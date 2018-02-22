package com.spacebar.alienwars.weapon.cli;

import com.spacebar.alienwars.weapon.Weapon;
import com.spacebar.alienwars.weapon.WeaponFactory;
import com.spacebar.alienwars.weapon.WeaponType;

public class CLIWeaponFactory implements WeaponFactory {

    @Override
    public Weapon createWeapon(WeaponType weaponType, int rounds) {
        if (weaponType != null) {
            switch (weaponType) {
                case LASER:
                    return new CLIWeapon(weaponType, rounds);
                case PLASMA:
                    return new CLIWeapon(weaponType, rounds);
            }
        }
        return null;
    }
}
