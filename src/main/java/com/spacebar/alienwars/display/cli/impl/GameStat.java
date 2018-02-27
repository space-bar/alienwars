package com.spacebar.alienwars.display.cli.impl;

import com.spacebar.alienwars.display.DisplayType;
import com.spacebar.alienwars.display.cli.AbstractCLIDisplay;
import com.spacebar.alienwars.game.XPLogic;
import com.spacebar.alienwars.player.Player;
import com.spacebar.alienwars.screen.Screen;

import java.util.InputMismatchException;

import static com.spacebar.alienwars.util.PlayGameUtils.CMD_SAVE;

public class GameStat extends AbstractCLIDisplay {

    public static final String HEADER = "" +
            "  ____________________________________\n" +
            " /   _____/\\__    ___/  _  \\__    ___/\n" +
            " \\_____  \\   |    | /  /_\\  \\|    |   \n" +
            " /        \\  |    |/    |    \\    |   \n" +
            "/_______  /  |____|\\____|__  /____|   \n" +
            "        \\/                 \\/       ";

    public GameStat() {
        super(DisplayType.GAME_STAT);
    }

    @Override
    public void display(Screen screen) {
        Player characterPlayer = screen.getGame().getCharacterPlayer();
        XPLogic xpLogic = screen.getGame().getXPLogic();

        drawHeader(screen, HEADER.split(NEW_LINE));
        drawBody(screen, false, RABBIT.split(NEW_LINE));

        drawBody(screen, false, WHITE_SPACE,
                "[BACK to continue game ]",
                "[SAVE to save game]",
                "[HOME to goto the Start Menu]",
                "[EXIT to quit]", WHITE_SPACE);
        drawTagGroup(screen, 2,

                " Player : " + characterPlayer.getPlayerName(),
                " Status : " + (screen.getGame().getStatus() != null ? screen.getGame().getStatus().name() : ""),
                " Level  : " + characterPlayer.getPlayerXP().getLevel(),
                " Health : " + characterPlayer.getPlayerXP().getAvailableHealth() + " / " + characterPlayer.getPlayerXP().getHealth(),
                " Points : " + characterPlayer.getPlayerXP().getXp() + " / " + characterPlayer.getPlayerXP().getMaxXp(),
                " Weapon : " + characterPlayer.getSpaceship().getWeapon().getWeaponType().name(),
                " HeadShots : " + xpLogic.getHeadShotCounter(),
                " Weapon Rounds : " + characterPlayer.getSpaceship().getWeapon().getAvaliableRounds(),
                " Moves : " + xpLogic.getMoveCounter(),
                " Duration  : " + (System.currentTimeMillis() - xpLogic.getStartTimer()) / 1000 + " secs");

        drawFooter(screen, APP_LOGO.split(NEW_LINE));
        readInput(screen);

    }


    private void readInput(Screen screen) {
        screen.getIOStream().write(NEW_LINE + "Enter:");
        this.readInput(screen, (String input) -> {
            if (CMD_SAVE.equalsIgnoreCase(input)) {
                screen.getDisplayExplorer().next(screen, DisplayType.SAVE_GAME);
            } else {
                throw new InputMismatchException();
            }
        });
    }
}
