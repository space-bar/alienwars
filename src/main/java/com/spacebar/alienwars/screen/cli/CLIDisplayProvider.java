package com.spacebar.alienwars.screen.cli;

import com.spacebar.alienwars.screen.DisplayProvider;
import com.spacebar.alienwars.screen.DisplayType;
import com.spacebar.alienwars.screen.Displayable;
import com.spacebar.alienwars.screen.cli.display.Welcome;

public class CLIDisplayProvider implements DisplayProvider {
    private Displayable welcomeScreen;

   /* public CLIDisplayProvider(Screen screen) {
        if (screen == null)
            throw new IllegalArgumentException("Screen cannot be null");
        this.screen = screen;
    }*/


    public Displayable getDisplay(DisplayType displayType) {
        if (displayType != null) {

            switch (displayType) {

                case WELCOME:
                    return getWelcomeDisplay();

                case HELP:
                    return getWelcomeDisplay();

            }
        }
        return null;
    }

    private Displayable getWelcomeDisplay() {
        if (welcomeScreen == null)
            welcomeScreen = new Welcome();
        return welcomeScreen;
    }

}
