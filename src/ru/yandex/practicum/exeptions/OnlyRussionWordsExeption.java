package ru.yandex.practicum.exeptions;

public class OnlyRussionWordsExeption extends Exception {
    public OnlyRussionWordsExeption(String message) {
        super(message);
    }
}
