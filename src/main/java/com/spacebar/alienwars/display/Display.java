package com.spacebar.alienwars.display;

import com.spacebar.alienwars.screen.Screen;

public interface Display {

    void display(Screen screen);

    DisplayType getDisplayType();
}
