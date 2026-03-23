package ru.yandex.practicum.exeptions;

public class NotFoundWordInDictionaryExeption extends RuntimeException {
    public NotFoundWordInDictionaryExeption(String message) {
        super(message);
    }
}
