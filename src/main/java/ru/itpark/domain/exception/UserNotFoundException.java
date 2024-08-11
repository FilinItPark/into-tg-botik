package ru.itpark.domain.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException() {
        super("Такого пользователя не существует");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
