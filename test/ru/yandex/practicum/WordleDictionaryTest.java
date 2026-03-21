package ru.yandex.practicum;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exeptions.DictionaryIsEmptyExeption;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.util.*;
import java.util.List;

public class WordleDictionaryTest {
    private WordleDictionary dict;


    @BeforeEach
    void setUp() {
        dict = new WordleDictionary(List.of("домик", "карта", "речка", "панда", "торта", "зебра"));
    }

    @Test
    void testGetWordForGame() {
        String word = dict.getWordForGame(); // слово получили!
        assertNotNull(word);   // не null
        assertTrue(dict.getWords().contains(word)); // Слово из словаря!
    }

    @Test
    void testGetWordHint() throws DictionaryIsEmptyExeption { // я хз как тестить приватные методы. Вот вам и инкапсуляция.
        String word = "домик";                                   // пусть тестировщики пишут автотесты :) У меня все работает! :)
        String encryptWord = "+++++";                           // проверю логику вызовам публичных

        String wordHint = dict.getWordHint(word, encryptWord);
        assertNotNull(wordHint); // слово вернулось!
        assertEquals(word, wordHint); // логика отработала верно! вернулось единственное слово которое подходит.


        String word1 = "карта";
        String encryptWord1 = "-+--+";
        String expectedWord = "панда";

        assertThrows(DictionaryIsEmptyExeption.class, () -> { // исключение бросает!
            dict.getWordHint(word1, encryptWord1);
        });

        dict = new WordleDictionary(List.of("домик", "карта", "речка", "панда", "торта", "зебра"));
        String wordHint1 = dict.getWordHint(word1, encryptWord1);
        assertEquals(expectedWord, wordHint1); // единственное слово подходит!

        String word2 = "ортта";
        String encryptWord2 = "^^^++";
        String expectedWord2 = "торта";
        dict = new WordleDictionary(List.of("домик", "карта", "речка", "панда", "торта", "зебра"));
        String wordHint2 = dict.getWordHint(word2, encryptWord2);
        assertEquals(expectedWord2, wordHint2); // единственное слово подходит!


    }


}
