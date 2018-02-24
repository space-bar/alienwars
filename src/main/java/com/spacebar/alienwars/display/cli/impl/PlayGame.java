package com.spacebar.alienwars.display.cli.impl;

import com.spacebar.alienwars.display.DisplayType;
import com.spacebar.alienwars.game.Game;
import com.spacebar.alienwars.player.Player;
import com.spacebar.alienwars.player.PlayerType;
import com.spacebar.alienwars.screen.Screen;
import com.spacebar.alienwars.display.cli.AbstractCLIDisplay;

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
            renderGame(screen);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void renderGame(Screen screen) {
        renderGame(screen, 0, 0);
    }

    private void renderGame(Screen screen, int steps, int shot) {
        StringBuilder battleField = drawBattleField(screen, steps, shot);

        drawHeader(screen, APP_BANNER.split(NEW_LINE));
        screen.getIOStream().write(battleField.toString());
        drawFooter(screen, APP_LOGO.split(NEW_LINE));
        drawTag(screen," Player : " + screen.getGame().getCharacterPlayer().getPlayerName(),"Weapon Rounds : 10");
        //drawTag(screen, " Player : " + screen.getGame().getCharacterPlayer().getPlayerName(), false);

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
        coordinate.y = height;

        Map<Integer, Player[]> positionMap = new HashMap<>();
        positionMap.put(0, alienPlayers);
        positionMap.put(height, new Player[]{characterPlayer});

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

    private StringBuilder drawBattleField(Screen screen, int step, int shot) {
        int height = screen.getHeight();
        StringBuilder data = new StringBuilder();
        Map<Integer, Player[]> positionMap = new HashMap<>();
        //step = step > height ? height : step;

        Point hitCoordinate = shot > 0 ? computeAlienHitCoordinate(shot) : null;
        AtomicInteger shotX = new AtomicInteger(0);
        // AtomicInteger stepX = new AtomicInteger(0);
        if (hitCoordinate != null && hitCoordinate.x == -1) {
            //missed shot
            shotX.set(shot);
        }

        int stepY = 1;
        IntStream.rangeClosed(stepY, height + stepY).forEach(index -> {
            Player[] players = coordinateMap.get(index - stepY);
            int stepX = 0;
            if (players != null) {
                Arrays.stream(players)
                        .filter(player -> player != null)
                        .forEach(player -> {
                            Point coord = player.getSpaceship().getCoordinate();
                            if (PlayerType.ALIEN.equals(player.getPlayerType())) {
                                if (hitCoordinate != null && coord.equals(hitCoordinate)) {
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
                if (height + stepY != index) {
                    positionMap.put(index, players);
                } else {
                    stepX = step;
                    positionMap.put(index - stepY, players);
                    //endGame if position is already taken
                }
            }
            StringBuilder xData = drawXPosition(screen, stepX, shotX.get(), players);
            data.append(xData);
            data.append(NEW_LINE);

        });
        coordinateMap.clear();
        coordinateMap.putAll(positionMap);
        return data;
    }

    private StringBuilder drawXPosition(Screen screen, int step, int shot, Player... players) {
        int width = screen.getWidth() - 2;
        List<Player> playerList = players != null ? new ArrayList<>(Arrays.asList(players)) : null;
        StringBuilder sb = new StringBuilder("|");

        for (int x = 0; x < width; x++) {
            String display = WHITE_SPACE;
            if (shot > 0 && shot == x) {
                display = "^";
            }
            if (playerList != null) {
                int indexAt = -1;
                for (int index = 0; index < playerList.size(); index++) {
                    Player player = playerList.get(index);
                    if (player != null) {
                        Point coordinate = player.getSpaceship().getCoordinate();
                        if (coordinate.x == x) {
                            display = player.getSpaceship().getDisplay();
                            if (player.getSpaceship().isDestroyed()) {
                                display = contents(display.length(), 'X');
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
                            /*if (step != 0) {
                                int dx = coordinate.x + step;
                                coordinate.x = dx > 0 ? Math.min(dx, width - displayWidth) : Math.max(dx, 1);
                            }*/
                            indexAt = index;
                            break;
                        }
                    }
                }
                if (indexAt > -1 && indexAt < playerList.size()) { // remove to have one less to iterate over
                    playerList.remove(indexAt);
                }
            }
            sb.append(display);
        }
        sb.append("|");
        return sb;
    }

/*    private int computePlayerStepX(int step, Player... players) {
        Player characterPlayer = game.getCharacterPlayer();
        Point coordinate = characterPlayer.getSpaceship().getCoordinate();
        return (coordinate.x + characterPlayer.getSpaceship().getDisplay().length() - 2) + step;
    }*/

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
            int steps = 0;
            int shot = 0;
            if (input != null && (input.trim().isEmpty() || input.equals("1"))) {
                boolean fired = screen.getGame().getCharacterPlayer().getSpaceship().getWeapon().fire();
                if (fired) {
                    shot = computeHitShotX(screen.getGame());
                }
            } else {
                input = input != null ? input.trim() : null;
                char cmd = input.charAt(0);
                if (cmd == 'r' || cmd == 'R') {
                    steps = countRepetitiveLetter(input, 'r');
                } else if (cmd == 'l' || cmd == 'L') {
                    steps = countRepetitiveLetter(input, 'l');
                    steps = Math.negateExact(steps);
                } else if ("exit".equalsIgnoreCase(input)) {
                    screen.getDisplayExplorer().previous(screen);
                    return;
                }
            }
            renderGame(screen, steps, shot);
        });
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
