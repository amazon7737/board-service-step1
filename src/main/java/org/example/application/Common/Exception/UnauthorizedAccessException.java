package org.example.application.Common.Exception;

public class UnauthorizedAccessException extends BoardException {
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
