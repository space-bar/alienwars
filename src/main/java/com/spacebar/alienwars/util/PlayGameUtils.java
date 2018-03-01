package com.spacebar.alienwars.util;

import com.spacebar.alienwars.display.DisplayType;
import com.spacebar.alienwars.exception.GameIllegalStateException;
import com.spacebar.alienwars.game.Game;
import com.spacebar.alienwars.game.GameStatus;
import com.spacebar.alienwars.game.XPLogic;
import com.spacebar.alienwars.player.Player;
import com.spacebar.alienwars.player.PlayerType;
import com.spacebar.alienwars.player.PlayerXP;
import com.spacebar.alienwars.screen.Screen;
import com.spacebar.alienwars.spaceship.Spaceship;

import java.awt.*;
import java.util.*;

import static com.spacebar.alienwars.util.GameUtils.CMD_EXIT;
import static com.spacebar.alienwars.util.GameUtils.CMD_HOME;

public class PlayGameUtils {
    public static final String CMD_STAT = "stat";

    public static final String CMD_SAVE = "save";

    private PlayGameUtils() {
    }

    /**
     * Builds a coordinate map exempting already assigned co-ordinates
     *
     * @param screen
     * @return
     */
    public static Map<Integer, Player[]> buildCoordinateMap(Screen screen) {
        Game game = screen.getGame();
        Player[] alienPlayers = game.getAlienPlayers();
        Player characterPlayer = game.getCharacterPlayer();

        int width = screen.getWidth() - 2;
        int height = screen.getHeight();
        Map<Integer, Player[]> positionMap = new HashMap<>();

        //compute a random positon for alien spaceship if NOT IN PLAY
        int maxAlienPerRow = Math.min(4,alienPlayers.length);
        int maxX = Math.max(width, alienPlayers.length) / Math.max(alienPlayers.length, 1);
        int x = 0;
        int y = 0;
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
                y++;
            }
            if (coordinate.x == 0) {
                coordinate.x = random.nextInt(Math.max((m * index) - x - display.length(), 1)) + x;
                coordinate.x = Math.min(coordinate.x, width - display.length());
                x = coordinate.x + x + display.length();
            }
            if (coordinate.y == 0) {
                coordinate.y = y;
            }

            Player[] players = PlayGameUtils.addPlayerToGroup(player, positionMap.get(coordinate.y));
            positionMap.put(coordinate.y, players);
        }

        // position character player at the footer
        Point coordinate = characterPlayer.getSpaceship().getCoordinate();
        coordinate.x = coordinate.x > 0 ? coordinate.x : (width - characterPlayer.getSpaceship().getDisplay().length()) / 2;
        coordinate.y = height;
        positionMap.put(coordinate.y, new Player[]{characterPlayer});

        return positionMap;
    }


    /// Game Game level Up and Status methods
    public static boolean levelUp(Screen screen, int steps, int shot) {
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
            screen.getGame().getCharacterPlayer().getSpaceship().getWeapon().reload();
            return true;
        }
        return false;
    }

    public static Player[] addPlayerToGroup(Player player, Player[] players) {
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

    public static GameStatus checkGameStatus(Game game) {
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

    /**
     * playerList(alien) and players(character) are either on the same level meaning the alien has landed or the user has no more health
     * check the condition and destroy character spaceship
     *
     * @param playerList
     * @param players
     */
    public static void checkPlayerGameStatus(Player[] playerList, Player... players) {
        if (playerList != null && Arrays.stream(playerList).anyMatch(p -> p != null && !p.getSpaceship().isDestroyed())) {
            Arrays.stream(players).filter(o -> o != null && o.getPlayerXP() != null).forEach(p -> {
                        p.getPlayerXP().subtractHealth(1);
                        if (p.getPlayerXP().getAvailableHealth() <= 0) {
                            p.getSpaceship().destroy();
                        }
                    }
            );
        }
    }

    public static boolean containsAliens(Map<Integer, Player[]> coordinateMap) {
        return coordinateMap.keySet().stream().anyMatch(key -> {
                    Player[] players = coordinateMap.get(key);
                    return players != null &&
                            Arrays.stream(players)
                                    .anyMatch(p -> p != null &&
                                            PlayerType.ALIEN.equals(p.getPlayerType()) &&
                                            !p.getSpaceship().isDestroyed());
                }
        );
    }

    // ======================== utils for minor computation
    public static void pauseGameAndDisplay(Screen screen, DisplayType displayType) {
        try {
            screen.getGame().pause(GameStatus.PAUSE);
            screen.getDisplayExplorer().next(screen, displayType);
        } catch (GameIllegalStateException e) {
            throw new InputMismatchException();
        }
    }

    public static int countRepetitiveLetter(String input, char val) {
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

    public static int computeHitShotX(Game game) {
        Player characterPlayer = game.getCharacterPlayer();
        Point coordinate = characterPlayer.getSpaceship().getCoordinate();
        return (coordinate.x + (characterPlayer.getSpaceship().getDisplay().length() / 2));
    }


    // ========================= Input processing
    public static int processInputMoves(Screen screen, String input) {
        int steps = 0;
        char cmd = Character.toLowerCase(input.charAt(0));
        switch (Character.toLowerCase(cmd)) {
            case 'l':
            case 'a': //left
                steps = PlayGameUtils.countRepetitiveLetter(input, cmd);
                steps = Math.negateExact(steps);
                break;
            case 'r':
            case 'd': //right
                steps = PlayGameUtils.countRepetitiveLetter(input, cmd);
                break;
            default:
                processInputActions(screen, input);

        }
        return steps;
    }

    public static void processInputActions(Screen screen, String input) {
        switch (input.toLowerCase()) {
            case CMD_SAVE:
                PlayGameUtils.pauseGameAndDisplay(screen, DisplayType.SAVE_GAME);
                break;
            case CMD_STAT:
                PlayGameUtils.pauseGameAndDisplay(screen, DisplayType.GAME_STAT);
                break;

            case CMD_HOME:
                PlayGameUtils.pauseGameAndDisplay(screen, DisplayType.HOME);
                break;
            case CMD_EXIT:
                PlayGameUtils.pauseGameAndDisplay(screen, DisplayType.EXIT);
                break;
            default:
                throw new InputMismatchException();
        }
    }


}
