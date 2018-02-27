package com.spacebar.alienwars.display.cli.impl;

import com.spacebar.alienwars.display.DisplayType;
import com.spacebar.alienwars.exception.GameIllegalStateException;
import com.spacebar.alienwars.exception.GameInitializationException;
import com.spacebar.alienwars.game.XPLogic;
import com.spacebar.alienwars.game.Game;
import com.spacebar.alienwars.game.GameStatus;
import com.spacebar.alienwars.player.Player;
import com.spacebar.alienwars.player.PlayerType;
import com.spacebar.alienwars.player.PlayerXP;
import com.spacebar.alienwars.screen.Screen;
import com.spacebar.alienwars.display.cli.AbstractCLIDisplay;
import com.spacebar.alienwars.screen.cli.CLIScreen;
import com.spacebar.alienwars.spaceship.Spaceship;
import com.spacebar.alienwars.util.GameUtils;
import com.spacebar.alienwars.weapon.Weapon;


import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class PlayGame extends AbstractCLIDisplay {
    public static final String CMD_STAT = "stat";

    public static final String CMD_SAVE = "save";

    private Map<Integer, Player[]> coordinateMap;


    public PlayGame() {
        super(DisplayType.PLAY_GAME);
    }

    @Override
    public void display(Screen screen) {
        try {
            coordinateMap = initCoordinateMap(screen);
            Game game = screen.getGame();
            if (!game.isPlaying()) {
                game.start();
            }
            renderGame(screen);
        } catch (Exception e) {
            //
        }
    }

    public static void main(String[] args) {
        new PlayGame().drawTagGroup(new CLIScreen(100, 30), 2, " Player : xxxxasa",
                " Level : 10 ",
                " Weapon Rounds : 10 ",
                " Points : 10 ",
                " Weapon Rounds : 10 ");
    }

    private void renderGame(Screen screen) {
        renderGame(screen, 0, 0);
    }

    private void renderGame(Screen screen, int steps, int shot) {

        StringBuilder battleField = drawBattleField(screen, steps, shot);

        GameStatus gameStatus = checkGameStatus(screen.getGame());
        if (gameStatus == GameStatus.LOST || gameStatus == GameStatus.ABORTED) {
            new EndGame().display(screen, gameStatus);
            return;
        }

        boolean levelUp = levelUp(screen, steps, shot);
        renderGame(screen, battleField, gameStatus, levelUp);

        if (levelUp || gameStatus == GameStatus.WON) {
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
            coordinateMap = initCoordinateMap(screen);
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

    private boolean levelUp(Screen screen, int steps, int shot) {
        XPLogic xpLogic = screen.getGame().getXPLogic();
        if (steps > 0) {
            xpLogic.addMove();
        }
        PlayerXP playerXP = screen.getGame().getCharacterPlayer().getPlayerXP();

        if (shot > 0) {
            xpLogic.computeXP(playerXP);
            xpLogic.reset();
        }
        if (playerXP.canLevelUp()) {
            playerXP.levelUp();
            return true;
        }
        return false;
    }

    private Map<Integer, Player[]> initCoordinateMap(Screen screen) {
        Game game = screen.getGame();
        Player[] alienPlayers = game.getAlienPlayers();
        Player characterPlayer = game.getCharacterPlayer();

        int width = screen.getWidth();
        int height = screen.getHeight();
        Map<Integer, Player[]> positionMap = new HashMap<>();

        //compute a random positon for alien spaceship if NOT IN PLAY
        int maxAlienPerRow = 4;
        int maxX = Math.max(width, alienPlayers.length) / Math.max(alienPlayers.length, 1);
        int x = 0;
        Random random = new Random();
        for (int index = 0; index < alienPlayers.length; index++) {
            Player player = alienPlayers[index];
            if (player.getSpaceship().isDestroyed() && game.isPlaying()) {
                player.setSpaceship(screen.getSpaceshipFactory().createSpaceship(player.getSpaceship().getSpaceshipType()));
            }

            Point coordinate = player.getSpaceship().getCoordinate();
            String display = player.getSpaceship().getDisplay();
            int m = Math.max(maxX, display.length());

            if (index >= maxAlienPerRow && (index % maxAlienPerRow) == 0) {
                x = 0;
                coordinate.y = coordinate.y > 0 ? coordinate.y : (index / maxAlienPerRow) + 1;
            }
            if (coordinate.x == 0) {
                coordinate.x = random.nextInt(Math.max(m * (index + 1) - x - display.length(), 1)) + x;
                x = coordinate.x + x + display.length();
            }

            Player[] players = addPlayerToGroup(player, positionMap.get(coordinate.y));
            positionMap.put(coordinate.y, players);
        }

        // position character player at the footer
        Point coordinate = characterPlayer.getSpaceship().getCoordinate();
        coordinate.x = coordinate.x > 0 ? coordinate.x : (width - characterPlayer.getSpaceship().getDisplay().length()) / 2;
        coordinate.y = height;
        positionMap.put(coordinate.y, new Player[]{characterPlayer});

        return positionMap;
    }

    private Player[] addPlayerToGroup(Player player, Player[] players) {
        Player[] playerz;
        if (players == null || players.length == 0) {
            playerz = new Player[]{player};
        } else {
            playerz = new Player[players.length + 1];
            System.arraycopy(players, 0, playerz, 0, players.length);
            playerz[players.length] = player;
        }
        return playerz;
    }

    private GameStatus checkGameStatus(Game game) {
        if (game.isPlaying()) {
            Spaceship spaceship = game.getCharacterPlayer().getSpaceship();
            if (spaceship.isDestroyed() || spaceship.getWeapon().getAvaliableRounds() <= 0) {
                return GameStatus.LOST;
            }
            if (Arrays.stream(game.getAlienPlayers())
                    .allMatch(player -> player.getSpaceship().isDestroyed())) {
                return GameStatus.WON;
            }
            return GameStatus.IN_PLAY;
        }
        return GameStatus.ABORTED;
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
                    Player[] playerList = positionMap.get(index - stepY);
                    if (playerList != null && Arrays.stream(playerList).allMatch(Objects::nonNull)) {
                        Arrays.stream(players).filter(Objects::nonNull).forEach(p ->
                                p.getSpaceship().destroy()
                        );
                    }
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
                        coord.x = coord.x + step;
                        int dx = coord.x + step;
                        coord.x = dx > 0 ? Math.min(dx, width - displayWidth) : Math.max(dx, 1);
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
                                .filter(i -> player.equals(players[i]))
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
        this.readInput(screen, (String input) -> {
            processInput(screen, input);
        }, true);
    }

    private void processInput(Screen screen, String input) {
        int steps = 0;
        int shot = 0;
        if (input != null && (input.trim().isEmpty() || input.equals("1"))) {
            boolean fired = screen.getGame().getCharacterPlayer().getSpaceship().getWeapon().fire();
            if (fired) {
                shot = computeHitShotX(screen.getGame());
            }
        } else {
            steps = processMoves(screen, trimValue(input));
        }
        renderGame(screen, steps, shot);
    }

    private int processMoves(Screen screen, String input) {
        int steps = 0;
        char cmd = Character.toLowerCase(input.charAt(0));
        switch (Character.toLowerCase(cmd)) {
            case 'l':
                steps = countRepetitiveLetter(input, cmd);
                steps = Math.negateExact(steps);
                break;
            case 'r':
                steps = countRepetitiveLetter(input, cmd);
                break;
            case 'a': //left
                steps = countRepetitiveLetter(input, cmd);
                steps = Math.negateExact(steps);
                break;
            case 'd': //right
                steps = countRepetitiveLetter(input, cmd);
                break;
            default: {
                processActions(screen, input);
            }
        }
        return steps;
    }

    private void processActions(Screen screen, String input) {
        switch (input.toLowerCase()) {
            case CMD_SAVE:
                pauseGameAndDisplay(screen, DisplayType.SAVE_GAME);
                break;
            case CMD_STAT:
                pauseGameAndDisplay(screen, DisplayType.GAME_STAT);
                break;

            case CMD_HOME:
                pauseGameAndDisplay(screen, DisplayType.HOME);
                break;
            case CMD_EXIT:
                pauseGameAndDisplay(screen, DisplayType.EXIT);
                break;
            default: {
                throw new InputMismatchException();
            }
        }
    }

    private void pauseGameAndDisplay(Screen screen, DisplayType displayType) {
        try {
            screen.getGame().pause(GameStatus.PAUSE);
            screen.getDisplayExplorer().next(screen, displayType);
        } catch (GameIllegalStateException e) {
            throw new InputMismatchException();
        }
    }

    private int countRepetitiveLetter(String input, char val) {
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

    private int computeHitShotX(Game game) {
        Player characterPlayer = game.getCharacterPlayer();
        Point coordinate = characterPlayer.getSpaceship().getCoordinate();
        return (coordinate.x + (characterPlayer.getSpaceship().getDisplay().length() / 2));
    }


}
