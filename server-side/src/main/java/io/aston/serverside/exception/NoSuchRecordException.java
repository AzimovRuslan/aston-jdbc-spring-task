package io.aston.serverside.exception;

public class NoSuchRecordException extends RuntimeException {
    public NoSuchRecordException(String message) {
        super(message);
    }
}
