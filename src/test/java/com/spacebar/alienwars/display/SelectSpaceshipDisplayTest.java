package com.spacebar.alienwars.display;

import com.spacebar.alienwars.player.Player;
import com.spacebar.alienwars.spaceship.SpaceshipType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


import static com.spacebar.alienwars.util.GameUtils.CMD_BACK;
import static com.spacebar.alienwars.util.GameUtils.CMD_EXIT;
import static com.spacebar.alienwars.util.GameUtils.CMD_HOME;

public class SelectSpaceshipDisplayTest extends AbstractDisplayTest {


    @Before
    public void setUp() throws Exception {
        super.setUp();
        newGame();
    }

    @Test
    public void shouldRenderSELECTSPACESHIP_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenAssert(
                DisplayType.SELECT_SPACE_SHIP,
                CMD_EXIT);
    }


    @Test
    public void shouldRenderSELECTSPACESHIP_whenHomeAsInput_thenHOME_whenExitAsInput_thenTerminate() {
        renderDisplay_whenInputs_thenAssert(
                new DisplayType[]{DisplayType.SELECT_SPACE_SHIP, DisplayType.HOME},
                CMD_HOME, CMD_EXIT);
    }

    //Assert for back feature
    @Test
    public void shouldRenderNEWGAME_whenPlayerNameAsInput_thenSELECTSPACESHIP_whenBackAsInput_thenNEWGAME_whenExitAsInput_thenTerminate() {
        Player oldPlayer = screen.getGame().getCharacterPlayer();
        String playerName = "TestPlayer2";

        exit.checkAssertionAfterwards(() -> {
            Player newPlayer = screen.getGame().getCharacterPlayer();
            Assert.assertNotEquals(newPlayer, oldPlayer);
            Assert.assertEquals(newPlayer.getPlayerName(), playerName);
        });

        renderDisplay_whenInputs_thenAssert(
                new DisplayType[]{DisplayType.NEW_GAME},
                playerName, CMD_BACK, CMD_EXIT);
    }

    //
    @Test
    public void shouldRenderSELECTSPACESHIP_when1AsInput_thenPLAYGAME_whenExitAsInput_thenTerminate() {
        exit.checkAssertionAfterwards(() ->
                Assert.assertEquals(SpaceshipType.values()[0], screen.getGame().getCharacterPlayer().getSpaceship().getSpaceshipType())
        );

        renderDisplay_whenInputs_thenAssert(
                new DisplayType[]{DisplayType.SELECT_SPACE_SHIP, DisplayType.PLAY_GAME},
                "1", CMD_EXIT);
    }

    @Test
    public void shouldRenderSELECTSPACESHIP_whenInvalidInput_thenExitAsInput_thenTerminate() {
        exit.checkAssertionAfterwards(() ->
                Assert.assertNull(screen.getGame().getCharacterPlayer().getSpaceship())
        );
        renderDisplay_whenInputs_thenAssert(
                new DisplayType[]{DisplayType.SELECT_SPACE_SHIP},
                "X", CMD_EXIT);

    }
}
