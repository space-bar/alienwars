package com.spacebar.alienwars.screen;


public interface Screen {

    int getWidth();

    int getHeight();

    boolean isWindows();

    IOStream getIOStream();

    DisplayProvider getDisplayProvider();

    DisplayExplorer getDisplayExplorer();
}
