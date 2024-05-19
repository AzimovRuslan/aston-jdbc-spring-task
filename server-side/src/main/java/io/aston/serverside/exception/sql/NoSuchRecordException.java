package io.aston.serverside.exception.sql;

public class NoSuchRecordException extends RuntimeException {
    public NoSuchRecordException(String message) {
        super(message);
    }
}
