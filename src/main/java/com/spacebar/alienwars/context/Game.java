package com.spacebar.alienwars.context;

public class Game {

    private boolean windows;

    public Game(boolean windows) {
        this.windows = windows;
    }

    public boolean isWindows() {
        return windows;
    }

    private static Game instance = null;
}
