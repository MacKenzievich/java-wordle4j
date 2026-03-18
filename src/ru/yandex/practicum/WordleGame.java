package ru.yandex.practicum;

import ru.yandex.practicum.exeptions.WordLengthExeption;

import java.util.*;

/*
в этом классе хранится словарь и состояние игры
    текущий шаг
    всё что пользователь вводил
    правильный ответ

в этом классе нужны методы, которые
    проанализируют совпадение слова с ответом
    предложат слово-подсказку с учётом всего, что вводил пользователь ранее

не забудьте про специальные типы исключений для игровых и неигровых ошибок
 */
public class WordleGame {
    private WordleDictionary dictionary;
    private String answer;      //Правельный ответ
    private List<String> userAnswers;       // список ответов
    private int steps;      // номер попытки
    private static final int attempts = 6;

    private boolean isGameOver;

    public WordleGame(WordleDictionary dictionary) {
        this.dictionary = dictionary;
        this.answer = dictionary.getWordForGame();
        this.userAnswers = new LinkedList<>();
        this.steps = 0;
        this.isGameOver = false;
    }

    private List<String> getUserAnswers() { // подумать
        return userAnswers;
    }

    public String getWord(String word) throws WordLengthExeption { // сохраняем слово в список ответов.
        userAnswers.add(userWordValidations(word)); // если прошло валидацию.
        return word;
    }

    private String userWordValidations(String word) throws WordLengthExeption { // проверяем на длину слова
        if (word.length() == 5) {
            return word.toLowerCase(); // Не вижу смысла проверять на регистр. Просто приведём всё к одному.
        }
        throw new WordLengthExeption("Слово должно состоять из 5 букв");
    }

    public String getEncryptedAnswer(String answer, String userAnswer) {
        StringBuilder sb = new StringBuilder();
        Map<Character, Integer> charMap = new HashMap<>();

        char[] arrayChar = answer.toCharArray();

        for (Character ch : arrayChar) {
            charMap.put(ch, charMap.getOrDefault(ch, 0) + 1);
        }

        for (int i = 0; i < answer.length(); i++) {
            if (answer.charAt(i) == userAnswer.charAt(i)){ // если буква есть и стоит правильно
                sb.append('+');
                charMap.put(userAnswer.charAt(i), charMap.get(userAnswer.charAt(i)) -1);
            } else if (answer.indexOf(userAnswer.charAt(i)) == -1) { // если буквы нет
                sb.append('-');
            } else {
                if (charMap.get(userAnswer.charAt(i)) != 0){
                    sb.append('^');
                    charMap.put(userAnswer.charAt(i), charMap.get(userAnswer.charAt(i)) -1);
                }
            }
        }
        return sb.toString();
    }


    private int countSteps() { // увеличиваем количество потраченых попыток
        return steps++;
    }

    private boolean checkIsGameOver() {              //Проверяем состояние игры.
        if (steps == attempts && guessWordCheck() == true) {
            return true;
        }
        return false;
    }


}
