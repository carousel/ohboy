package com.miro.ohboy.exception;

public class PostCostructException extends RuntimeException {
    public PostCostructException(String message) {
        super(message);
    }

    public PostCostructException(String message, Throwable cause) {
        super(message, cause);
    }
}
