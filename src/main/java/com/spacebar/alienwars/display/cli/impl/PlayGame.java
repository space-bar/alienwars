package com.spacebar.alienwars.display.cli.impl;

import com.spacebar.alienwars.display.DisplayType;
import com.spacebar.alienwars.exception.GameInitializationException;
import com.spacebar.alienwars.game.Game;
import com.spacebar.alienwars.game.GameStatus;
import com.spacebar.alienwars.player.Player;
import com.spacebar.alienwars.player.PlayerType;
import com.spacebar.alienwars.player.PlayerXP;
import com.spacebar.alienwars.screen.Screen;
import com.spacebar.alienwars.display.cli.AbstractCLIDisplay;
import com.spacebar.alienwars.util.GameUtils;
import com.spacebar.alienwars.util.PlayGameUtils;
import com.spacebar.alienwars.weapon.Weapon;


import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class PlayGame extends AbstractCLIDisplay {

    private Map<Integer, Player[]> coordinateMap;


    public PlayGame() {
        super(DisplayType.PLAY_GAME);
    }

    @Override
    public void display(Screen screen) {
        try {
            coordinateMap = PlayGameUtils.buildCoordinateMap(screen);
            Game game = screen.getGame();
            if (!game.isPlaying()) {
                game.start();
            }
            renderGame(screen);
        } catch (Exception e) {
            //
        }
    }

    private void renderGame(Screen screen) {
        renderGame(screen, 0, 0);
    }

    private void renderGame(Screen screen, int steps, int shot) {

        StringBuilder battleField = drawBattleField(screen, steps, shot);

        GameStatus gameStatus = PlayGameUtils.checkGameStatus(screen.getGame());
        if (gameStatus == GameStatus.LOST || gameStatus == GameStatus.ABORTED) {
            new EndGame().display(screen, gameStatus);
            return;
        }

        boolean levelUp = PlayGameUtils.levelUp(screen, steps, shot);
        renderGame(screen, battleField, gameStatus, levelUp);

        if (levelUp || gameStatus == GameStatus.WON || !PlayGameUtils.containsAliens(coordinateMap)) {
            if (levelUp) {
                try {
                    Player[] aliens = GameUtils.createAliens(screen, screen.getGame().getCharacterPlayer().getPlayerXP().getEnemyCount());
                    screen.getGame().restart(aliens);
                } catch (GameInitializationException gie) {
                    // restart game for the next level
                    screen.getIOStream().write("Sorry Game could not be initialized!");
                    return;
                }
            }
            try {
                TimeUnit.SECONDS.sleep(4);// pause for so user can notice next level loading
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            coordinateMap = PlayGameUtils.buildCoordinateMap(screen);
            battleField = drawBattleField(screen, 0, 0);
            renderGame(screen, battleField, screen.getGame().getStatus(), false);
        }

        readInput(screen);
    }

    private void renderGame(Screen screen, StringBuilder battleField, GameStatus gameStatus, boolean levelUp) {
        drawHeader(screen, APP_BANNER.split(NEW_LINE));
        screen.getIOStream().write(battleField.toString());
        drawFooter(screen, APP_LOGO.split(NEW_LINE));

        if (levelUp) {
            drawBody(screen, "Nice !!! Level Completed.  Leveling Up...");
        } else if (gameStatus != null && gameStatus == GameStatus.WON) {
            drawBody(screen, "Wow !!! You won.  But not just yet...");
        } else {
            Player characterPlayer = screen.getGame().getCharacterPlayer();
            PlayerXP playerXP = characterPlayer.getPlayerXP();
            Weapon weapon = characterPlayer.getSpaceship().getWeapon();
            drawTagGroup(screen, 2,

                    " Player : " + characterPlayer.getPlayerName(),
                    " Level : " + playerXP.getLevel(),
                    " Health : " + playerXP.getAvailableHealth() + " / " + playerXP.getHealth(),
                    " Points : " + playerXP.getXp() + " / " + playerXP.getMaxXp(),
                    " Weapon Rounds : " + weapon.getAvaliableRounds() + " / " + weapon.getRounds());
        }
    }

    private StringBuilder drawBattleField(Screen screen, int step, int shot) {
        int height = screen.getHeight();
        StringBuilder data = new StringBuilder();
        Map<Integer, Player[]> positionMap = new HashMap<>();

        Point hitCoordinate = shot > 0 ? computeAlienHitCoordinate(shot) : null;
        AtomicInteger shotX = new AtomicInteger(0);

        if (hitCoordinate != null && hitCoordinate.x == -1) {
            //missed shot
            shotX.set(shot);
        }

        int stepY = 1;
        IntStream.rangeClosed(stepY, height + stepY).forEach(index -> {
            Player[] players = coordinateMap.get(index - stepY);
            if (players != null) {
                computeXYPosition(screen, players, hitCoordinate, shotX, index, step, shot);
                if (height + stepY != index) {
                    positionMap.put(index, players);
                } else {
                    PlayGameUtils.checkPlayerGameStatus(positionMap.get(index - stepY), players);
                    positionMap.put(index - stepY, players);
                }
            }
            StringBuilder xData = drawXPosition(screen, shotX.get(), players);
            data.append(xData);
            data.append(NEW_LINE);

        });
        coordinateMap.clear();
        coordinateMap.putAll(positionMap);
        return data;
    }


    private void computeXYPosition(Screen screen, Player[] players, Point hitCoordinate, AtomicInteger shotX, int index, int step, int shot) {
        Arrays.stream(players)
                .filter(Objects::nonNull)
                .forEach(player -> {
                    Point coord = player.getSpaceship().getCoordinate();
                    if (PlayerType.ALIEN.equals(player.getPlayerType())) {
                        if (hitCoordinate != null && coord.equals(hitCoordinate)) {
                            screen.getGame().getXPLogic().evaluateHit(player.getSpaceship(), shot);
                            player.getSpaceship().destroy();
                            shotX.set(shot);
                        }
                        coord.y = index;
                    } else if (step != 0) {
                        int width = screen.getWidth() - 2;
                        int displayWidth = player.getSpaceship().getDisplay().length() - 1;
                        int dx = coord.x + step;
                        coord.x = dx > 0 ? Math.min(dx, width - displayWidth - 1) : Math.max(dx, 0);
                    }
                });
    }

    private StringBuilder drawXPosition(Screen screen, int shot, Player... players) {
        int width = screen.getWidth() - 2;
        List<Player> playerList = players != null ? new ArrayList<>(Arrays.asList(players)) : null;
        StringBuilder sb = new StringBuilder("|");
        AtomicInteger xIndex = new AtomicInteger();
        for (int x = 0; x < width; x++) {
            if (x < xIndex.get()) {
                continue;
            }
            String display = WHITE_SPACE;
            if (shot > 0 && shot == x) {
                display = "^";
            }
            if (playerList != null) {
                xIndex.set(x);
                String displayX = computeXPosition(playerList, x, players);
                if (displayX != null) {
                    xIndex.set(x + displayX.length());
                    display = displayX;
                }
            }
            sb.append(display);
        }
        sb.append("|");
        return sb;
    }

    private String computeXPosition(List<Player> playerList, int x, Player... players) {
        int indexAt = -1;
        String display = null;
        for (int index = 0; index < playerList.size(); index++) {
            Player player = playerList.get(index);
            if (player != null) {
                Point coordinate = player.getSpaceship().getCoordinate();
                if (coordinate.x == x) {
                    display = player.getSpaceship().getDisplay();
                    if (player.getSpaceship().isDestroyed()) {
                        IntStream.range(0, players.length)
                                .filter(i -> player!=null && player.equals(players[i]))
                                .findFirst().ifPresent(i -> players[i] = null);
                    }
                    indexAt = index;
                    break;
                }
            }
        }
        if (indexAt > -1 && indexAt < playerList.size()) { // remove to have one less to iterate over
            playerList.remove(indexAt);
        }
        return display;
    }

    private Point computeAlienHitCoordinate(int shot) {
        Point hitCoordinate = new Point(-1, -1);
        coordinateMap.keySet().stream()
                .sorted(Comparator.reverseOrder())
                .filter(key -> {

                    Player[] players = coordinateMap.get(key);
                    if (players != null && players.length > 0) {
                        Optional<Player> optionalPlayer = Arrays.stream(players)
                                .filter(player -> {
                                    if (player != null && PlayerType.ALIEN.equals(player.getPlayerType())) {
                                        Point coord = player.getSpaceship().getCoordinate();
                                        String display = player.getSpaceship().getDisplay();
                                        return shot > 0 && shot >= coord.x && shot < coord.x + display.length();
                                    }
                                    return false;
                                }).findFirst();
                        if (optionalPlayer.isPresent()) {
                            Point coord = optionalPlayer.get().getSpaceship().getCoordinate();
                            hitCoordinate.setLocation(coord);
                        }
                        return optionalPlayer.isPresent();
                    }
                    return false;

                }).findFirst();

        return hitCoordinate;
    }

    private void readInput(Screen screen) {
        this.readInput(screen, (String input) ->
                        processInput(screen, input)
                , true);
    }

    private void processInput(Screen screen, String input) {
        int steps = 0;
        int shot = 0;
        if (input != null && (input.trim().isEmpty() || input.equals("1"))) {
            boolean fired = screen.getGame().getCharacterPlayer().getSpaceship().getWeapon().fire();
            if (fired) {
                shot = PlayGameUtils.computeHitShotX(screen.getGame());
            }
        } else {
            steps = PlayGameUtils.processInputMoves(screen, trimValue(input));
        }
        renderGame(screen, steps, shot);
    }
}
