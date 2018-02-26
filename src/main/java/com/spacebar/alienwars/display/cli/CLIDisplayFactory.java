package com.spacebar.alienwars.display.cli;

import com.spacebar.alienwars.display.DisplayFactory;
import com.spacebar.alienwars.display.DisplayType;
import com.spacebar.alienwars.display.Display;
import com.spacebar.alienwars.display.cli.impl.*;
public class CLIDisplayFactory implements DisplayFactory {

    private Display homeDisplay;

    private Display playGameDisplay;

    private Display startNewGameDisplay;

    private Display loadSavedGameDisplay;

    private Display saveGameDisplay;

    private Display selectSpaceShipDisplay;

    private Display helpDisplay;

    private Display aboutDisplay;

    private Display exitDisplay;


    public Display getDisplay(DisplayType displayType) {
        if (displayType != null) {

            switch (displayType) {

                case HOME:
                    return getHomeDisplay();

                case NEW_GAME:
                    return getNewGameDisplay();

                case LOAD_SAVED_GAME:
                    return getLoadSavedGameDisplay();

                case SAVE_GAME:
                    return getSaveGameDisplay();

                case PLAY_GAME:
                    return getPlayGameDisplay();

                case SELECT_SPACE_SHIP:
                    return getSelectSpaceShipDisplay();

                case HELP:
                    return getHelpDisplay();

                case EXIT:
                    return getExitDisplay();

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
        if (playGameDisplay == null) {
            playGameDisplay = new PlayGame();
        }
        return playGameDisplay;
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

    private Display getSaveGameDisplay() {
        if (saveGameDisplay == null) {
            saveGameDisplay = new SaveGame();
        }
        return saveGameDisplay;
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

    private Display getExitDisplay() {
        if (exitDisplay == null) {
            exitDisplay = new Exit();
        }
        return exitDisplay;
    }


}
