package com.spacebar.alienwars.player;

import com.spacebar.alienwars.spaceship.Spaceship;

public abstract class AbstractPlayer implements Player {

    private final PlayerType playerType;

    private final String playerName;

    private Spaceship spaceship;

    public AbstractPlayer(PlayerType playerType) {
        this(playerType, null);
    }

    public AbstractPlayer(PlayerType playerType, String playerName) {
        if (playerType == null) {
            throw new IllegalArgumentException();
        }
        this.playerType = playerType;
        this.playerName = playerName == null ? playerType.name() : playerName;
    }

    @Override
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public Spaceship getSpaceship() {
        return spaceship;
    }

    public void setSpaceship(Spaceship spaceship) {
        this.spaceship = spaceship;
    }

    public PlayerType getPlayerType() {
        return playerType;
    }
}
