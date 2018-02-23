package com.spacebar.alienwars.display.cli;

import com.spacebar.alienwars.display.DisplayType;
import com.spacebar.alienwars.display.Display;
import com.spacebar.alienwars.io.IOStream;
import com.spacebar.alienwars.screen.Screen;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.function.Consumer;
import java.util.stream.IntStream;

public abstract class AbstractCLIDisplay implements Display {

    public static final String NEW_LINE = "\n";

    public static final String WHITE_SPACE = " ";

    public static final String ERROR_RABBIT = "" +
            "(\\_/)\n" +
            "\n" +
            "( •,•)\n" +
            "\n" +
            "(\")_(\")";

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

    private final DisplayType displayType;

    public AbstractCLIDisplay(DisplayType displayType) {
        if (displayType == null) {
            throw new IllegalArgumentException();
        }
        this.displayType = displayType;
    }

    public DisplayType getDisplayType() {
        return displayType;
    }

    protected <T> void readInput(Screen screen, Consumer<T> fnx) {
        IOStream r = screen.getIOStream();
        try {
            T input = (T) r.read();
            fnx.accept(input);
        } catch (InputMismatchException ime) {
            screen.getIOStream().writeLine(ERROR_RABBIT);
            readInput(screen, fnx);
        }
    }

    protected void readIntInput(Screen screen, Consumer<Integer> fnx) {
        IOStream r = screen.getIOStream();
        try {
            int input = r.readInt();
            fnx.accept(input);
        } catch (InputMismatchException ime) {
            screen.getIOStream().writeLine(ERROR_RABBIT);
            readIntInput(screen, fnx);
        }
    }

    protected Integer convertToInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ime) {
        }
        return null;
    }

    protected void drawHeader(Screen screen, String... headers) {
        int width = screen.getWidth();
        int maxHeaderWidth = computeMaxWidth(headers);
        width = Math.max(width, maxHeaderWidth);

        String lines = contents(width - 2, '=');
        String padding = contents((width - maxHeaderWidth) / 2, ' ');

        Arrays.stream(headers).forEach((header) -> {
            screen.getIOStream().writeLine(padding + header);
        });
        screen.getIOStream().writeLine('+' + lines + '+');
    }

    protected void drawBody(Screen screen, String... body) {
        int _width = screen.getWidth();
        int maxBodyWidth = computeMaxWidth(body);
        int width = Math.max(_width, maxBodyWidth);
        String padding = contents((width - maxBodyWidth) / 2, ' ');
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

        String lines = contents(width - 2, '=');
        String padding = contents((width - maxHeaderWidth) / 2, ' ');

        screen.getIOStream().writeLine('+' + lines + '+');
        Arrays.stream(footers).forEach((footer) -> {
            screen.getIOStream().writeLine(padding + footer);
        });
    }

    protected String contents(int length, char pixel) {
        StringBuilder sb = new StringBuilder();
        IntStream.range(0, length).forEach(i -> {
            sb.append(pixel);
        });
        return sb.toString();
    }

    protected String contents(int length, String content) {
        StringBuilder sb = new StringBuilder();
        IntStream.range(0, length).forEach(i -> {
            sb.append(content);
        });
        return sb.toString();
    }

    protected int computeMaxWidth(String... values) {
        return Arrays.stream(values)
                .mapToInt((value) -> value.length())
                .max().orElse(1);
    }
}
