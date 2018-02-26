package com.spacebar.alienwars.display.cli.impl;

import com.spacebar.alienwars.display.DisplayType;
import com.spacebar.alienwars.display.cli.AbstractCLIDisplay;
import com.spacebar.alienwars.game.XPLogic;
import com.spacebar.alienwars.game.GameStatus;
import com.spacebar.alienwars.player.Player;
import com.spacebar.alienwars.screen.Screen;

public class EndGame extends AbstractCLIDisplay {

    public static final String YOU_WON = "" +
            "_____.___.                 __      __                \n" +
            "\\__  |   |  ____   __ __  /  \\    /  \\ ____    ____  \n" +
            " /   |   | /  _ \\ |  |  \\ \\   \\/\\/   //  _ \\  /    \\ \n" +
            " \\____   |(  <_> )|  |  /  \\        /(  <_> )|   |  \\\n" +
            " / ______| \\____/ |____/    \\__/\\  /  \\____/ |___|  /\n" +
            " \\/                              \\/               \\/ ";

    public static final String YOU_LOST = "" +
            "_____.___.                .____                     __   \n" +
            "\\__  |   |  ____   __ __  |    |     ____   _______/  |_ \n" +
            " /   |   | /  _ \\ |  |  \\ |    |    /  _ \\ /  ___/\\   __\\\n" +
            " \\____   |(  <_> )|  |  / |    |___(  <_> )\\___ \\  |  |  \n" +
            " / ______| \\____/ |____/  |_______ \\\\____//____  > |__|  \n" +
            " \\/                               \\/           \\/       ";

    public EndGame() {
        super(DisplayType.PLAY_GAME);
    }

    @Override
    public void display(Screen screen) {
    //
    }


    public void display(Screen screen, GameStatus gameStatus) {
        String header = GameStatus.WON == gameStatus ? YOU_WON : YOU_LOST;
        Player characterPlayer = screen.getGame().getCharacterPlayer();
        XPLogic xpLogic = screen.getGame().getXPLogic();

        drawHeader(screen, header.split(NEW_LINE));
        drawBody(screen, false, RABBIT.split(NEW_LINE));

        drawBody(screen, false, WHITE_SPACE,
                "[BACK to continue game ]",
                "[SAVE to save game]",
                "[HOME to goto the Start Menu]",
                "[EXIT to quit]", WHITE_SPACE);
        drawTagGroup(screen, 2,

                " Player : " + characterPlayer.getPlayerName(),
                " Level : " + characterPlayer.getPlayerXP().getLevel(),
                " Health : " + characterPlayer.getPlayerXP().getAvailableHealth() + " / " + characterPlayer.getPlayerXP().getHealth(),
                " Points : " + characterPlayer.getPlayerXP().getXp() + " / " + characterPlayer.getPlayerXP().getMaxXp(),
                " Weapon Rounds : " + characterPlayer.getSpaceship().getWeapon().getAvaliableRounds(),
                " HeadShots : " + xpLogic.getHeadShotCounter(),
                " Moves : " + xpLogic.getMoveCounter(),
                " Duration : " + (System.currentTimeMillis() - xpLogic.getStartTimer()) / 1000 + " secs");

        drawFooter(screen, APP_LOGO.split(NEW_LINE));
        readInput(screen);

    }


    private void readInput(Screen screen) {
        screen.getIOStream().write(NEW_LINE + "Enter:");
        this.readInput(screen, (String input) -> {

            if (CMD_BACK.equalsIgnoreCase(input)) {
                screen.getDisplayExplorer().display(screen, DisplayType.PLAY_GAME);
            } else if ("save".equalsIgnoreCase(input)) {
                screen.getDisplayExplorer().display(screen, DisplayType.SAVE_GAME);
            } else {
                performDefaultCommand(screen, input, v -> {
                });
            }

        }, true);
    }
}
