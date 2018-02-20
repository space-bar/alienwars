package com.spacebar.alienwars.screen.cli;

import com.spacebar.alienwars.screen.DisplayType;
import com.spacebar.alienwars.screen.Displayable;
import com.spacebar.alienwars.screen.IOStream;
import com.spacebar.alienwars.screen.Screen;

import java.io.Console;
import java.util.Arrays;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.OptionalInt;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public abstract class AbstractCLIDisplay implements Displayable {

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


    protected <T> void readInput(Screen screen, Consumer<T> fnx) {
        IOStream r = screen.getIOStream();
        try {
            T no = (T) r.read();
            fnx.accept(no);
        } catch (InputMismatchException ime) {
            readInput(screen, fnx);
        }
    }

    protected void readIntInput(Screen screen, Consumer<Integer> fnx) {
        IOStream r = screen.getIOStream();
        try {
            int no = r.readInt();
            fnx.accept(no);
        } catch (InputMismatchException ime) {
            readIntInput(screen, fnx);
        }
    }

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

    protected void drawHeader(Screen screen, String... headers) {
        int width = screen.getWidth();
        int maxHeaderWidth = computeMaxWidth(headers);
        width = Math.max(width, maxHeaderWidth);

        String padding = contents((width - maxHeaderWidth) / 2, ' ');
        Arrays.stream(headers).forEach((header) -> {
            screen.getIOStream().writeLine(padding + header);
        });

        String lines = contents(width, '=', '+', '+');
        screen.getIOStream().write(lines);

    }

    protected void drawBody(Screen screen, String... body) {
        int _width = screen.getWidth();
        int maxHeaderWidth = computeMaxWidth(body);
        int width = Math.max(_width, maxHeaderWidth);
        String padding = contents((width - maxHeaderWidth) / 2, ' ');
        screen.getIOStream().writeLine("");
        Arrays.stream(body).forEach((b) -> {
            int rightPaddingLength = (width - (padding.length() + b.length())) - 2;
            String rightPadding = contents(rightPaddingLength, ' ');
            screen.getIOStream().writeLine("|" + padding + b + rightPadding + "|");
        });
    }

    protected void drawFooter(Screen screen, String... footers) {
        int width = screen.getWidth();
        int maxHeaderWidth = computeMaxWidth(footers);
        width = Math.max(width, maxHeaderWidth);

        String lines = contents(width, '=', '+', '+');
        screen.getIOStream().writeLine(lines);

        String padding = contents((width - maxHeaderWidth) / 2, ' ');

       /* String[] logo =APP_LOGO.split("\\n");
        String padding = contents((width - maxHeaderWidth) / 2, ' ');
        Arrays.stream(APP_LOGO.split("\\n")).forEach((footer) -> {
            screen.getIOStream().writeLine(padding + footer);
        });*/

        Arrays.stream(footers).forEach((footer) -> {
            screen.getIOStream().writeLine(padding + footer);
        });


    }

    private String contents(int length, char pixel) {
        StringBuilder sb = new StringBuilder();
        IntStream.range(0, length).forEach(i -> {
            sb.append(pixel);
        });
        return sb.toString();
    }

    private String contents(int length, char pixel, char prefix, char suffix) {
        StringBuilder sb = new StringBuilder();
        IntStream.range(0, length).forEach(i -> {
            if (i == 0) {
                sb.append(prefix);
            } else if (i == length - 1) {
                sb.append(suffix);
            } else {
                sb.append(pixel);
            }
        });
        return sb.toString();
    }

    private int computeMaxWidth(String... values) {
        return Arrays.stream(values)
                .mapToInt((value) -> value.length())
                .max().orElse(1);
    }
}
