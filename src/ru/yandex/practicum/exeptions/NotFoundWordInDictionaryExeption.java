package ru.yandex.practicum.exeptions;

public class NotFoundWordInDictionaryExeption extends Exception{
    public NotFoundWordInDictionaryExeption(String message) {
        super(message);
    }
}
