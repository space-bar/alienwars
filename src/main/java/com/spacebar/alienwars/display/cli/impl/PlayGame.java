package com.spacebar.alienwars.display.cli.impl;

import com.spacebar.alienwars.display.DisplayType;
import com.spacebar.alienwars.game.Game;
import com.spacebar.alienwars.game.cli.CLIGame;
import com.spacebar.alienwars.player.Player;
import com.spacebar.alienwars.player.PlayerType;
import com.spacebar.alienwars.player.cli.CLIPlayer;
import com.spacebar.alienwars.screen.Screen;
import com.spacebar.alienwars.display.cli.AbstractCLIDisplay;
import com.spacebar.alienwars.spaceship.SpaceshipType;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

public class PlayGame extends AbstractCLIDisplay {

    private Map<Integer, Player[]> coordinateMap;

    public PlayGame() {
        super(DisplayType.PLAY_GAME);
    }

    @Override
    public void display(Screen screen) {
        try {
            /*Game game = screen.getGame();
            //final Player characterPlayer = game.getCharacterPlayer();
            //characterPlayer.getSpaceship().

            Player characterPlayer = screen.getPlayerFactory().createPlayer(PlayerType.CHARACTER, "Player");
            characterPlayer.setSpaceship(screen.getSpaceshipFactory().createSpaceship(SpaceshipType.ORION));

            CLIPlayer player = (CLIPlayer) screen.getPlayerFactory().createPlayer(PlayerType.ALIEN, null);
            player.setSpaceship(screen.getSpaceshipFactory().createSpaceship(SpaceshipType.DESTROYER));
            CLIPlayer player2 = (CLIPlayer) screen.getPlayerFactory().createPlayer(PlayerType.ALIEN, null);
            player2.setSpaceship(screen.getSpaceshipFactory().createSpaceship(SpaceshipType.DESTROYER));
            ((CLIGame) game).setAlienPlayers(new Player[]{player, player2});
            ((CLIGame) game).setCharacterPlayer(characterPlayer);
            screen.setGame(game);*/

            coordinateMap = initCoordinateMap(screen);
            renderGame(screen, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void renderGame(Screen screen, int steps) {
        StringBuilder battleField = drawBattleField(screen, steps);

        drawHeader(screen, APP_BANNER.split(NEW_LINE));
        screen.getIOStream().write(battleField.toString());
        drawFooter(screen, APP_LOGO.split(NEW_LINE));

        readInput(screen);
    }


    private Map<Integer, Player[]> initCoordinateMap(Screen screen) {
        Game game = screen.getGame();
        Player[] alienPlayers = game.getAlienPlayers();
        Player characterPlayer = game.getCharacterPlayer();

        int width = screen.getWidth();
        int height = screen.getHeight();


        //compute a random positon for alien spaceship
        int maxX = width / alienPlayers.length;
        int x = 0;
        Random random = new Random();
        for (int index = 0; index < alienPlayers.length; index++) {
            Point coordinate = alienPlayers[index].getSpaceship().getCoordinate();
            String display = alienPlayers[index].getSpaceship().getDisplay();
            int m = maxX - display.length();
            m = Math.max(m, 1);
            coordinate.x = random.nextInt(m * (index + 1) - x) + x;
            coordinate.y = 0;
            x = coordinate.x + x;
        }

        Point coordinate = characterPlayer.getSpaceship().getCoordinate();
        coordinate.x = (width - characterPlayer.getSpaceship().getDisplay().length()) / 2;
        coordinate.y = height - 1;

        Map<Integer, Player[]> positionMap = new HashMap<>();
        positionMap.put(0, alienPlayers);
        positionMap.put(height - 1, new Player[]{characterPlayer});

        return positionMap;
    }


   /* private StringBuilder drawBattleField(Screen screen, int step) {
        int height = screen.getHeight();
        StringBuilder data = new StringBuilder();
        Map<Integer, Player[]> positionMap = new HashMap<>();
        //step = step > height ? height : step;
        IntStream.range(step, height + step).forEach(index -> {
            Player[] players = coordinateMap.get(index - step);
            if (players != null) {
                Arrays.stream(players)
                        .filter(player -> PlayerType.ALIEN.equals(player.getPlayerType()))
                        .forEach(player -> {
                            Point coordinate = player.getSpaceship().getCoordinate();
                            coordinate.y = index;
                        });
                if (height + step != index)
                    positionMap.put(index, players);
                else {
                    //positionMap.put(index, players);
                }
            }
            StringBuilder xData = drawXPosition(screen,step, players);
            data.append(xData);
            data.append(NEW_LINE);

        });
        coordinateMap.clear();
        coordinateMap.putAll(positionMap);
        return data;
    }*/

    private StringBuilder drawBattleField(Screen screen, int step) {
        int height = screen.getHeight();
        StringBuilder data = new StringBuilder();
        Map<Integer, Player[]> positionMap = new HashMap<>();
        //step = step > height ? height : step;
        int stepY = 1;
        IntStream.rangeClosed(stepY, height + stepY).forEach(index -> {
            Player[] players = coordinateMap.get(index - stepY);
            int stepX = 0;
            if (players != null) {
                Arrays.stream(players)
                        .filter(player -> PlayerType.ALIEN.equals(player.getPlayerType()))
                        .forEach(player -> {
                            Point coordinate = player.getSpaceship().getCoordinate();
                            coordinate.y = index;
                        });
                if (height + stepY != index)
                    positionMap.put(index, players);
                else {
                    stepX = step;
                    positionMap.put(index - stepY, players);
                }
            }
            StringBuilder xData = drawXPosition(screen, stepX, players);
            data.append(xData);
            data.append(NEW_LINE);

        });
        coordinateMap.clear();
        coordinateMap.putAll(positionMap);
        return data;
    }

    private StringBuilder drawXPosition(Screen screen, int step, Player... players) {
        int width = screen.getWidth() - 2;
        StringBuilder sb = new StringBuilder("|");
        for (int x = 0; x < width; x++) {
            String display = WHITE_SPACE;
            if (players != null) {
                for (Player player : players) {
                    Point coordinate = player.getSpaceship().getCoordinate();
                    if (coordinate.x == x) {
                        display = player.getSpaceship().getDisplay();
                        int displayWidth = display.length() - 1;
                        x = x + displayWidth;
                        if (step != 0) {
                            int dx = coordinate.x + step;
                            coordinate.x = dx > 0 ? Math.min(dx, width - displayWidth) : Math.max(dx, 1);
                        }
                        break;
                    }
                }
            }
            sb.append(display);
        }
        sb.append("|");
        return sb;
    }


    private void readInput(Screen screen) {
        this.readInput(screen, (String input) -> {
            input = input != null ? input.trim() : null;
            int steps = 0;
            char cmd = input.charAt(0);
            if (cmd == 'r' || cmd == 'R') {
                steps = countRepeativeLetter(input, 'r');
            } else if (cmd == 'l' || cmd == 'L') {
                steps = countRepeativeLetter(input, 'l');
                steps = Math.negateExact(steps);
            } else if ("exit".equalsIgnoreCase(input)) {
                screen.getDisplayExplorer().previous(screen);
                return;
            }
            renderGame(screen, steps);
        });
    }

    private int countRepeativeLetter(String input, char val) {
        char[] chars = input.toCharArray();
        int x = 0;
        for (char value : chars) {
            if (value != Character.toUpperCase(val) && value != Character.toLowerCase(val)) {
                return x;
            }
            x++;
        }
        return x;
    }

}
