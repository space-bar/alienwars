package com.spacebar.alienwars.display;

import com.spacebar.alienwars.player.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import java.awt.*;
import java.util.Arrays;

import static com.spacebar.alienwars.util.GameUtils.CMD_BACK;
import static com.spacebar.alienwars.util.GameUtils.CMD_EXIT;
import static com.spacebar.alienwars.util.GameUtils.CMD_HOME;
import static com.spacebar.alienwars.util.PlayGameUtils.CMD_SAVE;
import static com.spacebar.alienwars.util.PlayGameUtils.CMD_STAT;

public class PlayGameDisplayTest extends AbstractDisplayTest {


    @Before
    public void setUp() throws Exception {
        super.setUp();
        newGame();
        newSpaceship();
    }

    @Test
    public void shouldRenderPLAYGAME_whenExitAsInput_thenExit() {
        renderDisplay_whenInputs_thenAssert(
                DisplayType.PLAY_GAME,
                CMD_EXIT);
    }

    @Test
    public void shouldRenderPLAYGAME_whenHomeAsInput_thenHOME_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenAssert(
                new DisplayType[]{DisplayType.PLAY_GAME, DisplayType.HOME},
                CMD_HOME, CMD_EXIT);
    }

    @Test
    public void shouldRenderPLAYGAME_whenInvalidInput_thenExitAsInput_thenExit() {
        String input = "XX";
        renderDisplay_whenInputs_thenAssert(
                DisplayType.PLAY_GAME,
                input, CMD_EXIT);
    }

    @Test
    public void shouldRenderPLAYGAME_whenSaveAsInput_thenSAVEGAME_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenAssert(
                new DisplayType[]{DisplayType.PLAY_GAME, DisplayType.SAVE_GAME},
                CMD_SAVE, CMD_EXIT);
    }

    @Test
    public void shouldRenderPLAYGAME_whenStatInput_thenGAMESTATE_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenAssert(
                new DisplayType[]{DisplayType.PLAY_GAME, DisplayType.GAME_STAT},
                CMD_STAT, CMD_EXIT);
    }

    @Test
    public void shouldRenderPLAYGAME_when1AsInput_thenShoot_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenAssert(
                new DisplayType[]{DisplayType.PLAY_GAME},
                "1", CMD_EXIT);
    }

    @Test
    public void shouldRenderPLAYGAME_whenRInput_thenMoveRight_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenAssert(
                new DisplayType[]{DisplayType.PLAY_GAME},
                "R", CMD_EXIT);
    }

    @Test
    public void shouldRenderPLAYGAME_whenDInput_thenMoveRight_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenAssert(
                new DisplayType[]{DisplayType.PLAY_GAME},
                "D", CMD_EXIT);
    }


    @Test
    public void shouldRenderPLAYGAME_when1AsInput_thenAlienHitAndHeadshot_whenExitAsInput_thenTerminate() {
        Player[] alienPlayers = screen.getGame().getAlienPlayers();
        Point coord = alienPlayers[0].getSpaceship().getCoordinate();
        coord.x = ((screen.getWidth() - 2) / 2) - (alienPlayers[0].getSpaceship().getDisplay().length() / 2) - 1;
        coord.y = 2;
        Assert.assertFalse(alienPlayers[0].getSpaceship().isDestroyed());
        exit.checkAssertionAfterwards(() -> {
            Assert.assertTrue(alienPlayers[0].getSpaceship().isDestroyed());
            Assert.assertTrue(screen.getGame().getXPLogic().getHeadShotCounter() > 0);
        });
        renderDisplay_whenInputs_thenAssert(
                new DisplayType[]{DisplayType.PLAY_GAME},
                "1", CMD_EXIT);
    }

    @Test
    public void shouldRenderPLAYGAME_whenRInput_thenMoveLeft_whenExitAsInput_thenTerminate() {
        renderPLAYGAME_thenMove_whenExitAsInput_thenTerminate("RRR", true);
    }

    @Test
    public void shouldRenderPLAYGAME_whenDInput_thenMoveLeft_whenExitAsInput_thenTerminate() {
        renderPLAYGAME_thenMove_whenExitAsInput_thenTerminate("DDD", true);
    }


    @Test
    public void shouldRenderPLAYGAME_whenLInput_thenMoveLeft_whenExitAsInput_thenTerminate() {
        renderPLAYGAME_thenMove_whenExitAsInput_thenTerminate("LLL", false);
    }

    @Test
    public void shouldRenderPLAYGAME_whenAInput_thenMoveLeft_whenExitAsInput_thenTerminate() {
        renderPLAYGAME_thenMove_whenExitAsInput_thenTerminate("AAA", false);
    }

    private void renderPLAYGAME_thenMove_whenExitAsInput_thenTerminate(String steps, boolean moveRight) {
        Player player = screen.getGame().getCharacterPlayer();
        Point coord = player.getSpaceship().getCoordinate();
        int x = ((screen.getWidth() - 2) / 2) - (player.getSpaceship().getDisplay().length() / 2) - 1;
        coord.x = x;
        Assert.assertTrue(player.getSpaceship().getCoordinate().x == x);
        int move = steps.length() * (moveRight ? 1 : -1);
        exit.checkAssertionAfterwards(() -> {
            Assert.assertTrue(player.getSpaceship().getCoordinate().x == (x + move));
        });
        renderDisplay_whenInputs_thenAssert(
                new DisplayType[]{DisplayType.PLAY_GAME},
                steps, CMD_EXIT);
    }


    @Test
    public void shouldRenderPLAYGAME_whenAlienMove_thenLost_whenExitAsInput_thenTerminate() {
        assertGameLost();
        renderDisplay_whenInputs_thenAssert(
                new DisplayType[]{DisplayType.PLAY_GAME},
                "L", CMD_EXIT);
    }

    @Test
    public void shouldRenderPLAYGAME_whenAlienMove_thenLost_whenSaveAsInput_thenSAVEGAME_whenExitAsInput_thenTerminate() {
        assertGameLost();
        renderDisplay_whenInputs_thenAssert(
                new DisplayType[]{DisplayType.PLAY_GAME, DisplayType.SAVE_GAME},
                "L", CMD_SAVE, CMD_EXIT);
    }


    private void assertGameLost() {
        Player[] alienPlayers = screen.getGame().getAlienPlayers();
        Point coord = alienPlayers[0].getSpaceship().getCoordinate();
        coord.y = screen.getHeight() - 1;
        Assert.assertFalse(screen.getGame().getCharacterPlayer().getSpaceship().isDestroyed());
        exit.checkAssertionAfterwards(() -> {
            Assert.assertTrue(screen.getGame().getCharacterPlayer().getSpaceship().isDestroyed());
        });
    }


    @Test
    public void shouldRenderPLAYGAME_when1AsInput_thenWin_whenExitAsInput_thenTerminate() {
        Player[] alienPlayers = screen.getGame().getAlienPlayers();
        String[] input = new String[alienPlayers.length + 1];
        for (int i = 0; i < alienPlayers.length; i++) {
            Point coord = alienPlayers[i].getSpaceship().getCoordinate();
            coord.y = i;
            coord.x = ((screen.getWidth() - 2) / 2) - (alienPlayers[0].getSpaceship().getDisplay().length() / 2) - 1;
            input[i] = "1";
        }
        input[input.length - 1] = CMD_EXIT;
        Arrays.stream(alienPlayers).forEach(p -> Assert.assertFalse(p.getSpaceship().isDestroyed()));
        exit.checkAssertionAfterwards(() -> {
            Arrays.stream(alienPlayers).forEach(p -> Assert.assertTrue(p.getSpaceship().isDestroyed()));
        });
        renderDisplay_whenInputs_thenAssert(
                new DisplayType[]{DisplayType.PLAY_GAME},
                input);
    }

}
