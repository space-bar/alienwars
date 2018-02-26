package com.spacebar.alienwars.display;

import com.spacebar.alienwars.screen.Screen;

public interface DisplayExplorer {

    void previous(Screen screen);

    void next(Screen screen, DisplayType displayType);

    void display(Screen screen, DisplayType displayType);

    Display current();

    boolean history(DisplayType displayType);
}
