package com.spacebar.alienwars.display;


import com.spacebar.alienwars.util.GameUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.InputMismatchException;

import static com.spacebar.alienwars.util.GameUtils.CMD_BACK;
import static com.spacebar.alienwars.util.GameUtils.CMD_EXIT;
import static com.spacebar.alienwars.util.GameUtils.CMD_HOME;

public class LoadSavedGameDisplayTest extends AbstractDisplayTest {

    @Test
    public void shouldRenderLOADSAVEDGAME_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenAssert(
                DisplayType.LOAD_SAVED_GAME,
                CMD_EXIT);
    }


    @Test
    public void shouldRenderLOADSAVEDGAME_whenHomeAsInput_thenHOME_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenAssert(
                new DisplayType[]{DisplayType.LOAD_SAVED_GAME, DisplayType.HOME},
                CMD_HOME, CMD_EXIT);
    }

    @Test
    public void shouldRenderHOME_when2AsInput_thenLOADSAVEDGAME_whenBackAsInput_thenHOME_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenAssert(
                new DisplayType[]{DisplayType.HOME},
                "2", CMD_BACK, CMD_EXIT);
    }


    @Test
    public void shouldRenderLOADSAVEDGAME_when1AsInput_thenPLAYGAME_whenExitAsInput_thenTerminate() {
        String saveAsName = "TEST_GAME_" + System.currentTimeMillis();
        try {
            newGame();
            newSpaceship();
            GameUtils.saveGame(saveAsName, screen.getGame());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (GameUtils.getManifest().getProperty(saveAsName) == null) {
            return;
        }
        renderDisplay_whenInputs_thenAssert(
                new DisplayType[]{DisplayType.LOAD_SAVED_GAME, DisplayType.PLAY_GAME},
                "1", CMD_EXIT);
    }

}
