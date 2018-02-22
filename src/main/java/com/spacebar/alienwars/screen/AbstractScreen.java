package com.spacebar.alienwars.screen;


import com.spacebar.alienwars.display.DefaultDisplayExplorer;
import com.spacebar.alienwars.display.DisplayExplorer;
import com.spacebar.alienwars.spaceship.SpaceshipFactory;

public abstract class AbstractScreen implements Screen {

    private final int width;

    private final int height;

    private boolean windows;


    private DisplayExplorer displayExplorer;


    {
        String os = System.getProperty("os.name");
        windows = os != null && os.contains("Windows");
    }

    public AbstractScreen(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public boolean isWindows() {
        return windows;
    }


    @Override
    public DisplayExplorer getDisplayExplorer() {
        if (displayExplorer == null) {
            displayExplorer = new DefaultDisplayExplorer();
        }
        return displayExplorer;
    }


}
