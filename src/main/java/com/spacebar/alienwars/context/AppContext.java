package com.spacebar.alienwars.context;

import com.spacebar.alienwars.screen.DisplayProvider;
import com.spacebar.alienwars.screen.IOStream;
import com.spacebar.alienwars.screen.Screen;
import com.spacebar.alienwars.screen.cli.CLIDisplayProvider;
import com.spacebar.alienwars.screen.cli.CLIIOStream;

public class AppContext {

    private static AppContext instance;

    private boolean windows;

    private IOStream ioStream;

    private Screen screen;

    private DisplayProvider displayProvider;

    {
        String os = System.getProperty("os.name");
        windows = os != null && os.contains("Windows");

        ioStream = new CLIIOStream();
        screen = new Screen(100, 20, ioStream);
        displayProvider = new CLIDisplayProvider();
    }


    private AppContext() {
    }

    public static AppContext getInstance() {
        if (instance == null) {
            instance = new AppContext();
        }
        return instance;
    }

    public boolean isWindows() {
        return windows;
    }

    public void setWindows(boolean windows) {
        this.windows = windows;
    }

    public IOStream getIOStream() {
        return ioStream;
    }

    public void setIOStream(IOStream ioStream) {
        this.ioStream = ioStream;
    }

    public DisplayProvider getDisplayProvider() {
        return displayProvider;
    }

    public void setDisplayProvider(DisplayProvider displayProvider) {
        this.displayProvider = displayProvider;
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }
}
