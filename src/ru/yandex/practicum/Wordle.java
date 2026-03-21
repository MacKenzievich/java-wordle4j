package ru.yandex.practicum;

import ru.yandex.practicum.exeptions.DictionaryIsEmptyExeption;
import ru.yandex.practicum.exeptions.NotFoundWordInDictionaryExeption;
import ru.yandex.practicum.exeptions.OnlyRussionWordsExeption;
import ru.yandex.practicum.exeptions.WordLengthExeption;

import java.util.Scanner;

public class Wordle {

    public static void main(String[] args) {
        Logger.init("log_file.txt");
        WordleDictionaryLoader loader = new WordleDictionaryLoader();
        WordleDictionary dictionary = loader.getDictionary(); // получили валидный список слов
        WordleGame game = new WordleGame(dictionary);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Игра началась! Введите слово:");
        Logger.log("Игра началась!");
        while (!game.isGameOver()) {
            try {
                String userInput = scanner.nextLine();
                if (userInput.isEmpty()) {
                    game.getUserAnswer(userInput);
                    System.out.println(game.getSuggestedWord());
                    System.out.println(game.getEncryptedAnswer());
                } else {
                    game.getUserAnswer(userInput);
                    System.out.println(game.getEncryptedAnswer());
                }
            } catch (WordLengthExeption e) {
                System.out.println(e.getMessage());
                Logger.log("Произошло игровое исключение: " + e.getMessage());
            } catch (OnlyRussionWordsExeption e) {
                System.out.println(e.getMessage());
                Logger.log("Произошло игровое исключение: " + e.getMessage());
            } catch (NotFoundWordInDictionaryExeption e) {
                System.out.println(e.getMessage());
                Logger.log("Произошло игровое исключение: " + e.getMessage());
            } catch (DictionaryIsEmptyExeption e) {
                System.out.println(e.getMessage());
                Logger.log("Произошло игровое исключение: " + e.getMessage());
            }


        }
        if (game.getUserRight()) {
            System.out.println("Вы отгадали слово!");
        } else {
            System.out.println("Попытки исчерпаны :(");
        }
        Logger.close();

    }
}
