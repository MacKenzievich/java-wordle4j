package ru.yandex.practicum;

import ru.yandex.practicum.exeptions.DictionaryIsEmptyExeption;
import ru.yandex.practicum.exeptions.NotFoundWordInDictionaryExeption;
import ru.yandex.practicum.exeptions.OnlyRussionWordsExeption;
import ru.yandex.practicum.exeptions.WordLengthExeption;

import java.util.Scanner;

public class Wordle {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in); Logger logger = new Logger()) {
            logger.init("log_file.txt");
            WordleDictionaryLoader loader = new WordleDictionaryLoader(logger);
            WordleDictionary dictionary = loader.getDictionary(); // получили валидный список слов

            WordleGame game = new WordleGame(dictionary, logger);
            System.out.println("Игра началась! Введите слово:");
            logger.log("Игра началась!");

            while (!game.isGameOver()) {
                String userInput = scanner.nextLine();
                try {
                    // Обработка исключений для следующего ввода
                    game.getUserAnswer(userInput);
                } catch (WordLengthExeption | OnlyRussionWordsExeption | NotFoundWordInDictionaryExeption |
                         DictionaryIsEmptyExeption e) {
                    System.out.println("Ошибка: " + e.getMessage());
                    continue; // пропускаем текущий цикл и ждем нового ввода
                }

                System.out.println(game.getEncryptedAnswer());

                // если пользователь ввёл пустое слово, показываем подсказку
                if (userInput.isEmpty()) {
                    System.out.println(game.getSuggestedWord());
                }
            }

            if (game.getUserRight()) {
                System.out.println("Вы отгадали слово!");
            } else {
                System.out.println("Попытки исчерпаны :(");
            }
        }
    }
}
