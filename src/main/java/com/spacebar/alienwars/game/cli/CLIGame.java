package com.spacebar.alienwars.game.cli;

import com.spacebar.alienwars.game.AbstractGame;
import com.spacebar.alienwars.player.AlienPlayer;
import com.spacebar.alienwars.player.CharacterPlayer;

public class CLIGame extends AbstractGame {

    public CLIGame(CharacterPlayer characterPlayer, AlienPlayer alienPlayer) {
        super(characterPlayer, alienPlayer);
    }
}
