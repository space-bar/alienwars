package com.spacebar.alienwars.screen;

public interface DisplayExplorer {

    void previous(Screen screen);

    void next(Screen screen, DisplayType displayType);

    Displayable current();
}
