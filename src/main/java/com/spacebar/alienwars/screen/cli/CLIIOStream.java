package com.spacebar.alienwars.screen.cli;

import com.spacebar.alienwars.screen.IOStream;
import com.sun.java.swing.plaf.windows.resources.windows;

import java.io.Console;
import java.io.IOException;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.IntStream;

public class CLIIOStream implements IOStream<String> {

    private final static Console console = System.console();

    private final static PrintStream out = System.out;

    private final Scanner in = console == null ? new Scanner(System.in) : null;

    private boolean windows;

    {
        String os = System.getProperty("os.name");
        windows = os != null && os.contains("Windows");
    }

    @Override

    public void write(String value) {
        if (console != null) {
            console.writer().print(value);
            console.flush();
        } else {
            out.print(value);
        }

    }

    @Override
    public void writeLine(String value) {
        if (console != null) {
            console.writer().println(value);
            console.flush();
        } else {
            out.println(value);
        }
    }

    @Override
    public void writeInt(int value) {
        if (console != null) {
            console.writer().print(value);
            console.flush();
        } else {
            out.print(value);
        }
    }


    //read input
    @Override
    public String read() {
        if (console != null) {
            return console.readLine();
        } else {
            return in.next();
        }
    }

    @Override
    public String readLine() {
        if (console != null) {
            return console.readLine();
        } else {
            return in.nextLine();
        }
    }

    @Override
    public int readInt() {
        if (console != null) {
            String value = console.readLine();
            try {
                return Integer.parseInt(value, 10);
            } catch (NumberFormatException nfe) {
                throw new InputMismatchException(nfe.getMessage());
            }
        } else {
            return in.nextInt();
        }
    }


    @Override
    public void clear() {
        if (windows) {
            clearWindows();
        } else {
            clearUnix();
        }
    }

    private void clearWindows() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            clearByBackspace();
        }
    }


    private void clearUnix() {
        try {
            if (console != null) {
                console.writer().print("\033[H\033[2J");
                console.flush();
            } else {
                out.println("\033[H\033[2J");
            }
        } catch (Exception e) {
            clearByBackspace();
        }
    }

    private void clearByBackspace() {
        IntStream.range(0, 80 * 300).forEach(i -> {
            if (console != null) {
                console.writer().print("\b");
                console.flush();
            } else {
                out.print("\b");
            }
        });
    }
}
