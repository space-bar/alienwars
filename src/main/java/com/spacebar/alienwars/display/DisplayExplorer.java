package com.spacebar.alienwars.display;

import com.spacebar.alienwars.screen.Screen;

public interface DisplayExplorer {

    boolean previous(Screen screen);

    boolean next(Screen screen, DisplayType displayType);

    boolean display(Screen screen, DisplayType displayType);

    Display current();

    DisplayType[] history();
}
