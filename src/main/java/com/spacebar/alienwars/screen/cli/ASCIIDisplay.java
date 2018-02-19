package com.spacebar.alienwars.screen.cli;

import com.spacebar.alienwars.screen.Screen;

import java.io.Console;
import java.util.Arrays;
import java.util.stream.IntStream;

public class ASCIIDisplay {

    public static final String APP_BANNER = "" +
            "   _____   .__   .__                   __      __                       \n" +
            "  /  _  \\  |  |  |__|  ____    ____   /  \\    /  \\_____  _______  ______\n" +
            " /  /_\\  \\ |  |  |  |_/ __ \\  /    \\  \\   \\/\\/   /\\__  \\ \\_  __ \\/  ___/\n" +
            "/    |    \\|  |__|  |\\  ___/ |   |  \\  \\        /  / __ \\_|  | \\/\\___ \\ \n" +
            "\\____|__  /|____/|__| \\___  >|___|  /   \\__/\\  /  (____  /|__|  /____  >\n" +
            "        \\/                \\/      \\/         \\/        \\/            \\/ ";

    public static final String APP_LOGO = "" +
            " +-++-++-++-++-+ +-++-++-++-+\n" +
            " |A||l||i||e||n| |W||a||r||s|\n" +
            " +-++-++-++-++-+ +-++-++-++-+";


    /*public void display(Screen screen, String... contents) {
        //screen.getHeight()
        StringBuilder horizontalBorder = new StringBuilder();
        IntStream.range(0, screen.getHeight()).forEach((col) -> {
            String content = null;
            if (col == 0) {
                IntStream.range(0, screen.getWidth()).forEach((row) -> {
                    horizontalBorder.append("=");
                });
                content = horizontalBorder.toString();

            } else if (col == screen.getHeight() - 1) {
                content = horizontalBorder.toString();
            } else {
                IntStream.range(0, screen.getWidth()).forEach((row) -> {
                    screen.getIOStream().writeLine("=");
                });
            }
        });

        Arrays.stream(contents).forEach((content) -> {
            screen.getIOStream().writeLine(content);
        });
      //  content.length
    }
*/
    public void drawx(Screen screen, String... contents) {
        IntStream.range(0, screen.getHeight()).forEach((col) -> {
            IntStream.range(0, screen.getWidth()).forEach((row) -> {
                if (row == 0) {
                    screen.getIOStream().write("=");
                } else if (col == 0) {
                    screen.getIOStream().write("|");
                }

            });
        });
    }

    public void draw(Screen screen, String... contents) {

        int rows = screen.getHeight();
        int cols = screen.getWidth();
        IntStream.range(0, rows).forEach((row) -> {

            for (int col = 0; col < cols; col++) {
                if (row == 0) {
                    col = drawInlineContent(screen, col, "+", "=");
                } else if (row == rows - 1) {
                    col = drawInlineContent(screen, col, "+", "=");
                } else {
                    col = drawInlineContent(screen, col, " ", " ");

                }
            }

        });
    }

    private int drawInlineContent(Screen screen, int colIndex, String border, String content) {
        int cols = screen.getWidth();
        String value;
        if (colIndex == 0) {
            value = border;
        } else if (colIndex == cols - 1) {
            value = border;
        } else {
            value = content;
        }
        screen.getIOStream().write(content);
        int index = (value != null && !value.isEmpty() ? value.length() - 1 : 0) + colIndex;
        if (index >= cols || colIndex == cols - 1) {
            screen.getIOStream().writeLine("");
        }
        return index;
    }
}
