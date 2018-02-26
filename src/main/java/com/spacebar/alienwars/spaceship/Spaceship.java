package com.spacebar.alienwars.spaceship;

import com.spacebar.alienwars.weapon.Weapon;


import java.awt.*;
import java.io.Serializable;

public interface Spaceship extends Serializable {

    Weapon getWeapon();

    void setWeapon(Weapon weapon);

    Point getCoordinate();

    SpaceshipType getSpaceshipType();

    String getDisplay();

    void destroy();

    boolean isDestroyed();
}
