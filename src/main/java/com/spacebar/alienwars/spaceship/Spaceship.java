package com.spacebar.alienwars.spaceship;

import com.spacebar.alienwars.weapon.Weapon;
import com.sun.scenario.effect.impl.state.RenderState;
import com.sun.xml.internal.bind.v2.runtime.Coordinator;

import java.awt.*;

public interface Spaceship {

    Weapon getWeapon();

    void setWeapon(Weapon weapon);

    Point getCoordinate();

    SpaceshipType getSpaceshipType();

    String getDisplay();

    void destroy();

    boolean isDestroyed();
}
