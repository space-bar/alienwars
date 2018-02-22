package com.spacebar.alienwars.display;

public interface DisplayFactory {

    Displayable getDisplay(DisplayType displayType);
}
