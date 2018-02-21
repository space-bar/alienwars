package com.spacebar.alienwars.exception;

public class GameInitializationException extends Exception {

    public GameInitializationException() {
    }

    public GameInitializationException(String message) {
        super(message);
    }

    public GameInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
