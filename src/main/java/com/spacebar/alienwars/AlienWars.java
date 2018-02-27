package com.spacebar.alienwars;

import com.spacebar.alienwars.display.DisplayType;
import com.spacebar.alienwars.screen.Screen;
import com.spacebar.alienwars.screen.cli.CLIScreen;

public class AlienWars {

    public static void main(String[] args) {
        Screen screen = new CLIScreen(100, 20);
        screen.getDisplayExplorer().next(screen, DisplayType.HOME);
    }
}
