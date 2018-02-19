package com.spacebar.alienwars.screen.cli.display;

import com.spacebar.alienwars.screen.Displayable;
import com.spacebar.alienwars.screen.IOStream;
import com.spacebar.alienwars.screen.Screen;
import com.spacebar.alienwars.screen.cli.ASCIIDisplay;

public class StartGame implements Displayable {

    @Override
    public void display(Screen screen) {
        IOStream r = screen.getIOStream();
        r.writeLine(ASCIIDisplay.APP_LOGO);
        r.writeLine("===========================================================\n");

        r.writeLine("|       What do you want to do ?                           |");
        r.writeLine("|       Type a number and hit enter.                       |");
        r.writeLine("|                                                          |");
        r.writeLine("|       1. Start Game                                      |");
        r.writeLine("|       2. Help                                            |");
        r.writeLine("|       3. Exit                                            |");

        r.writeLine("\n===========================================================\n");

        r.write("Enter Menu Number :");

        //readInput(screen);
        //System.out.print("\033[31;1mHello\033[0m, \033[32;1;2mworld!\033[0m");
        //System.out.println("\033[31mRed\033[32m, Green\033[33m, Yellow\033[34m, Blue\033[0m");
    }
}
