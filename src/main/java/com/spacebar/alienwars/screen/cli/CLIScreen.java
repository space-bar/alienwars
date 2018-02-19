package com.spacebar.alienwars.screen.cli;

import com.spacebar.alienwars.screen.AbstractScreen;
import com.spacebar.alienwars.screen.DisplayProvider;
import com.spacebar.alienwars.screen.IOStream;

public class CLIScreen extends AbstractScreen {

    private IOStream ioStream;


    private DisplayProvider displayProvider;

    public CLIScreen(int width, int height) {
        super(width, height);
    }

    public IOStream getIOStream() {
        if (ioStream == null) {
            ioStream = new CLIIOStream();
        }
        return ioStream;
    }

    @Override
    public DisplayProvider getDisplayProvider() {
        if (displayProvider == null) {
            displayProvider = new CLIDisplayProvider();
        }

        return displayProvider;
    }
}
