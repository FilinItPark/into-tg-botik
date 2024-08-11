package ru.itpark.domain.exception;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException() {
        super("У вас нет прав доступа");
    }

    public AccessDeniedException(String message) {
        super(message);
    }
}
