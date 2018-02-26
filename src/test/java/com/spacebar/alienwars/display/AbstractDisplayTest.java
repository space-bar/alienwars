package com.spacebar.alienwars.display;

import com.spacebar.alienwars.AbstractCLITest;
import com.spacebar.alienwars.game.Game;
import com.spacebar.alienwars.spaceship.Spaceship;
import com.spacebar.alienwars.spaceship.SpaceshipType;
import com.spacebar.alienwars.util.GameUtils;
import org.junit.Assert;

import java.util.Arrays;
import java.util.Random;

import static com.spacebar.alienwars.display.cli.AbstractCLIDisplay.CMD_EXIT;


public abstract class AbstractDisplayTest extends AbstractCLITest {
    private static final SpaceshipType[] CHARACTER_SPACESHIP_TYPES = Arrays.stream(SpaceshipType.values())
            .filter(spaceshipType -> spaceshipType.isAlien())
            .toArray(size -> new SpaceshipType[size]);

    protected void newGame() {
        Game game = GameUtils.createGame(screen, "TestGamer");
        screen.setGame(game);
    }


    protected void newSpaceship() {
        int x = new Random().nextInt(CHARACTER_SPACESHIP_TYPES.length);
        Spaceship spaceship = GameUtils.createSpaceship(screen, CHARACTER_SPACESHIP_TYPES[x]);
        screen.getGame().getCharacterPlayer().setSpaceship(spaceship);
    }

    protected void renderDisplay_whenInputs_thenTerminate(DisplayType displayType, String... inputs) {
        renderDisplay_whenInputs_thenTerminate(displayType, new DisplayType[]{displayType}, inputs);
    }

    protected void renderDisplay_whenInputs_thenTerminate(DisplayType displayType, DisplayType[] assertDisplayTypes, String... inputs) {
        if (inputs != null && inputs.length > 0) {
            systemInMock.provideLines(inputs);
        }
        exit.expectSystemExitWithStatus(0);
        screen.getDisplayExplorer().next(screen, displayType);
        if (assertDisplayTypes != null) {
            Arrays.stream(assertDisplayTypes).forEach(d ->
                    Assert.assertTrue(screen.getDisplayExplorer().history(d))
            );
        }
        Assert.assertEquals(DisplayType.EXIT, screen.getDisplayExplorer().current().getDisplayType());
    }
}
