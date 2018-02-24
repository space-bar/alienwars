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

    public static final String ERROR_FACE = "( •,•)";


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
            screen.getIOStream().writeLine(ERROR_FACE);
            readInput(screen, fnx);
        }
    }

    protected void readIntInput(Screen screen, Consumer<Integer> fnx) {
        IOStream r = screen.getIOStream();
        try {
            int input = r.readInt();
            fnx.accept(input);
        } catch (InputMismatchException ime) {
            screen.getIOStream().writeLine(ERROR_FACE);
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
            if (header == null) {
                header = WHITE_SPACE;
            }
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
            if (b == null) {
                b = WHITE_SPACE;
            }
            int rightPaddingLength = (width - (padding.length() + b.length())) - 2;
            String rightPadding = contents(rightPaddingLength, ' ');
            screen.getIOStream().writeLine("|" + padding + b + rightPadding + "|");
        });
    }

    protected void drawFooter(Screen screen, String... footers) {
        int width = screen.getWidth();
        int maxFooterWidth = computeMaxWidth(footers);
        width = Math.max(width, maxFooterWidth);

        String lines = contents(width - 2, '=');
        String padding = contents((width - maxFooterWidth) / 2, ' ');

        screen.getIOStream().writeLine('+' + lines + '+');
        Arrays.stream(footers).forEach((footer) -> {
            if (footer == null) {
                footer = WHITE_SPACE;
            }
            screen.getIOStream().writeLine(padding + footer);
        });
    }

    protected void drawTag(Screen screen, String... tags) {
        int width = screen.getWidth();
        //int maxTagWidth = computeMaxWidth(tags);
        //width = Math.max(width / 2, maxTagWidth);
        int _width = (width / 2) - 4;
        String lines = contents(_width, "+-");
       // screen.getIOStream().writeLine(lines + '+');


        String padding = contents(4, WHITE_SPACE);


        IntStream.range(0, tags.length).forEach(index -> {
            String tag = tags[index];
            if (tag == null) {
                tag = WHITE_SPACE;
            }

            if (index < 2) {
                screen.getIOStream().writeLine(lines + '+');
            }
            /*if(tag.length()>= _width){
                index
            }*/

            String rightPadding = contents(_width - tag.length(), ' ');
            if (index % 2 == 2) {
                screen.getIOStream().write("|" + tag + rightPadding + '|' + padding);
            } else {
                screen.getIOStream().writeLine("|" + tag + rightPadding + '|');
            }
            screen.getIOStream().writeLine(lines + '+');
        });

    }

    protected void drawTag(Screen screen, String content, boolean child) {
        drawTag(screen, content, child, content.length());
    }

    protected void drawTag(Screen screen, String content, boolean child, int length) {
        int width = length < content.length() + 2 ? content.length() + 2 : length;
        String lines = contents(width / 2, "+-");
        int x = width - content.length() - 1;
        String padding = x > 0 ? contents(width, "+-") : "";

        if (!child) {
            screen.getIOStream().writeLine(lines + '+');
            screen.getIOStream().writeLine(lines + '+');
        }
        screen.getIOStream().writeLine("|" + content + padding + '|');
        screen.getIOStream().writeLine(lines + '+');
    }

    protected void appendTag(Screen screen, String content, int length) {
        int width = ((length < content.length() ? content.length() + 2 : length) / 2) - 1;
        String lines = contents(width, "+-");
        int x = width - content.length() - 2;
        String padding = x > 0 ? contents(width, "+-") : "";

        screen.getIOStream().writeLine(lines + '+');
        screen.getIOStream().writeLine("|" + content + padding + '|');
        screen.getIOStream().writeLine(lines + '+');
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
                .mapToInt(value -> value != null ? value.length() : 0)
                .max().orElse(1);
    }
}
