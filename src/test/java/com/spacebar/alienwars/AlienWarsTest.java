package com.spacebar.alienwars;

import com.spacebar.alienwars.display.AboutDisplayTest;
import com.spacebar.alienwars.display.ExitDisplayTest;
import com.spacebar.alienwars.display.HomeDisplayTest;
import com.spacebar.alienwars.display.NewGameDisplayTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        HomeDisplayTest.class,
        AboutDisplayTest.class,
        ExitDisplayTest.class,
        NewGameDisplayTest.class
})
public class AlienWarsTest extends AbstractCLITest {

    @Test
    public void shouldDisplayABOUTAndThenExit() {
    }
}
