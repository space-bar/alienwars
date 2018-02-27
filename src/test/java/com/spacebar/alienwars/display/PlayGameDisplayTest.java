package com.spacebar.alienwars.display;

import com.spacebar.alienwars.util.GameUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import static com.spacebar.alienwars.display.cli.AbstractCLIDisplay.CMD_EXIT;
import static com.spacebar.alienwars.display.cli.AbstractCLIDisplay.CMD_HOME;

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

}
