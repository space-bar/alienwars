package com.spacebar.alienwars.display.cli;

import com.spacebar.alienwars.display.DisplayFactory;
import com.spacebar.alienwars.display.DisplayType;
import com.spacebar.alienwars.display.Displayable;
import com.spacebar.alienwars.display.cli.impl.*;

public class CLIDisplayFactory implements DisplayFactory {

    private Displayable homeDisplay;

    private Displayable startGameDisplay;

    private Displayable startNewGameDisplay;

    private Displayable loadSavedGameDisplay;

    private Displayable selectSpaceShipDisplay;

    private Displayable helpDisplay;

    private Displayable aboutDisplay;


    public Displayable getDisplay(DisplayType displayType) {
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


    private Displayable getHomeDisplay() {
        if (homeDisplay == null) {
            homeDisplay = new Home();
        }
        return homeDisplay;
    }


    private Displayable getPlayGameDisplay() {
        if (startGameDisplay == null) {
            startGameDisplay = new PlayGame();
        }
        return startGameDisplay;
    }

    private Displayable getNewGameDisplay() {
        if (startNewGameDisplay == null) {
            startNewGameDisplay = new NewGame();
        }
        return startNewGameDisplay;
    }

    private Displayable getLoadSavedGameDisplay() {
        if (loadSavedGameDisplay == null) {
            loadSavedGameDisplay = new LoadSavedGame();
        }
        return loadSavedGameDisplay;
    }

    private Displayable getSelectSpaceShipDisplay() {
        if (selectSpaceShipDisplay == null) {
            selectSpaceShipDisplay = new SelectSpaceShip();
        }
        return selectSpaceShipDisplay;
    }

    private Displayable getHelpDisplay() {
        if (helpDisplay == null) {
            helpDisplay = new Help();
        }
        return helpDisplay;
    }


    private Displayable getAboutDisplay() {
        if (aboutDisplay == null) {
            aboutDisplay = new About();
        }
        return aboutDisplay;
    }


}
