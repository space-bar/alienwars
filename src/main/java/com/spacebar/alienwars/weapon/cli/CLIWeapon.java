package com.spacebar.alienwars.weapon.cli;

import com.spacebar.alienwars.weapon.AbstractWeapon;
import com.spacebar.alienwars.weapon.WeaponType;

public class CLIWeapon extends AbstractWeapon {


    public CLIWeapon(WeaponType weaponType) {
        super(weaponType);
    }

    public CLIWeapon(WeaponType weaponType, int rounds) {
        super(weaponType, rounds);
    }


}
