package com.spacebar.alienwars.display;

import com.spacebar.alienwars.screen.Screen;

public interface Displayable {

    void display(Screen screen);

    DisplayType getDisplayType();
}
