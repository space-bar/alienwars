package com.spacebar.alienwars.game;

import com.spacebar.alienwars.exception.GameIllegalStateException;
import com.spacebar.alienwars.exception.GameInitializationException;
import com.spacebar.alienwars.player.Player;

import java.io.Serializable;

public interface Game extends Serializable {

    String getName();

    Player getCharacterPlayer();

    Player[] getAlienPlayers();

    void start() throws GameInitializationException;

    void restart(Player... aliens) throws GameInitializationException;

    boolean isPlaying();

    void stop(GameStatus status) throws GameIllegalStateException;

    void pause(GameStatus status) throws GameIllegalStateException;

    GameStatus getStatus();

    XPLogic getXPLogic();

}
