package com.spacebar.alienwars.player;

public interface PlayerFactory {

    Player createPlayer(PlayerType playerType, String playerName);
}
