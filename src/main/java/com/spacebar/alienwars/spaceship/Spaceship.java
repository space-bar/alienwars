package com.spacebar.alienwars.spaceship;

import com.spacebar.alienwars.weapon.Weapon;
import com.sun.scenario.effect.impl.state.RenderState;
import com.sun.xml.internal.bind.v2.runtime.Coordinator;

import java.awt.*;

public interface Spaceship {

    Weapon getWeapon();

    Point getCoordinate();

    SpaceshipType getSpaceshipType();

    String getSpaceshipName();

    String getDisplay();
}
