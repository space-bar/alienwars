package com.spacebar.alienwars.display;

import org.junit.Assert;
import org.junit.Test;

import static com.spacebar.alienwars.display.cli.AbstractCLIDisplay.CMD_BACK;
import static com.spacebar.alienwars.display.cli.AbstractCLIDisplay.CMD_EXIT;
import static com.spacebar.alienwars.display.cli.AbstractCLIDisplay.CMD_HOME;

public class NewGameDisplayTest extends AbstractDisplayTest {

    @Test
    public void shouldRenderNEWGAME_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenTerminate(DisplayType.NEW_GAME, CMD_EXIT);
    }


    @Test
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
    }
}
