package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.exeptions.DictionaryIsEmptyExeption;
import ru.yandex.practicum.exeptions.NotFoundWordInDictionaryExeption;
import ru.yandex.practicum.exeptions.OnlyRussionWordsExeption;
import ru.yandex.practicum.exeptions.WordLengthExeption;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.NotDirectoryException;
import java.util.List;

public class WordleGameTest {
    private WordleDictionary dict;
    private WordleGame game;

    @BeforeEach
    void setUp() {
        Logger logger = new Logger();
        dict = new WordleDictionary(List.of("спать", "птица", "сжать", "панда", "торта", "зебра"));
        game = new WordleGame(dict, logger);
    }

    @Test
    void userWordValidationsTest() throws WordLengthExeption, NotFoundWordInDictionaryExeption, DictionaryIsEmptyExeption, OnlyRussionWordsExeption {
        String shortWord = "хочу";
        String englishWord = "sleep";
        String notDictionaryWord = "силна";
        String emptyWord = "";

        assertThrows(WordLengthExeption.class, () -> { // исключение бросает!
            game.userWordValidations(shortWord);
        });
        assertThrows(OnlyRussionWordsExeption.class, () -> { // исключение бросает!
            game.userWordValidations(englishWord);
        });
        assertThrows(NotFoundWordInDictionaryExeption.class, () -> { // исключение бросает!
            game.userWordValidations(notDictionaryWord);
        });

        String word = game.userWordValidations(emptyWord);
        assertNotNull(word);   // слово вернуло
        assertTrue(dict.getWords().contains(word)); // слово из словаря!

    }

    @Test
    void getEncryptedAnswerTest() throws WordLengthExeption, NotFoundWordInDictionaryExeption, DictionaryIsEmptyExeption, OnlyRussionWordsExeption {
        game.setAnswer("спать");
        String input = "птица";
        String input1 = "сжать";
        String input2 = "спать";
        String input3 = "зебра";

        String expected = "^^--^";
        String expected1 = "+-+++";
        String expected2 = "+++++";
        String expected3 = "----^";

        game.getUserAnswer(input);
        assertEquals(expected, game.getEncryptedAnswer());

        game.getUserAnswer(input1);
        assertEquals(expected1, game.getEncryptedAnswer());

        game.getUserAnswer(input2);
        assertEquals(expected2, game.getEncryptedAnswer());

        game.getUserAnswer(input3);
        assertEquals(expected3, game.getEncryptedAnswer());


    }
}
