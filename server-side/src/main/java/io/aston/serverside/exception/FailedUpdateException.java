package io.aston.serverside.exception;

public class FailedUpdateException extends RuntimeException {
    public FailedUpdateException(String message) {
        super(message);
    }
}
