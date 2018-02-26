package com.spacebar.alienwars.display;

import com.spacebar.alienwars.io.IOStream;
import com.spacebar.alienwars.screen.Screen;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedDeque;

public class DefaultDisplayExplorer implements DisplayExplorer {

    private final Deque<DisplayType> explorerStack = new ArrayDeque<>();
    private Display current;

    @Override
    public void previous(Screen screen) {
        if (!explorerStack.isEmpty()) {
            DisplayType displayType = explorerStack.pop();
            Optional<Display> displayableOptional = getDisplay(screen, displayType);
            if (displayableOptional.isPresent()) {
                Display display = displayableOptional.get();
                current = display;
                clearScreen(screen);
                display.display(screen);

            }
        }
    }

    @Override
    public void next(Screen screen, DisplayType displayType) {
        Optional<Display> displayableOptional = getDisplay(screen, displayType);
        if (displayableOptional.isPresent()) {
            Display display = displayableOptional.get();
            if (current != null) {
                explorerStack.push(current.getDisplayType());
            }
            current = display;
            clearScreen(screen);
            display.display(screen);

        }
    }

    @Override
    public void display(Screen screen, DisplayType displayType) {
        Optional<Display> displayableOptional = getDisplay(screen, displayType);
        if (displayableOptional.isPresent()) {
            Display display = displayableOptional.get();
            current = display;
            clearScreen(screen);
            display.display(screen);
        }
    }

    @Override
    public Display current() {
        return current;
    }


    public boolean history(DisplayType displayType) {
       return explorerStack.contains(displayType);
    }

    private Optional<Display> getDisplay(Screen screen, DisplayType displayType) {
        Display display = null;
        if (screen != null) {
            DisplayFactory displayFactory = screen.getDisplayFactory();
            if (displayFactory != null) {
                display = displayFactory.getDisplay(displayType);
            }
        }
        return display != null ? Optional.of(display) : Optional.empty();
    }

    private void clearScreen(Screen screen) {
        if (screen != null) {
            IOStream ioStream = screen.getIOStream();
            if (ioStream != null) {
                ioStream.clear();
            }
        }
    }
}
