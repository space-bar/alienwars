package com.spacebar.alienwars.display;


import org.junit.Test;

import static com.spacebar.alienwars.display.cli.AbstractCLIDisplay.CMD_EXIT;

public class AboutDisplayTest extends AbstractDisplayTest {

    @Test
    public void shouldRenderABOUT_whenExitAsInput_thenRenderEXIT_thenTerminate() {
        systemInMock.provideLines(CMD_EXIT);
        exit.expectSystemExitWithStatus(0);
        screen.getDisplayExplorer().next(screen, DisplayType.ABOUT);
    }


}
