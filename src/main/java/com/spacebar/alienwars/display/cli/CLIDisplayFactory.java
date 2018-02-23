package com.spacebar.alienwars.display.cli;

import com.spacebar.alienwars.display.DisplayFactory;
import com.spacebar.alienwars.display.DisplayType;
import com.spacebar.alienwars.display.Display;
import com.spacebar.alienwars.display.cli.impl.*;

public class CLIDisplayFactory implements DisplayFactory {

    private Display homeDisplay;

    private Display startGameDisplay;

    private Display startNewGameDisplay;

    private Display loadSavedGameDisplay;

    private Display selectSpaceShipDisplay;

    private Display helpDisplay;

    private Display aboutDisplay;


    public Display getDisplay(DisplayType displayType) {
        if (displayType != null) {

            switch (displayType) {

                case HOME:
                    return getHomeDisplay();

                case NEW_GAME:
                    return getNewGameDisplay();

                case LOAD_SAVED_GAME:
                    return getLoadSavedGameDisplay();

                case PLAY_GAME:
                    return getPlayGameDisplay();

                case SELECT_SPACE_SHIP:
                    return getSelectSpaceShipDisplay();

                case HELP:
                    return getHelpDisplay();

                case ABOUT:
                    return getAboutDisplay();

            }
        }
        return getHomeDisplay();
    }


    private Display getHomeDisplay() {
        if (homeDisplay == null) {
            homeDisplay = new Home();
        }
        return homeDisplay;
    }


    private Display getPlayGameDisplay() {
        if (startGameDisplay == null) {
            startGameDisplay = new PlayGame();
        }
        return startGameDisplay;
    }

    private Display getNewGameDisplay() {
        if (startNewGameDisplay == null) {
            startNewGameDisplay = new NewGame();
        }
        return startNewGameDisplay;
    }

    private Display getLoadSavedGameDisplay() {
        if (loadSavedGameDisplay == null) {
            loadSavedGameDisplay = new LoadSavedGame();
        }
        return loadSavedGameDisplay;
    }

    private Display getSelectSpaceShipDisplay() {
        if (selectSpaceShipDisplay == null) {
            selectSpaceShipDisplay = new SelectSpaceShip();
        }
        return selectSpaceShipDisplay;
    }

    private Display getHelpDisplay() {
        if (helpDisplay == null) {
            helpDisplay = new Help();
        }
        return helpDisplay;
    }


    private Display getAboutDisplay() {
        if (aboutDisplay == null) {
            aboutDisplay = new About();
        }
        return aboutDisplay;
    }


}
