package com.spacebar.alienwars;

import com.spacebar.alienwars.context.AppContext;
import com.spacebar.alienwars.screen.DisplayType;
import com.spacebar.alienwars.screen.Displayable;
import com.spacebar.alienwars.screen.Screen;
import com.spacebar.alienwars.screen.cli.CLIScreen;

public class AlienWars {

    public static void main(String[] args) {
        Screen screen = new CLIScreen(100, 100);
        screen.getDisplayExplorer().next(screen, DisplayType.HOME);
    }

    public static void mainx(String[] args) {
        System.out.println(" " +
                " +-++-++-++-++-+ +-++-++-++-+\n" +
                " |A||l||i||e||n| |W||a||r||s|\n" +
                " +-++-++-++-++-+ +-++-++-++-+");

        System.out.println(
                "                                                                      \n" +
                        "  ,---.  ,--.,--.                   ,--.   ,--.                       \n" +
                        " /  O  \\ |  |`--' ,---. ,--,--,     |  |   |  | ,--,--.,--.--. ,---.  \n" +
                        "|  .-.  ||  |,--.| .-. :|      \\    |  |.'.|  |' ,-.  ||  .--'(  .-'  \n" +
                        "|  | |  ||  ||  |\\   --.|  ||  |    |   ,'.   |\\ '-'  ||  |   .-'  `) \n" +
                        "`--' `--'`--'`--' `----'`--''--'    '--'   '--' `--`--'`--'   `----'  \n" +
                        "                                                                      ");
        System.out.println("" +
                "     ___       __       __   _______ .__   __.    ____    __    ____  ___      .______           _______.\n" +
                "    /   \\     |  |     |  | |   ____||  \\ |  |    \\   \\  /  \\  /   / /   \\     |   _  \\         /       |\n" +
                "   /  ^  \\    |  |     |  | |  |__   |   \\|  |     \\   \\/    \\/   / /  ^  \\    |  |_)  |       |   (----`\n" +
                "  /  /_\\  \\   |  |     |  | |   __|  |  . `  |      \\            / /  /_\\  \\   |      /         \\   \\    \n" +
                " /  _____  \\  |  `----.|  | |  |____ |  |\\   |       \\    /\\    / /  _____  \\  |  |\\  \\----..----)   |   \n" +
                "/__/     \\__\\ |_______||__| |_______||__| \\__|        \\__/  \\__/ /__/     \\__\\ | _| `._____||_______/    \n" +
                "                                                                                                         "
        );
    }
}
