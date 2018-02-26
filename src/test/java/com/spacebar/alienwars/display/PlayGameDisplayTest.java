package com.spacebar.alienwars.display;

import org.junit.Before;
import org.junit.Test;


import static com.spacebar.alienwars.display.cli.AbstractCLIDisplay.CMD_EXIT;

public class PlayGameDisplayTest extends AbstractDisplayTest {


    @Before
    public void setUp() throws Exception {
        super.setUp();
        newGame();
        newSpaceship();
    }

    @Test
    public void shouldRenderPLAYGAME_whenExitAsInput_thenExit() {
        systemInMock.provideLines(CMD_EXIT);
        exit.expectSystemExitWithStatus(0);
        screen.getDisplayExplorer().display(screen, DisplayType.PLAY_GAME);
    }

    @Test
    public void shouldRenderPLAYGAME_whenInvalidInput_thenExitAsInput_thenExit() {
        systemInMock.provideLines("X", CMD_EXIT);
        exit.expectSystemExitWithStatus(0);
        screen.getDisplayExplorer().display(screen, DisplayType.PLAY_GAME);
    }


}
