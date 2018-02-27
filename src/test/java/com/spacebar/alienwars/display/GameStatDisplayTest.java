package com.spacebar.alienwars.display;

import org.junit.Before;
import org.junit.Test;

import static com.spacebar.alienwars.util.GameUtils.CMD_BACK;
import static com.spacebar.alienwars.util.GameUtils.CMD_EXIT;
import static com.spacebar.alienwars.util.GameUtils.CMD_HOME;
import static com.spacebar.alienwars.util.PlayGameUtils.CMD_STAT;

public class GameStatDisplayTest extends AbstractDisplayTest {


    @Before
    public void setUp() throws Exception {
        super.setUp();
        newGame();
        newSpaceship();
    }

    @Test
    public void shouldRenderGAMESTAT_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenAssert(
                DisplayType.GAME_STAT,
                CMD_EXIT);
    }

    @Test
    public void shouldRenderGAMESTAT_whenHomeAsInput_thenHOME_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenAssert(
                new DisplayType[]{DisplayType.GAME_STAT, DisplayType.HOME},
                CMD_HOME, CMD_EXIT);
    }

    @Test
    public void shouldRenderPLAYGAME_whenStatAsInput_thenGAMESTAT_whenBackAsInput_thenPLAYGAME_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenAssert(
                new DisplayType[]{DisplayType.PLAY_GAME},
                CMD_STAT, CMD_BACK, CMD_EXIT);
    }
}
