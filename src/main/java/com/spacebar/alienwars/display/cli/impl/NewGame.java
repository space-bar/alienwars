package com.spacebar.alienwars.display.cli.impl;

import com.spacebar.alienwars.display.DisplayType;
import com.spacebar.alienwars.display.cli.AbstractCLIDisplay;
import com.spacebar.alienwars.game.Game;
import com.spacebar.alienwars.screen.Screen;
import com.spacebar.alienwars.util.GameUtils;

import java.util.InputMismatchException;

public class NewGame extends AbstractCLIDisplay {

    public static final String HEADER = "" +
            "_______                    ________                       \n" +
            " \\      \\   ______  _  __  /  _____/_____    _____   ____  \n" +
            " /   |   \\_/ __ \\ \\/ \\/ / /   \\  ___\\__  \\  /     \\_/ __ \\ \n" +
            "/    |    \\  ___/\\     /  \\    \\_\\  \\/ __ \\|  Y Y  \\  ___/ \n" +
            "\\____|__  /\\___  >\\/\\_/    \\______  (____  /__|_|  /\\___  >\n" +
            "        \\/     \\/                 \\/     \\/      \\/     \\/";

    public NewGame() {
        super(DisplayType.NEW_GAME);
    }

    @Override
    public void display(Screen screen) {
        drawHeader(screen, HEADER.split(NEW_LINE));
        drawBody(screen,
                "Hmmmm so you wanna give it a spin. Lets dance",
                "Enter a name, not lesser than 3 characters.", WHITE_SPACE,
                "[Just type EXIT to quit Or HOME to goto the Start Menu]", WHITE_SPACE);

        drawFooter(screen, APP_LOGO.split(NEW_LINE));

        readInput(screen);
    }

    int ex = 0;

    private void readInput(Screen screen) {
        screen.getIOStream().writeLine("Enter Name :");
        this.readInput(screen, (String input) -> {
            input = trimValue(input);

            if (input.length() > 2) {
                Game game = GameUtils.createGame(screen, input);
                screen.setGame(game);
                screen.getDisplayExplorer().next(screen, DisplayType.SELECT_SPACE_SHIP);
            } else {
                screen.getIOStream().writeLine(ex > 0 ?
                        "Ok that does it do this " + ex + " more time(s), and am shutting you down."
                        : "Opps! my grandma can come up with a better name");
                ex++;
                throw new InputMismatchException();
            }
        });
    }
}
