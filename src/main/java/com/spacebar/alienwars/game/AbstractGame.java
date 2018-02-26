package com.spacebar.alienwars.game;

import com.spacebar.alienwars.exception.GameIllegalStateException;
import com.spacebar.alienwars.exception.GameInitializationException;
import com.spacebar.alienwars.player.Player;

public abstract class AbstractGame implements Game {

    private Player characterPlayer;
    private Player[] alienPlayers;
    private boolean playing;
    private GameStatus status;

    private String name;
    private XPLogic xp;

    public AbstractGame(Player characterPlayer, Player[] alienPlayers) {
        this.characterPlayer = characterPlayer;
        this.alienPlayers = alienPlayers;
        this.status = GameStatus.NEW;
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
    public void start() throws GameInitializationException {
        playing = true;
        status = GameStatus.IN_PLAY;
    }
    @Override
    public void restart(Player... aliens) throws GameInitializationException {
        this.alienPlayers = aliens;
        playing = true;
        status = GameStatus.IN_PLAY;
    }

    @Override
    public boolean isPlaying() {
        return playing;
    }

    @Override
    public void stop(GameStatus status) throws GameIllegalStateException {
        playing = false;
        this.status = status;
    }

    @Override
    public void pause(GameStatus status) throws GameIllegalStateException {
        playing = false;
        this.status = status;
    }

    @Override
    public GameStatus getStatus() {
        return status;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public XPLogic getXPLogic() {
        if (xp == null)
            xp = new DefaultXPLogic();
        return xp;
    }
}
