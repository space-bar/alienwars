package com.spacebar.alienwars.display.cli.impl;

import com.spacebar.alienwars.display.DisplayType;
import com.spacebar.alienwars.exception.GameInitializationException;
import com.spacebar.alienwars.game.Game;
import com.spacebar.alienwars.player.Player;
import com.spacebar.alienwars.player.PlayerType;
import com.spacebar.alienwars.player.cli.CLIPlayer;
import com.spacebar.alienwars.screen.Screen;
import com.spacebar.alienwars.display.cli.AbstractCLIDisplay;
import com.spacebar.alienwars.spaceship.SpaceshipType;

import java.util.Arrays;
import java.util.stream.IntStream;

public class PlayGame extends AbstractCLIDisplay {

    public PlayGame() {
        super(DisplayType.PLAY_GAME);
    }

    @Override
    public void display(Screen screen) {
        try {
            renderGame(screen);
        } catch (GameInitializationException e) {
            e.printStackTrace();
        }
       /* Game game = screen.getGame();
        if (game == null)
            return;
        game.getCharacterPlayer();*/

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

    private final static String[] ALIEN_NAMES = {"DARK VADER", "CRUSHER", "SKY BLAZER", "INVADER"};

    private void renderGame(Screen screen) throws GameInitializationException {

        Game game = screen.getGame();
        final Player characterPlayer = game.getCharacterPlayer();
        //characterPlayer.getSpaceship().

        CLIPlayer player = (CLIPlayer) screen.getPlayerFactory().createPlayer(PlayerType.ALIEN, null);
        player.setSpaceship(screen.getSpaceshipFactory().createSpaceship(SpaceshipType.DESTROYER));
        CLIPlayer player2 = (CLIPlayer) screen.getPlayerFactory().createPlayer(PlayerType.ALIEN, null);
        player2.setSpaceship(screen.getSpaceshipFactory().createSpaceship(SpaceshipType.DESTROYER));

        final Player[] alienPlayers = new Player[]{player, player2};//game.getAlienPlayers();
        /*if (characterPlayer == null || alienPlayers == null || alienPlayers.length == 0) {
            throw new GameInitializationException();
        }*/
        drawHeader(screen, APP_BANNER.split("\n"));
        StringBuilder sb = new StringBuilder();
        for (Player alienPlayer : alienPlayers) {
            String display = alienPlayer.getSpaceship().getDescription();
            sb.append(display);
        }
        screen.getIOStream().writeLine("");
        screen.getIOStream().writeLine(sb.toString());
    }

    /* protected void drawAlien(Screen screen, String... headers) {
         int width = screen.getWidth();
         int maxHeaderWidth = computeMaxWidth(headers);
         width = Math.max(width, maxHeaderWidth);

         String padding = contents((width - maxHeaderWidth) / 2, ' ');
         Arrays.stream(headers).forEach((header) -> {
             screen.getIOStream().writeLine(padding + header);
         });

         String lines = contents(width, '=', '+', '+');
         screen.getIOStream().write(lines);

     }
 */
    private String contents(int length, String pixel) {
        StringBuilder sb = new StringBuilder();
        IntStream.range(0, length).forEach(i -> {
            sb.append(pixel);
        });
        return sb.toString();
    }
}
