package ru.yandex.practicum;

import java.util.List;
import java.util.Random;

public class WordleDictionary {

    private List<String> words;

    private final Random random = new Random();

    public WordleDictionary(List<String> words) {
        this.words = words;
    }

    public String getWordForGame() {   // получаем загаданое слово
        return words.get(random.nextInt(words.size()));
    }

}
