package io.aston.serverside.exception;

public class FailedDeleteException extends RuntimeException {
    public FailedDeleteException(String message) {
        super(message);
    }
}
