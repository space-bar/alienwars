package com.spacebar.alienwars.display.cli.impl;

import com.spacebar.alienwars.display.DisplayType;
import com.spacebar.alienwars.game.XPLogic;
import com.spacebar.alienwars.game.Game;
import com.spacebar.alienwars.game.GameStatus;
import com.spacebar.alienwars.player.Player;
import com.spacebar.alienwars.player.PlayerType;
import com.spacebar.alienwars.player.PlayerXP;
import com.spacebar.alienwars.screen.Screen;
import com.spacebar.alienwars.display.cli.AbstractCLIDisplay;
import com.spacebar.alienwars.screen.cli.CLIScreen;
import com.spacebar.alienwars.weapon.Weapon;


import java.awt.*;
import java.util.*;
import java.util.List;
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
            Game game = screen.getGame();
            if (!game.isPlaying()) {
                game.start();
            }
            coordinateMap = initCoordinateMap(screen);
            renderGame(screen);
        } catch (Exception e) {
            //
            e.printStackTrace();
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
        boolean levelUp = levelUp(screen, steps, shot);
        if (gameStatus == GameStatus.IN_PLAY) {
            if (levelUp) {
                battleField = drawBattleField(screen, 0, 0);
            }
            drawHeader(screen, APP_BANNER.split(NEW_LINE));
            screen.getIOStream().write(battleField.toString());
            drawFooter(screen, APP_LOGO.split(NEW_LINE));

            Player characterPlayer = screen.getGame().getCharacterPlayer();
            PlayerXP playerXP = characterPlayer.getPlayerXP();
            Weapon weapon = characterPlayer.getSpaceship().getWeapon();
            drawTagGroup(screen, 2,

                    " Player : " + characterPlayer.getPlayerName(),
                    " Level : " + playerXP.getLevel(),
                    " Health : " + playerXP.getAvailableHealth() + " / " + playerXP.getHealth(),
                    " Points : " + playerXP.getXp() + " / " + playerXP.getMaxXp(),
                    " Weapon Rounds : " + weapon.getAvaliableRounds() + " / " + weapon.getRounds());

            readInput(screen);
        } else {
            new EndGame().display(screen, gameStatus);
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
            coordinateMap = initCoordinateMap(screen);
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


        //compute a random positon for alien spaceship
        int maxX = width / alienPlayers.length;
        int x = 0;
        Random random = new Random();
        for (int index = 0; index < alienPlayers.length; index++) {
            Player player = alienPlayers[index];
            if (player.getSpaceship().isDestroyed()) {
                //player = screen.getPlayerFactory().createPlayer(player.getPlayerType(), player.getPlayerName());
                player.setSpaceship(screen.getSpaceshipFactory().createSpaceship(player.getSpaceship().getSpaceshipType()));
                //alienPlayers[index] = player;
            }

            Point coordinate = player.getSpaceship().getCoordinate();
            String display = player.getSpaceship().getDisplay();
            int m = maxX - display.length();
            m = Math.max(m, 1);
            //coordinate.x = coordinate.x>0?coordinate.x:random.nextInt(m * (index + 1) - x) + x;
            if (coordinate.x == 0) {
                coordinate.x = random.nextInt(m * (index + 1) - x) + x;
                x = coordinate.x + x;
            }

            //coordinate.y = 0;
            Player[] players = positionMap.get(coordinate.y);
            Player[] playerz = null;
            if (players == null || players.length == 0) {
                players = new Player[]{player};
            } else {
                playerz = new Player[players.length + 1];
                System.arraycopy(players, 0, playerz, 0, players.length);
                playerz[players.length] = player;
            }

            positionMap.put(coordinate.y, playerz);
        }

        Point coordinate = characterPlayer.getSpaceship().getCoordinate();
        coordinate.x = coordinate.x > 0 ? coordinate.x : (width - characterPlayer.getSpaceship().getDisplay().length()) / 2;
        coordinate.y = height;


        Player[] playerAliens = new Player[alienPlayers.length];
        System.arraycopy(alienPlayers, 0, playerAliens, 0, alienPlayers.length);
        positionMap.put(0, playerAliens);
        positionMap.put(height, new Player[]{characterPlayer});

        return positionMap;
    }

    private GameStatus checkGameStatus(Game game) {
        if (game.isPlaying()) {
            if (game.getCharacterPlayer().getSpaceship().isDestroyed()) {
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
                    //endGame if position is already taken
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
                String displayX = computeXPosition(playerList, xIndex, players);
                if (displayX != null) {
                    display = displayX;
                }
            }
            sb.append(display);
        }
        sb.append("|");
        return sb;
    }

    private String computeXPosition(List<Player> playerList, AtomicInteger xIndex, Player... players) {
        int indexAt = -1;
        String display = null;
        int x = xIndex.get();
        for (int index = 0; index < playerList.size(); index++) {
            Player player = playerList.get(index);
            if (player != null) {
                Point coordinate = player.getSpaceship().getCoordinate();
                if (coordinate.x == x) {
                    display = player.getSpaceship().getDisplay();
                    if (player.getSpaceship().isDestroyed()) {
                        OptionalInt optionalIndex =
                                IntStream.range(0, players.length)
                                        .filter(i -> player.equals(players[i]))
                                        .findFirst();
                        if (optionalIndex.isPresent()) {
                            players[optionalIndex.getAsInt()] = null;
                        }
                    }
                    int displayWidth = display.length() - 1;
                    x = x + displayWidth;
                    indexAt = index;
                    break;
                }
            }
        }
        if (indexAt > -1 && indexAt < playerList.size()) { // remove to have one less to iterate over
            playerList.remove(indexAt);
        }
        xIndex.set(x);
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
        );
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
            input = trimValue(input);
            char cmd = Character.toLowerCase(input.charAt(0));

            if (cmd == 'r') {
                steps = countRepetitiveLetter(input, 'r');
            } else if (cmd == 'l') {
                steps = countRepetitiveLetter(input, 'l');
                steps = Math.negateExact(steps);
            } else if ("save".equalsIgnoreCase(input)) {
                screen.getDisplayExplorer().next(screen, DisplayType.SAVE_GAME);
            } else if ("exit".equalsIgnoreCase(input)) {
                screen.getDisplayExplorer().previous(screen);
                return;
            }
        }
        renderGame(screen, steps, shot);
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
