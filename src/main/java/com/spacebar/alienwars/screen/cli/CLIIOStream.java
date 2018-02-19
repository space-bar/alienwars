package com.spacebar.alienwars.screen.cli;

import com.spacebar.alienwars.screen.IOStream;

import java.io.Console;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CLIIOStream implements IOStream<String> {

    private final static Console console = System.console();

    private final static PrintStream out = System.out;

    private final Scanner in = console == null ? new Scanner(System.in) : null;

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
}
