package com.spacebar.alienwars.display;

import org.junit.Test;


import static com.spacebar.alienwars.display.cli.AbstractCLIDisplay.CMD_EXIT;

public class SelectSpaceshipDisplayTest extends AbstractDisplayTest {


    @Test
    public void shouldRenderSELECTSPACESHIP_whenExitAsInput_thenTerminate() {
        newGame();

        systemInMock.provideLines(CMD_EXIT);
        exit.expectSystemExitWithStatus(0);
        screen.getDisplayExplorer().next(screen, DisplayType.SELECT_SPACE_SHIP);
    }

    @Test
    public void shouldRenderSELECTSPACESHIP_whenInvalidInput_thenExitAsInput_thenTerminate() {
        newGame();

        systemInMock.provideLines("X", CMD_EXIT);
        exit.expectSystemExitWithStatus(0);
        screen.getDisplayExplorer().next(screen, DisplayType.SELECT_SPACE_SHIP);
    }

    @Test
    public void shouldRenderNEWGAME_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenTerminate(DisplayType.NEW_GAME, CMD_EXIT);
    }


  /*  @Test
    public void shouldRenderNEWGAME_whenHomeAsInput_thenHOME_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenTerminate(
                DisplayType.NEW_GAME,
                new DisplayType[]{DisplayType.HOME, DisplayType.NEW_GAME},
                CMD_HOME, CMD_EXIT);
    }

    @Test
    public void shouldRenderHOME_when1AsInput_thenNEWGAME_whenBackAsInput_thenHOME_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenTerminate(
                DisplayType.HOME,
                new DisplayType[]{DisplayType.HOME, DisplayType.NEW_GAME},
                "1", CMD_BACK, CMD_EXIT);
    }

    //
    @Test
    public void shouldRenderNEWGAME_whenPlayerNameAsInput_thenSELECTSPACESHIP_whenExitAsInput_thenTerminate() {
        String playerName = "Test Player";
        renderDisplay_whenInputs_thenTerminate(
                DisplayType.NEW_GAME,
                new DisplayType[]{DisplayType.SELECT_SPACE_SHIP, DisplayType.NEW_GAME},
                playerName, CMD_EXIT);
        Assert.assertEquals(playerName, screen.getGame().getCharacterPlayer().getPlayerName());
    }*/
}
