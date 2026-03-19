package ru.yandex.practicum;

import ru.yandex.practicum.exeptions.NotFoundWordInDictionaryExeption;
import ru.yandex.practicum.exeptions.OnlyRussionWordsExeption;
import ru.yandex.practicum.exeptions.WordLengthExeption;

import java.util.Scanner;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {

    public static void main(String[] args) {
        WordleDictionaryLoader loader = new WordleDictionaryLoader();
        WordleDictionary dictionary = loader.getDictionary(); // получили валидный список слов
        WordleGame game = new WordleGame(dictionary);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Игра началась! Введите слово:");
        while (!game.isGameOver()) {
            try {
                System.out.println(game.getUserAnswer(scanner.nextLine()));
            } catch (WordLengthExeption e) {
                System.out.println(e.getMessage());
            } catch (OnlyRussionWordsExeption e) {
                System.out.println(e.getMessage());
            } catch (NotFoundWordInDictionaryExeption e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
