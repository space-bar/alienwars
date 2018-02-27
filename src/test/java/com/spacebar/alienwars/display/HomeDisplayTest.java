package com.spacebar.alienwars.display;

import org.junit.Test;

import static com.spacebar.alienwars.util.GameUtils.CMD_EXIT;
import static com.spacebar.alienwars.util.GameUtils.CMD_HOME;

public class HomeDisplayTest extends AbstractDisplayTest {

    @Test
    public void shouldRenderHOME_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenAssert(
                DisplayType.HOME,
                CMD_EXIT);
    }

    @Test
    public void shouldRenderHOME_when4AsInput_thenABOUT_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenAssert(
                new DisplayType[]{DisplayType.HOME, DisplayType.ABOUT},
                "4", CMD_EXIT);
    }

    @Test
    public void shouldRenderHOME_when3AsInput_thenHELP_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenAssert(
                new DisplayType[]{DisplayType.HOME, DisplayType.HELP},
                "3", CMD_EXIT);
    }

    @Test
    public void shouldRenderHOME_when2AsInput_thenLOADSAVEDGAME_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenAssert(
                new DisplayType[]{DisplayType.HOME, DisplayType.LOAD_SAVED_GAME},
                "2", CMD_EXIT);
    }

    @Test
    public void shouldRenderHOME_when1AsInput_thenNEWGAME_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenAssert(
                new DisplayType[]{DisplayType.HOME, DisplayType.NEW_GAME},
                "1", CMD_EXIT);
    }

    @Test
    public void shouldRenderHOME_whenInvalidInput_thenHELP_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenAssert(
                DisplayType.HOME,
                "x", CMD_EXIT);
    }

    @Test
    public void shouldRenderHOME_whenHomeAsInput_thenDoesNothing_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenAssert(
                DisplayType.HOME,
                CMD_HOME, CMD_EXIT);
    }
}
