package com.spacebar.alienwars.display;


import org.junit.Before;
import org.junit.Test;

import static com.spacebar.alienwars.display.cli.AbstractCLIDisplay.CMD_BACK;
import static com.spacebar.alienwars.display.cli.AbstractCLIDisplay.CMD_EXIT;
import static com.spacebar.alienwars.display.cli.AbstractCLIDisplay.CMD_HOME;
import static com.spacebar.alienwars.display.cli.impl.PlayGame.CMD_STAT;

public class SaveGameDisplayTest extends AbstractDisplayTest {
    @Before
    public void setUp() throws Exception {
        super.setUp();
        newGame();
        newSpaceship();
    }

    @Test
    public void shouldRenderSAVEGAME_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenTerminate(DisplayType.SAVE_GAME, CMD_EXIT);
    }

    @Test
    public void shouldRenderSAVEGAME_whenHomeAsInput_thenHOME_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenTerminate(
                DisplayType.GAME_STAT,
                new DisplayType[]{DisplayType.HOME, DisplayType.SAVE_GAME},
                CMD_HOME, CMD_EXIT);
    }

    @Test
    public void shouldRenderPLAYGAME_whenStatAsInput_thenSAVEGAME_whenBackAsInput_thenPLAYGAME_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenTerminate(
                DisplayType.PLAY_GAME,
                new DisplayType[]{DisplayType.PLAY_GAME, DisplayType.SAVE_GAME},
                CMD_STAT, CMD_BACK, CMD_EXIT);
    }
}
