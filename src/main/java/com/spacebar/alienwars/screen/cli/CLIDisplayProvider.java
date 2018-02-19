package com.spacebar.alienwars.screen.cli;

import com.spacebar.alienwars.screen.DisplayProvider;
import com.spacebar.alienwars.screen.DisplayType;
import com.spacebar.alienwars.screen.Displayable;
import com.spacebar.alienwars.screen.cli.display.*;

public class CLIDisplayProvider implements DisplayProvider {

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

                case START_NEW_GAME:
                    return getStartNewGameDisplay();

                case LOAD_SAVED_GAME:
                    return getLoadSavedGameDisplay();

                case START_GAME:
                    return getStartGameDisplay();

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


    private Displayable getStartGameDisplay() {
        if (startGameDisplay == null) {
            startGameDisplay = new StartGame();
        }
        return startGameDisplay;
    }

    private Displayable getStartNewGameDisplay() {
        if (startNewGameDisplay == null) {
            startNewGameDisplay = new StartNewGame();
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
