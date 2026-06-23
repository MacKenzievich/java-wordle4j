package ru.yandex.practicum.exeptions;

public class WordLengthExeption extends RuntimeException {
    public WordLengthExeption(String message) {
        super(message);
    }
}
