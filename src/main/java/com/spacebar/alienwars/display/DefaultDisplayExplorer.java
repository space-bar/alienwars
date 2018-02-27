package com.spacebar.alienwars.display;

import com.spacebar.alienwars.io.IOStream;
import com.spacebar.alienwars.screen.Screen;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Optional;

public class DefaultDisplayExplorer implements DisplayExplorer {

    private final Deque<DisplayType> explorerStack = new ArrayDeque<>();
    private Display current;

    @Override
    public boolean previous(Screen screen) {
        if (!explorerStack.isEmpty()) {
            Optional<Display> displayableOptional = getDisplay(screen, explorerStack.peek());
            if (displayableOptional.isPresent()) {
                explorerStack.pop();
                return display(screen, displayableOptional);
            }
        }
        return false;
    }

    @Override
    public boolean next(Screen screen, DisplayType displayType) {
        Optional<Display> displayableOptional = getDisplay(screen, displayType);
        if (displayableOptional.isPresent()) {
            if (current != null) {
                explorerStack.push(current.getDisplayType());
            }
            return display(screen, displayableOptional);
        }
        return false;
    }

    @Override
    public boolean display(Screen screen, DisplayType displayType) {
        Optional<Display> displayableOptional = getDisplay(screen, displayType);
        return display(screen, displayableOptional);
    }

    @Override
    public Display current() {
        return current;
    }


    public DisplayType[] history() {
        return explorerStack.toArray(new DisplayType[explorerStack.size()]);
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

    private boolean display(Screen screen, Optional<Display> displayableOptional) {
        if (displayableOptional.isPresent()) {
            Display display = displayableOptional.get();
            current = display;
            clearScreen(screen);
            display.display(screen);
            return true;
        }
        return false;
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
