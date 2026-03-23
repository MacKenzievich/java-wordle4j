package ru.yandex.practicum.exeptions;

public class OnlyRussionWordsExeption extends RuntimeException {
    public OnlyRussionWordsExeption(String message) {
        super(message);
    }
}
