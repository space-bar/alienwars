package com.spacebar.alienwars.display;

import com.spacebar.alienwars.AbstractCLITest;
import com.spacebar.alienwars.game.Game;
import com.spacebar.alienwars.spaceship.Spaceship;
import com.spacebar.alienwars.spaceship.SpaceshipType;
import com.spacebar.alienwars.util.GameUtils;
import org.junit.Assert;

import java.util.*;



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

    protected void renderDisplay_whenInputs_thenAssert(DisplayType displayType, String... inputs) {
        renderDisplay_whenInputs_thenAssert(new DisplayType[]{displayType}, inputs);
    }

    protected void renderDisplay_whenInputs_thenAssert(DisplayType[] expectedDisplayTypes, String... inputs) {
        if (inputs != null && inputs.length > 0) {
            systemInMock.provideLines(inputs);
        }
        exit.expectSystemExitWithStatus(0);
        if (expectedDisplayTypes.length > 0) {
            exit.checkAssertionAfterwards(() -> {
                DisplayType[] actualDisplayTypes = screen.getDisplayExplorer().history();
                if (actualDisplayTypes.length > 1) {
                    List<DisplayType> displayTypes = Arrays.asList(actualDisplayTypes);
                    Collections.reverse(displayTypes);
                    actualDisplayTypes = displayTypes.toArray(new DisplayType[actualDisplayTypes.length]);
                }
                Assert.assertArrayEquals(expectedDisplayTypes, actualDisplayTypes);
            });

            screen.getDisplayExplorer().next(screen, expectedDisplayTypes[0]);
        }
    }

}
