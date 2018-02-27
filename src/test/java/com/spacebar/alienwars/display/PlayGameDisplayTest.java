package com.spacebar.alienwars.display;

import org.junit.Before;
import org.junit.Test;


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
    public void shouldRenderPLAYGAME_whenLInput_thenMoveLeft_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenAssert(
                new DisplayType[]{DisplayType.PLAY_GAME},
                "L", CMD_EXIT);
    }

    @Test
    public void shouldRenderPLAYGAME_whenAInput_thenMoveLeft_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenAssert(
                new DisplayType[]{DisplayType.PLAY_GAME},
                "A", CMD_EXIT);
    }
}
