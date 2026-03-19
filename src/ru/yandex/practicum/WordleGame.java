package ru.yandex.practicum;

import ru.yandex.practicum.exeptions.NotFoundWordInDictionaryExeption;
import ru.yandex.practicum.exeptions.OnlyRussionWordsExeption;
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
    private String lastAnswer;
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

    public String getUserAnswer(String userWord) throws WordLengthExeption, OnlyRussionWordsExeption,
            NotFoundWordInDictionaryExeption { // сохраняем слово в список ответов.
        userAnswers.add(userWordValidations(userWord)); // если прошло валидацию, сохраняем в список ответов.
        lastAnswer = userWord; // если не пройдет валидацию выше и так будет exeption;
        return checkIsGameOver();
    }


    private String userWordValidations(String userWord) throws WordLengthExeption, OnlyRussionWordsExeption,
            NotFoundWordInDictionaryExeption { // проверяем на длину слова и русские буквы
        userWord = userWord.toLowerCase(); // Не вижу смысла проверять на регистр. Просто приведём всё к одному.
        if (userWord.isEmpty()) {
             // Вызвать метод подсказки
        }
        if (userWord.length() != 5) {
            throw new WordLengthExeption("Слово должно состоять из 5 букв!");
        }
        if (!userWord.matches("[а-я]+")) {
            throw new OnlyRussionWordsExeption("Слово должно состоять только из русских букв!");
        }
        if (!dictionary.getWords().contains(userWord)){
            throw new NotFoundWordInDictionaryExeption("Введеного слова нет в словаре!");
        }
        return userWord;
    }

    private boolean isCorrectAnswer() {
        return answer.equals(lastAnswer);
    }

    private String checkIsGameOver() {              //Проверяем состояние игры.
        if (steps == attempts) {
            isGameOver = true;
            return "Попытки закончились: загаданное слово " + answer;
        } else if (isCorrectAnswer()) {
            isGameOver = true;
            return "Вы отгадали слово! " + answer;
        }
        countSteps();
        return getEncryptedAnswer();
    }


    private String getEncryptedAnswer() {
        StringBuilder sb = new StringBuilder();
        Map<Character, Integer> charMap = new HashMap<>();

        for (char ch : answer.toCharArray()) {
            charMap.put(ch, charMap.getOrDefault(ch, 0) + 1);
        }

        for (int i = 0; i < answer.length(); i++) {
            if (lastAnswer.charAt(i) == answer.charAt(i)) {
                sb.append('+');
                charMap.put(lastAnswer.charAt(i), charMap.get(lastAnswer.charAt(i)) - 1);
            } else {
                sb.append(' ');
            }
        }

        for (int i = 0; i < answer.length(); i++) {
            if (sb.charAt(i) == '+') {
                continue;
            }

            if (!charMap.containsKey(lastAnswer.charAt(i)) || charMap.get(lastAnswer.charAt(i)) == 0) {
                sb.setCharAt(i, '-');
            } else {
                sb.setCharAt(i, '^');
                charMap.put(lastAnswer.charAt(i), charMap.get(lastAnswer.charAt(i) - 1));
            }
        }
        return sb.toString();
    }


    private int countSteps() { // увеличиваем количество потраченых попыток
        return steps++;
    }

    public boolean isGameOver() {
        return isGameOver;
    }
}
