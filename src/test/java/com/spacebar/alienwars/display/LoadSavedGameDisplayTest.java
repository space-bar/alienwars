package com.spacebar.alienwars.display;

import org.junit.Test;

import static com.spacebar.alienwars.display.cli.AbstractCLIDisplay.CMD_BACK;
import static com.spacebar.alienwars.display.cli.AbstractCLIDisplay.CMD_EXIT;
import static com.spacebar.alienwars.display.cli.AbstractCLIDisplay.CMD_HOME;

public class LoadSavedGameDisplayTest extends AbstractDisplayTest {

    @Test
    public void shouldRenderLOADSAVEDGAME_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenTerminate(DisplayType.LOAD_SAVED_GAME, CMD_EXIT);
    }


    @Test
    public void shouldRenderLOADSAVEDGAME_whenHomeAsInput_thenHOME_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenTerminate(
                DisplayType.LOAD_SAVED_GAME,
                new DisplayType[]{DisplayType.HOME, DisplayType.LOAD_SAVED_GAME},
                CMD_HOME, CMD_EXIT);
    }

    @Test
    public void shouldRenderHOME_when2AsInput_thenLOADSAVEDGAME_whenBackAsInput_thenHOME_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenTerminate(
                DisplayType.HOME,
                new DisplayType[]{DisplayType.HOME, DisplayType.LOAD_SAVED_GAME},
                "2", CMD_BACK, CMD_EXIT);
    }


}
