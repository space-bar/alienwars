package com.spacebar.alienwars.display;

import org.junit.Test;

import static com.spacebar.alienwars.util.GameUtils.CMD_BACK;
import static com.spacebar.alienwars.util.GameUtils.CMD_EXIT;
import static com.spacebar.alienwars.util.GameUtils.CMD_HOME;

public class HelpDisplayTest extends AbstractDisplayTest {

    @Test
    public void shouldRenderHELP_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenAssert(
                DisplayType.HELP,
                CMD_EXIT);
    }


    @Test
    public void shouldRenderHELP_whenHomeAsInput_thenHOME_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenAssert(
                new DisplayType[]{DisplayType.HELP, DisplayType.HOME},
                CMD_HOME, CMD_EXIT);
    }

    @Test
    public void shouldRenderHOME_when3AsInput_thenHELP_whenBackAsInput_thenHOME_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenAssert(
                new DisplayType[]{DisplayType.HOME},
                "3", CMD_BACK, CMD_EXIT);
    }

    @Test
    public void shouldRenderHELP_whenInvalidInput_thenDoNothing_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenAssert(
                DisplayType.HELP,
                "x", CMD_EXIT);
    }
}
