package com.spacebar.alienwars.display;

import org.junit.Test;

import static com.spacebar.alienwars.display.cli.AbstractCLIDisplay.CMD_EXIT;

public class ExitDisplayTest extends AbstractDisplayTest {

    @Test
    public void shouldRenderEXIT_thenTerminate() {
        renderDisplay_whenInputs_thenTerminate(DisplayType.EXIT);
    }
}
