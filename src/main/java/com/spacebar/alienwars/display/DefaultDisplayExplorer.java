package com.spacebar.alienwars.display;

import com.spacebar.alienwars.io.IOStream;
import com.spacebar.alienwars.screen.Screen;

import java.util.Optional;
import java.util.Stack;

public class DefaultDisplayExplorer implements DisplayExplorer {

    private final Stack<DisplayType> explorerStack = new Stack<>();
    private Display current;


    @Override
    public void previous(Screen screen) {
        if (!explorerStack.empty()) {
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
            if(current!=null) {
                explorerStack.push(current.getDisplayType());
            }
            current = display;
            clearScreen(screen);
            display.display(screen);

        }
    }

    @Override
    public Display current() {
        return current;
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
