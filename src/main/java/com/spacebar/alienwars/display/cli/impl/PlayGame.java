package com.spacebar.alienwars.display.cli.impl;

import com.spacebar.alienwars.game.Game;
import com.spacebar.alienwars.player.Player;
import com.spacebar.alienwars.screen.Screen;
import com.spacebar.alienwars.display.cli.AbstractCLIDisplay;

public class PlayGame extends AbstractCLIDisplay {

    @Override
    public void display(Screen screen) {
        Game game = screen.getGame();
        if (game == null)
            return;
        game.getCharacterPlayer();



     /*   IOStream r = screen.getIOStream();
        r.writeLine(CLIAbstractDisplay.APP_LOGO);
        r.writeLine("===========================================================\n");

        r.writeLine("|       What do you want to do ?                           |");
        r.writeLine("|       Type a number and hit enter.                       |");
        r.writeLine("|                                                          |");
        r.writeLine("|       1. Start Game                                      |");
        r.writeLine("|       2. Help                                            |");
        r.writeLine("|       3. Exit                                            |");

        r.writeLine("\n===========================================================\n");

        r.write("Enter Menu Number :");*/

        //readInput(screen);
        //System.out.print("\033[31;1mHello\033[0m, \033[32;1;2mworld!\033[0m");
        //System.out.println("\033[31mRed\033[32m, Green\033[33m, Yellow\033[34m, Blue\033[0m");
    }

    private void renderGame(Screen screen) {
        drawHeader(screen, APP_BANNER);
        Game game = screen.getGame();
        Player characterPlayer = game.getCharacterPlayer();
        //characterPlayer.getSpaceship().

    }
}
