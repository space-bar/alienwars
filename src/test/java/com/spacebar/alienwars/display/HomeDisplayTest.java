package com.spacebar.alienwars.display;

import org.junit.Test;

import static com.spacebar.alienwars.display.cli.AbstractCLIDisplay.CMD_EXIT;

public class HomeDisplayTest extends AbstractDisplayTest {

    @Test
    public void shouldRenderHOME_whenExitAsInput_thenRenderEXIT_thenTerminate() {
        systemInMock.provideLines(CMD_EXIT);
        exit.expectSystemExitWithStatus(0);
        screen.getDisplayExplorer().next(screen, DisplayType.HOME);
    }

    @Test
    public void shouldRenderHOME_when4AsInput_thenRenderABOUT_whenExitAsInput_thenRenderEXIT_thenTerminate() {
        systemInMock.provideLines("4", CMD_EXIT);
        exit.expectSystemExitWithStatus(0);
        screen.getDisplayExplorer().next(screen, DisplayType.HOME);
    }

    @Test
    public void shouldRenderHOME_when3AsInput_thenRenderHELP_whenExitAsInput_thenRenderEXIT_thenTerminate() {
        systemInMock.provideLines("3", CMD_EXIT);
        exit.expectSystemExitWithStatus(0);
        screen.getDisplayExplorer().next(screen, DisplayType.HOME);
    }
}
