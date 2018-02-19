package com.spacebar.alienwars.screen;

import java.util.Optional;
import java.util.Stack;

public class DefaultDisplayExplorer implements DisplayExplorer {

    private final Stack<DisplayType> explorerStack = new Stack<>();
    private Displayable current;


    @Override
    public void previous(Screen screen) {
        if (!explorerStack.empty()) {
            DisplayType displayType = explorerStack.pop();
            Optional<Displayable> displayableOptional = getDisplay(screen, displayType);
            if (displayableOptional.isPresent()) {
                Displayable displayable = displayableOptional.get();
                current = displayable;
                clearScreen(screen);
                displayable.display(screen);

            }
        }
    }

    @Override
    public void next(Screen screen, DisplayType displayType) {
        Optional<Displayable> displayableOptional = getDisplay(screen, displayType);
        if (displayableOptional.isPresent()) {
            Displayable displayable = displayableOptional.get();
            explorerStack.push(displayType);
            current = displayable;
            clearScreen(screen);
            displayable.display(screen);

        }
    }

    @Override
    public Displayable current() {
        return current;
    }

    private Optional<Displayable> getDisplay(Screen screen, DisplayType displayType) {
        Displayable display = null;
        if (screen != null) {
            DisplayProvider displayProvider = screen.getDisplayProvider();
            if (displayProvider != null) {
                display = displayProvider.getDisplay(displayType);
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
