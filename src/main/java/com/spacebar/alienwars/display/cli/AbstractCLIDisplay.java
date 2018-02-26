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

    public static final String RABBIT = "" +
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

    public static final String CMD_EXIT = "exit";

    public static final String CMD_BACK = "back";

    public static final String CMD_HOME = "home";

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
        readInput(screen, fnx, false);
    }

    protected <T> void readInput(Screen screen, Consumer<T> fnx, boolean skipDefaultCommand) {
        IOStream r = screen.getIOStream();
        try {
            T input = (T) r.read();
            if (!skipDefaultCommand) {
                performDefaultCommand(screen, input, fnx);
            } else if (fnx != null) {
                fnx.accept(input);
            }
        } catch (InputMismatchException ime) {
            screen.getIOStream().writeLine(ERROR_FACE);
            readInput(screen, fnx);
        }
    }

    protected <T> void performDefaultCommand(Screen screen, T input, Consumer<T> fnx) {
        String value = trimValue(input != null ? input.toString() : null);
        if (CMD_EXIT.equalsIgnoreCase(value)) {
            screen.getDisplayExplorer().display(screen, DisplayType.EXIT);

        } else if (CMD_BACK.equalsIgnoreCase(value)) {
            screen.getDisplayExplorer().previous(screen);
        } else if (CMD_HOME.equalsIgnoreCase(value)) {
            Display display = screen.getDisplayExplorer().current();
            if (display == null || (!DisplayType.HOME.equals(display.getDisplayType()) && !DisplayType.HOME.equals(displayType)))
                screen.getDisplayExplorer().display(screen, DisplayType.HOME);
        } else if (fnx != null) {
            fnx.accept(input);
        }
    }

    protected Integer convertToInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ime) {
            return null;
        }
    }

    protected String trimValue(String value) {
        return value == null ? "" : value.trim();
    }

    protected void drawHeader(Screen screen, String... headers) {
        int width = screen.getWidth();
        int maxHeaderWidth = computeMaxWidth(headers);
        width = Math.max(width, maxHeaderWidth);

        String lines = contents(width - 2, '=');
        String padding = contents((width - maxHeaderWidth) / 2, ' ');

        Arrays.stream(headers).forEach(header -> {
            if (header == null) {
                header = WHITE_SPACE;
            }
            screen.getIOStream().writeLine(padding + header);
        });
        screen.getIOStream().writeLine('+' + lines + '+');
    }

    protected void drawBody(Screen screen, String... body) {
        drawBody(screen, true, body);
    }

    protected void drawBody(Screen screen, boolean bordered, String... body) {
        int screenWidth = screen.getWidth();
        int maxBodyWidth = computeMaxWidth(body);
        int width = Math.max(screenWidth, maxBodyWidth);
        String padding = contents((width - maxBodyWidth) / 2, ' ');
        String border = bordered ? "|" : WHITE_SPACE;
        Arrays.stream(body).forEach(b -> {
            if (b == null) {
                b = WHITE_SPACE;
            }
            int rightPaddingLength = (width - (padding.length() + b.length())) - 2;
            String rightPadding = contents(rightPaddingLength, ' ');

            screen.getIOStream().writeLine(border + padding + b + rightPadding + border);
        });
    }

    protected void drawFooter(Screen screen, String... footers) {
        int width = screen.getWidth();
        int maxFooterWidth = computeMaxWidth(footers);
        width = Math.max(width, maxFooterWidth);

        String lines = contents(width - 2, '=');
        String padding = contents((width - maxFooterWidth) / 2, ' ');

        screen.getIOStream().writeLine('+' + lines + '+');
        Arrays.stream(footers).forEach(footer -> {
            if (footer == null) {
                footer = WHITE_SPACE;
            }
            screen.getIOStream().writeLine(padding + footer);
        });
    }


    protected void drawTagGroup(Screen screen, int colSize, String... tags) {
        int width = screen.getWidth();
        int w = (width / (colSize * 2)) - colSize;
        int computedWidth = w * 2;
        String lines = contents(w, "+-");
        String padding = contents(4, WHITE_SPACE);

        int size = (tags.length < colSize ? colSize : tags.length);
        int cols = (size / colSize) + (size % colSize > 0 ? 1 : 0);
        IOStream io = screen.getIOStream();
        for (int col = 0; col < cols; col++) {

            int f = (colSize * col);
            int l = colSize * (col + 1);
            if (col == 0) {
                tagGroupHeader(f, l, io, padding, lines, tags);
            }

            tagGroupBody(f, l, io, padding, computedWidth, tags);

            tagGroupFooter(f, l, io, padding, lines, tags);

        }
    }

    private void tagGroupHeader(int f, int l, IOStream io, String padding, String lines, String[] tags) {
        for (int x = f; x < l && x < tags.length; x++) {
            boolean first = x == f || x == 0;
            boolean last = x + 1 >= l || x + 1 >= tags.length;
            io.write((first ? "" : padding) + lines + '+' + (last ? NEW_LINE : ""));

        }
    }

    private void tagGroupBody(int f, int l, IOStream io, String padding, int computedWidth, String[] tags) {
        for (int x = f; x < l && x < tags.length; x++) {
            boolean first = x == f || x == 0;
            boolean last = x + 1 >= l || x + 1 >= tags.length;
            String tag = tags[x] == null ? "" : tags[x];
            if (tag.length() > computedWidth) {
                tag = tag.substring(0, computedWidth - 4) + "...";
            }

            String rightPadding = contents(computedWidth - tag.length() - 1, ' ');
            io.write((first ? "" : padding) + "|" + tag + rightPadding + '|' + (last ? NEW_LINE : ""));
        }
    }

    private void tagGroupFooter(int f, int l, IOStream io, String padding, String lines, String[] tags) {
        for (int x = f; x < l && x < tags.length; x++) {
            boolean first = x == f || x == 0;
            boolean last = x + 1 >= l || x + 1 >= tags.length;
            io.write((first ? "" : padding) + lines + '+' + (last ? NEW_LINE : ""));
        }
    }


    protected String contents(int length, char pixel) {
        if (length < 1) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        IntStream.range(0, length).forEach(i ->
                sb.append(pixel)
        );
        return sb.toString();
    }

    protected String contents(int length, String content) {
        if (length < 1) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        IntStream.range(0, length).forEach(i ->
                sb.append(content)
        );
        return sb.toString();
    }

    protected int computeMaxWidth(String... values) {
        return Arrays.stream(values)
                .mapToInt(value -> value != null ? value.length() : 0)
                .max().orElse(1);
    }
}
