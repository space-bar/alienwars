package com.spacebar.alienwars.spaceship;


import com.spacebar.alienwars.weapon.Weapon;

import java.awt.*;

public abstract class AbstractSpaceship implements Spaceship {

    private Weapon weapon;

    private Point coordinate;

    private String display;

    private boolean destroy;


    private final SpaceshipType spaceshipType;

    public AbstractSpaceship(SpaceshipType spaceshipType) {
        if (spaceshipType == null) {
            throw new IllegalArgumentException();
        }
        this.spaceshipType = spaceshipType;
    }


    @Override
    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    @Override
    public Point getCoordinate() {
        if (coordinate == null) {
            coordinate = new Point();
        }
        return coordinate;
    }

    public void setCoordinate(Point coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public SpaceshipType getSpaceshipType() {
        return spaceshipType;
    }

    @Override
    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public boolean isDestroyed() {
        return destroy;
    }

    public void destroy() {
        this.destroy = true;
    }
}
