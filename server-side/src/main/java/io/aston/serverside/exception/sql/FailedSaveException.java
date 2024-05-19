package io.aston.serverside.exception.sql;

public class FailedSaveException extends RuntimeException {
    public FailedSaveException(String message) {
        super(message);
    }
}
