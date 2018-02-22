package com.spacebar.alienwars.spaceship;


import com.spacebar.alienwars.weapon.Weapon;

import java.awt.*;

public abstract class AbstractSpaceship implements Spaceship {

    private Weapon weapon;

    private Point coordinate;

    private String description;

    private final String shipName;

    private final boolean alienShip;


    public AbstractSpaceship(String shipName, boolean alienShip) {
        this.shipName = shipName;
        this.alienShip = alienShip;
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
        return coordinate;
    }

    public void setCoordinate(Point coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public boolean isAlienShip() {
        return alienShip;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getShipName() {
        return shipName;
    }
}
