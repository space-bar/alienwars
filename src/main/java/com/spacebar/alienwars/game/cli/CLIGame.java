package com.spacebar.alienwars.game.cli;

import com.spacebar.alienwars.game.AbstractGame;
import com.spacebar.alienwars.player.Player;


public class CLIGame extends AbstractGame {

    public CLIGame(Player characterPlayer, Player[] alienPlayers) {
        super(characterPlayer, alienPlayers);
    }
}
