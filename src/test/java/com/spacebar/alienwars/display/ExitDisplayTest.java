package com.spacebar.alienwars.display;

import org.junit.Test;

public class ExitDisplayTest extends AbstractDisplayTest {

    @Test
    public void shouldRenderEXIT_thenTerminate() {
        exit.expectSystemExitWithStatus(0);
        screen.getDisplayExplorer().next(screen, DisplayType.EXIT);
    }
}
