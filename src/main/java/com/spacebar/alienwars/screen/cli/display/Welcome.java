package com.spacebar.alienwars.screen.cli.display;

import com.spacebar.alienwars.screen.Displayable;
import com.spacebar.alienwars.screen.IOStream;
import com.spacebar.alienwars.screen.Screen;



public class Welcome implements Displayable {

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
        r.writeLine("|       1. Start Game                                      |");
        r.writeLine("|       2. Help                                            |");
        r.writeLine("|       3. Exit                                            |");

        r.writeLine("\n===========================================================\n");

        r.write("Enter Menu Number :");

        r.read();

    }

}
