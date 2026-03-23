package ru.yandex.practicum.exeptions;

public class DictionaryIsEmptyExeption extends RuntimeException {
    public DictionaryIsEmptyExeption(String message) {
        super(message);
    }
}
