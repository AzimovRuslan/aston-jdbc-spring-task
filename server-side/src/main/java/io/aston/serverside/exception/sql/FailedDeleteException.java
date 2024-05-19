package io.aston.serverside.exception.sql;

public class FailedDeleteException extends RuntimeException {
    public FailedDeleteException(String message) {
        super(message);
    }
}
