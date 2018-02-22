package com.spacebar.alienwars.game;

import com.spacebar.alienwars.exception.GameIllegalStateException;
import com.spacebar.alienwars.exception.GameInitializationException;
import com.spacebar.alienwars.player.Player;

public class AbstractGame implements Game {

    private final Player characterPlayer;
    private final Player[] alienPlayers;

    public AbstractGame(Player characterPlayer, Player[] alienPlayers) {
        this.characterPlayer = characterPlayer;
        this.alienPlayers = alienPlayers;
    }

    @Override
    public Player getCharacterPlayer() {
        return characterPlayer;
    }

    @Override
    public Player[] getAlienPlayers() {
        return alienPlayers;
    }

    @Override
    public void start() throws GameIllegalStateException, GameInitializationException {

    }

    @Override
    public void stop() throws GameIllegalStateException {

    }

    @Override
    public void pause() throws GameIllegalStateException {

    }

    @Override
    public void resume() throws GameIllegalStateException {

    }


}
