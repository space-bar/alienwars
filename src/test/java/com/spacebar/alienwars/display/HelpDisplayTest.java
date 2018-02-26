package com.spacebar.alienwars.display;

import org.junit.Test;

import static com.spacebar.alienwars.display.cli.AbstractCLIDisplay.CMD_BACK;
import static com.spacebar.alienwars.display.cli.AbstractCLIDisplay.CMD_EXIT;
import static com.spacebar.alienwars.display.cli.AbstractCLIDisplay.CMD_HOME;

public class HelpDisplayTest extends AbstractDisplayTest {

    @Test
    public void shouldRenderHELP_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenTerminate(DisplayType.HELP, CMD_EXIT);
    }


    @Test
    public void shouldRenderHELP_whenHomeAsInput_thenHOME_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenTerminate(
                DisplayType.HELP,
                new DisplayType[]{DisplayType.HOME, DisplayType.HELP},
                CMD_HOME, CMD_EXIT);
    }

    @Test
    public void shouldRenderHOME_when3AsInput_thenHELP_whenBackAsInput_thenHOME_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenTerminate(
                DisplayType.HOME,
                new DisplayType[]{DisplayType.HOME, DisplayType.HELP},
                "3", CMD_BACK, CMD_EXIT);
    }
}
