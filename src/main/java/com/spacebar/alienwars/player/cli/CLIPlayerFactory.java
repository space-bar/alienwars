package com.spacebar.alienwars.player.cli;

import com.spacebar.alienwars.player.Player;
import com.spacebar.alienwars.player.PlayerFactory;
import com.spacebar.alienwars.player.PlayerType;

public class CLIPlayerFactory implements PlayerFactory {

    @Override
    public Player createPlayer(PlayerType playerType, String playerName) {
        if (playerType != null) {

            switch (playerType) {
                case ALIEN:
                    return new CLIPlayer(playerType, playerName);

                case CHARACTER:
                    return new CLIPlayer(playerType, playerName);
            }

        }
        return null;
    }
}
