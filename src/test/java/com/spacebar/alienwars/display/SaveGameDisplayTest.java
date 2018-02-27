package com.spacebar.alienwars.display;


import com.spacebar.alienwars.util.GameUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import static com.spacebar.alienwars.util.GameUtils.CMD_BACK;
import static com.spacebar.alienwars.util.GameUtils.CMD_EXIT;
import static com.spacebar.alienwars.util.GameUtils.CMD_HOME;
import static com.spacebar.alienwars.util.PlayGameUtils.CMD_STAT;

public class SaveGameDisplayTest extends AbstractDisplayTest {
    @Before
    public void setUp() throws Exception {
        super.setUp();
        newGame();
        newSpaceship();
    }

    @Test
    public void shouldRenderSAVEGAME_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenAssert(
                DisplayType.SAVE_GAME,
                CMD_EXIT);
    }

    @Test
    public void shouldRenderSAVEGAME_whenHomeAsInput_thenHOME_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenAssert(
                new DisplayType[]{DisplayType.SAVE_GAME, DisplayType.HOME},
                CMD_HOME, CMD_EXIT);
    }

    @Test
    public void shouldRenderPLAYGAME_whenSaveAsInput_thenSAVEGAME_whenBackAsInput_thenPLAYGAME_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenAssert(
                new DisplayType[]{DisplayType.PLAY_GAME},
                CMD_STAT, CMD_BACK, CMD_EXIT);
    }

    @Test
    public void shouldRenderSAVEGAME_whenGameNameAsInput_thenSaved_whenExitAsInput_thenTerminate() {
        String saveAsName = "TEST_GAME_" + System.currentTimeMillis();
        exit.checkAssertionAfterwards(() ->
                Assert.assertNotNull(GameUtils.getManifest().getProperty(saveAsName))
        );
        renderDisplay_whenInputs_thenAssert(
                DisplayType.SAVE_GAME,
                saveAsName, CMD_EXIT);

    }

    @Test
    public void shouldRenderSAVEGAME_whenInvalidInput_thenNotSaved_whenExitAsInput_thenTerminate() {
        String saveAsName = "XX";
        exit.checkAssertionAfterwards(() ->
                Assert.assertNull(GameUtils.getManifest().getProperty(saveAsName))
        );
        renderDisplay_whenInputs_thenAssert(
                DisplayType.SAVE_GAME,
                saveAsName, CMD_EXIT);

    }

}
