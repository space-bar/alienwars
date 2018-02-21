package com.spacebar.alienwars.game;

import com.spacebar.alienwars.exception.GameIllegalStateException;
import com.spacebar.alienwars.exception.GameInitializationException;

public interface Game {

    void start() throws GameIllegalStateException, GameInitializationException;

    void stop() throws GameIllegalStateException;

    void pause() throws GameIllegalStateException;

    void resume() throws GameIllegalStateException;

}
