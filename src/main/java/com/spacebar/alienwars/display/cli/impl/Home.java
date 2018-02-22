package com.spacebar.alienwars.display.cli.impl;

import com.spacebar.alienwars.display.DisplayType;
import com.spacebar.alienwars.io.IOStream;
import com.spacebar.alienwars.screen.Screen;
import com.spacebar.alienwars.display.cli.AbstractCLIDisplay;


import java.util.InputMismatchException;

public class Home extends AbstractCLIDisplay {

    public static final String header = "" +
            ",--.   ,--.         ,--.                                  \n" +
            "|  |   |  |  ,---.  |  |  ,---.  ,---.  ,--,--,--.  ,---. \n" +
            "|  |.'.|  | | .-. : |  | | .--' | .-. | |        | | .-. :\n" +
            "|   ,'.   | \\   --. |  | \\ `--. ' '-' ' |  |  |  | \\   --.\n" +
            "'--'   '--'  `----' `--'  `---'  `---'  `--`--`--'  `----'";

    public Home() {
        super(DisplayType.HOME);
    }

    public void display(Screen screen) {
        IOStream r = screen.getIOStream();
        drawHeader(screen, header.split("\\n"));
        drawBody(screen,
                "What do you want to do ?",
                "Type a number and hit enter.",
                " ",
                "1. Start New Game",
                "2. Load Saved Game",
                "3. Help",
                "4. About",
                "5. Exit");
        drawFooter(screen, APP_LOGO.split("\\n"));
        r.writeLine("Enter Menu Number :");

        readInput(screen);

        // r.writeLine(welcome);
        // r.writeLine("===========================================================\n");
      /*  r.writeLine("|       What do you want to do ?                           |");
        r.writeLine("|       Type a number and hit enter.                       |");
        r.writeLine("|                                                          |");
        r.writeLine("|       1. Start New Game                                  |");
        r.writeLine("|       2. Load Saved Game                                 |");
        r.writeLine("|       3. Help                                            |");
        r.writeLine("|       4. About                                           |");
        r.writeLine("|       5. Exit                                            |");*/

       /* r.writeLine("\n===========================================================\n");
        r.write("Enter Menu Number :");*/
    }

    private void readInput(Screen screen) {
        this.readIntInput(screen, (no) -> {
            switch (no) {
                case 1:
                    screen.getDisplayExplorer().next(screen, DisplayType.NEW_GAME);
                    break;
                case 2:
                    screen.getDisplayExplorer().next(screen, DisplayType.LOAD_SAVED_GAME);
                    break;
                case 3:
                    screen.getDisplayExplorer().next(screen, DisplayType.PLAY_GAME);
                    break;
                case 4:
                    screen.getDisplayExplorer().next(screen, DisplayType.ABOUT);
                    break;
                case 5:
                    screen.getDisplayExplorer().next(screen, DisplayType.EXIT);
                    break;
                default:
                    throw new InputMismatchException();
            }
        });
    }
}
