package com.spacebar.alienwars.screen;


import com.spacebar.alienwars.display.DisplayExplorer;
import com.spacebar.alienwars.display.DisplayFactory;
import com.spacebar.alienwars.game.Game;
import com.spacebar.alienwars.io.IOStream;

public interface Screen {

    int getWidth();

    int getHeight();

    boolean isWindows();

    IOStream getIOStream();

    DisplayFactory getDisplayFactory();

    DisplayExplorer getDisplayExplorer();

    Game getGame();
}
