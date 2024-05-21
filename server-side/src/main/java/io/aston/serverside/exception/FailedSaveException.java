package io.aston.serverside.exception;

public class FailedSaveException extends RuntimeException {
    public FailedSaveException(String message) {
        super(message);
    }
}
