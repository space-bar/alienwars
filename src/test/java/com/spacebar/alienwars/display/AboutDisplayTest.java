package com.spacebar.alienwars.display;


import org.junit.Test;

import static com.spacebar.alienwars.util.GameUtils.CMD_BACK;
import static com.spacebar.alienwars.util.GameUtils.CMD_EXIT;
import static com.spacebar.alienwars.util.GameUtils.CMD_HOME;


public class AboutDisplayTest extends AbstractDisplayTest {

    @Test
    public void shouldRenderABOUT_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenAssert(
                DisplayType.ABOUT,
                CMD_EXIT);
    }

    @Test
    public void shouldRenderABOUT_whenHomeAsInput_thenHOME_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenAssert(
                new DisplayType[]{DisplayType.ABOUT, DisplayType.HOME},
                CMD_HOME, CMD_EXIT);
    }

    @Test
    public void shouldRenderHOME_when4AsInput_thenABOUT_whenBackAsInput_thenHOME_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenAssert(
                DisplayType.HOME,
                "4", CMD_BACK, CMD_EXIT);
    }

}
