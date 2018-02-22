package com.spacebar.alienwars.spaceship.cli;

import com.spacebar.alienwars.spaceship.AbstractSpaceship;


public class CylonSpaceship extends AbstractSpaceship {

    public CylonSpaceship() {
        super("CYLON", true);
    }

    private String image = "/-^-\\ \033[32m";
}
