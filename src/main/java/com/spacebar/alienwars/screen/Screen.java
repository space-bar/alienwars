package com.spacebar.alienwars.screen;


import com.spacebar.alienwars.game.Game;

public interface Screen {

    int getWidth();

    int getHeight();

    boolean isWindows();

    IOStream getIOStream();

    DisplayProvider getDisplayProvider();

    DisplayExplorer getDisplayExplorer();

    Game getGame();
}
