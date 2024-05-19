package io.aston.serverside.exception.sql;

public class FailedUpdateException extends RuntimeException {
    public FailedUpdateException(String message) {
        super(message);
    }
}
