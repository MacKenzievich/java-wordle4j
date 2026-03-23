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
                if (userInput.isEmpty()) {
                    game.getUserAnswer(userInput);
                    System.out.println(game.getSuggestedWord());
                    System.out.println(game.getEncryptedAnswer());
                } else {
                    game.getUserAnswer(userInput);
                    System.out.println(game.getEncryptedAnswer());
                }
            }
            if (game.getUserRight()) {
                System.out.println("Вы отгадали слово!");
            } else {
                System.out.println("Попытки исчерпаны :(");
            }
        } catch (WordLengthExeption e) {
            System.out.println(e.getMessage());
        } catch (OnlyRussionWordsExeption e) {
            System.out.println(e.getMessage());
        } catch (NotFoundWordInDictionaryExeption e) {
            System.out.println(e.getMessage());
        } catch (DictionaryIsEmptyExeption e) {
            System.out.println(e.getMessage());
        }


    }
}
