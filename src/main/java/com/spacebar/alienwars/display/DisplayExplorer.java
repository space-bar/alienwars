package com.spacebar.alienwars.display;

import com.spacebar.alienwars.screen.Screen;

public interface DisplayExplorer {

    void previous(Screen screen);

    void next(Screen screen, DisplayType displayType);

    Displayable current();
}
