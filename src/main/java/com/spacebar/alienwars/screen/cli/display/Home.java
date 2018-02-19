package com.spacebar.alienwars.screen.cli.display;

import com.spacebar.alienwars.context.AppContext;
import com.spacebar.alienwars.screen.DisplayType;
import com.spacebar.alienwars.screen.Displayable;
import com.spacebar.alienwars.screen.IOStream;
import com.spacebar.alienwars.screen.Screen;

import java.util.InputMismatchException;

public class Home implements Displayable {

    public static final String welcome = "" +
            " ,--.   ,--.         ,--.                                  \n" +
            " |  |   |  |  ,---.  |  |  ,---.  ,---.  ,--,--,--.  ,---. \n" +
            " |  |.'.|  | | .-. : |  | | .--' | .-. | |        | | .-. :\n" +
            " |   ,'.   | \\   --. |  | \\ `--. ' '-' ' |  |  |  | \\   --.\n" +
            " '--'   '--'  `----' `--'  `---'  `---'  `--`--`--'  `----'";

    public void display(Screen screen) {
        IOStream r = screen.getIOStream();
        r.writeLine(welcome);
        r.writeLine("===========================================================\n");

        r.writeLine("|       What do you want to do ?                           |");
        r.writeLine("|       Type a number and hit enter.                       |");
        r.writeLine("|                                                          |");
        r.writeLine("|       1. Start New Game                                  |");
        r.writeLine("|       2. Load Saved Game                                 |");
        r.writeLine("|       3. Help                                            |");
        r.writeLine("|       4. About                                           |");
        r.writeLine("|       5. Exit                                            |");

        r.writeLine("\n===========================================================\n");

        r.write("Enter Menu Number :");

        readInput(screen);
    }

    private void readInput(Screen screen) {

        IOStream r = screen.getIOStream();

        try {
            int no = r.readInt();
            switch (no) {
                case 1:
                    screen.getDisplayExplorer().next(screen, DisplayType.START_GAME);
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    throw new InputMismatchException();
            }
        } catch (InputMismatchException ime) {
            readInput(screen);
        }
    }
}
