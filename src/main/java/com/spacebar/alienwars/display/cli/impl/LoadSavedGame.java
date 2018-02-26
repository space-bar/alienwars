package com.spacebar.alienwars.display.cli.impl;

import com.spacebar.alienwars.display.DisplayType;
import com.spacebar.alienwars.display.cli.AbstractCLIDisplay;
import com.spacebar.alienwars.game.Game;
import com.spacebar.alienwars.screen.Screen;
import com.spacebar.alienwars.util.FileUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LoadSavedGame extends AbstractCLIDisplay {

    public static final String HEADER = "" +
            ".____                     .___   ________                       \n" +
            "|    |    _________     __| _/  /  _____/_____    _____   ____  \n" +
            "|    |   /  _ \\__  \\   / __ |  /   \\  ___\\__  \\  /     \\_/ __ \\ \n" +
            "|    |__(  <_> ) __ \\_/ /_/ |  \\    \\_\\  \\/ __ \\|  Y Y  \\  ___/ \n" +
            "|_______ \\____(____  /\\____ |   \\______  (____  /__|_|  /\\___  >\n" +
            "        \\/         \\/      \\/          \\/     \\/      \\/     \\/";

    private Properties manifiest;
    private List<String> gameList;
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public LoadSavedGame() {
        super(DisplayType.LOAD_SAVED_GAME);
    }

    @Override
    public void display(Screen screen) {
        if (manifiest == null) {
            readManifiest();
        }
        String[] body = buildBody();
        drawHeader(screen, HEADER.split(NEW_LINE));

        drawBody(screen, WHITE_SPACE);
        if (body.length > 0) {
            drawBody(screen, body);
        } else {
            drawBody(screen, "Opps! Seems like there are no saved files",
                    "You can start a New Game", WHITE_SPACE,
                    "[HOME to goto the Start Menu]");
        }
        drawBody(screen, WHITE_SPACE);
        drawFooter(screen, APP_LOGO.split(NEW_LINE));

        readInput(screen);
    }

    private String[] buildBody() {
        List<String> gameDisplayList = new ArrayList<>();
        gameList = manifiest.stringPropertyNames().stream().filter(key -> {
            Object filename = manifiest.get(key);
            try {
                if (filename != null) {
                    String display = key + " [" + dateFormat.format(new Date(Long.parseLong(filename.toString()))) + "]";
                    gameDisplayList.add(display);
                    return true;
                }
            } catch (Exception ex) {
                return false;
            }
            return false;

        }).collect(Collectors.toList());

        String[] body = new String[gameDisplayList.size()];
        IntStream.range(0, gameDisplayList.size()).forEach(index ->
                body[index] = index + 1 + ". " + gameDisplayList.get(index)
        );
        return body;
    }

    private void readInput(Screen screen) {
        screen.getIOStream().writeLine(NEW_LINE + "Enter:");
        this.readInput(screen, (String input) -> {
            Integer index = convertToInt(input);
            if (index != null && gameList != null && index > 0 && index <= gameList.size()) {
                Game game = loadGame(gameList.get(index - 1));
                if (game != null) {
                    screen.getDisplayExplorer().next(screen, DisplayType.PLAY_GAME);
                } else {
                    screen.getDisplayExplorer().display(screen, DisplayType.HOME);
                }
            } else {
                screen.getIOStream().writeLine(NEW_LINE + "Enter:");
                throw new InputMismatchException();
            }
        });
    }


    private void readManifiest() {
        try {
            manifiest = FileUtils.readManifest();
        } catch (IOException e) {
            manifiest = new Properties();
        }
    }

    private Game loadGame(String filename) {
        try {
            return FileUtils.readAsObject(filename);
        } catch (Exception e) {
            return null;
        }

    }

}
