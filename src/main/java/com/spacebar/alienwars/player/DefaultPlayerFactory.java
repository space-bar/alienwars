package com.spacebar.alienwars.player;


public class DefaultPlayerFactory implements PlayerFactory {

    @Override
    public Player createPlayer(PlayerType playerType, String playerName) {
        if (playerType != null) {
            return new DefaultPlayer(playerType, playerName);
        }
        return null;
    }
}
