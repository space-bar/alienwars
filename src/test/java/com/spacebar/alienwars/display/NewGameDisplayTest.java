package com.spacebar.alienwars.display;

import com.spacebar.alienwars.player.Player;
import com.spacebar.alienwars.util.GameUtils;
import org.junit.Assert;
import org.junit.Test;

import static com.spacebar.alienwars.util.GameUtils.CMD_BACK;
import static com.spacebar.alienwars.util.GameUtils.CMD_EXIT;
import static com.spacebar.alienwars.util.GameUtils.CMD_HOME;

public class NewGameDisplayTest extends AbstractDisplayTest {

    @Test
    public void shouldRenderNEWGAME_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenAssert(
                DisplayType.NEW_GAME,
                CMD_EXIT);
    }


    @Test
    public void shouldRenderNEWGAME_whenHomeAsInput_thenHOME_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenAssert(
                new DisplayType[]{DisplayType.NEW_GAME, DisplayType.HOME},
                CMD_HOME, CMD_EXIT);
    }

    @Test
    public void shouldRenderHOME_when1AsInput_thenNEWGAME_whenBackAsInput_thenHOME_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenAssert(
                DisplayType.HOME,
                "1", CMD_BACK, CMD_EXIT);
    }


    @Test
    public void shouldRenderNEWGAME_whenPlayerNameAsInput_thenSELECTSPACESHIP_whenExitAsInput_thenTerminate() {
        String playerName = "TestPlayer";
        exit.checkAssertionAfterwards(() ->
                Assert.assertEquals(playerName, screen.getGame().getCharacterPlayer().getPlayerName())
        );
        renderDisplay_whenInputs_thenAssert(
                new DisplayType[]{DisplayType.NEW_GAME, DisplayType.SELECT_SPACE_SHIP},
                playerName, CMD_EXIT);

    }

    @Test
    public void shouldRenderNEWGAME_whenInvalidInput_whenExitAsInput_thenTerminate() {
        String playerName = "T";
        exit.checkAssertionAfterwards(() ->
                Assert.assertNull(screen.getGame())
        );
        renderDisplay_whenInputs_thenAssert(
                new DisplayType[]{DisplayType.NEW_GAME},
                playerName, CMD_EXIT);

    }

}
