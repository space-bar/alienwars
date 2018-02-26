package com.spacebar.alienwars;

import com.spacebar.alienwars.display.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.util.Arrays;

import static com.spacebar.alienwars.display.cli.AbstractCLIDisplay.CMD_EXIT;

@RunWith(Suite.class)
@Suite.SuiteClasses({

        AboutDisplayTest.class,
        ExitDisplayTest.class,
        GameStatDisplayTest.class,
        HelpDisplayTest.class,
        HomeDisplayTest.class,
        LoadSavedGameDisplayTest.class,
        NewGameDisplayTest.class,
        PlayGameDisplayTest.class,
        SaveGameDisplayTest.class,
        SelectSpaceshipDisplayTest.class
})
public class AlienWarsTest extends AbstractCLITest {

    @Test
    public void startApplication_renderHOME_whenExitAsInput_thenTerminate() {
        systemInMock.provideLines(CMD_EXIT);
        exit.expectSystemExitWithStatus(0);
        AlienWars.main(new String[0]);
    }
}
