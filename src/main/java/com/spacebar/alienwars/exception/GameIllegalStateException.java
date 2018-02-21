package com.spacebar.alienwars.exception;

public class GameIllegalStateException extends Exception {
    public GameIllegalStateException() {
    }

    public GameIllegalStateException(String message) {
        super(message);
    }

    public GameIllegalStateException(String message, Throwable cause) {
        super(message, cause);
    }
}
