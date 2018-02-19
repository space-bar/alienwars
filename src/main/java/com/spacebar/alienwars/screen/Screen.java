package com.spacebar.alienwars.screen;

import java.awt.*;

public class Screen {

    private final int width;
    private final int height;
    private IOStream ioStream;

    public Screen(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Screen(int width, int height, IOStream ioStream) {
        this.width = width;
        this.height = height;
        this.ioStream = ioStream;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public IOStream getIOStream() {
        return ioStream;
    }

    public void setIOStream(IOStream ioStream) {
        this.ioStream = ioStream;
    }

}
