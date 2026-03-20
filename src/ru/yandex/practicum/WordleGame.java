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
    private LinkedHashMap<String, String> userAnswers;       // список ответов
    private int steps;      // номер попытки
    private static final int attempts = 6;
    private String lastAnswer;
    private boolean isGameOver;
    private String helpWord;

    public WordleGame(WordleDictionary dictionary) {
        this.dictionary = dictionary;
        this.answer = dictionary.getWordForGame();
        this.userAnswers = new LinkedHashMap<>();
        this.steps = 0;
        this.isGameOver = false;
        this.helpWord = "";
        System.out.println(answer);
    }


    public void getUserAnswer(String userWord) throws WordLengthExeption, OnlyRussionWordsExeption,
            NotFoundWordInDictionaryExeption {
        lastAnswer = userWordValidations(userWord); // если прошло валидацию, сохраняем.
        String encryptWord = checkIsGameOver(); // проверяем состояние игры.
        // Если игра не закончилась получаем зашифрованную подсказку
        userAnswers.put(lastAnswer, encryptWord); // кладем в мапу последнее польз. слово и его шифров. подсказку.
    }

    private String userWordValidations(String userWord) throws WordLengthExeption, OnlyRussionWordsExeption,
            NotFoundWordInDictionaryExeption { // проверяем на длину слова и русские буквы
        userWord = userWord.toLowerCase(); // Не вижу смысла проверять на регистр. Просто приведём всё к одному.
        if (userWord.isEmpty()) {
           helpWord = getHelpAnswer();
           return getHelpWord();
        }
        if (userWord.length() != 5) {
            throw new WordLengthExeption("Слово должно состоять из 5 букв!");
        }
        if (!userWord.matches("[а-я]+")) {
            throw new OnlyRussionWordsExeption("Слово должно состоять только из русских букв!");
        }
        if (!dictionary.getWords().contains(userWord)) {
            throw new NotFoundWordInDictionaryExeption("Введенного слова нет в словаре!");
        }
        return userWord;
    }

    public String getHelpWord(){
        return this.helpWord;
    }

    private String getHelpAnswer() {

        if (steps == 0) {
            return dictionary.getWordForGame();   // используем этот метод если ранее не вводилось слово.
            // получаем рандомное слово из уже валидного словаря.
        } else {
            String lastKey = "";
            String lastValue = "";
            for (Map.Entry<String, String> entry : userAnswers.entrySet()){ // в последней итерации будут посление слова
                lastKey = entry.getKey();
                lastValue = entry.getValue();
            }
            return dictionary.getHelpWord(lastKey, lastValue);
        }
    }

    private String checkIsGameOver() {          //Проверяем состояние игры.
        countSteps();
        if (isCorrectAnswer()) {
            isGameOver = true;
            return "Вы отгадали слово! " + answer;
        } else if (steps == attempts) {
            isGameOver = true;
            return "Попытки закончились: загаданное слово " + answer;
        }
        return getEncryptedAnswer();
    }


    private void countSteps() { // увеличиваем количество потраченных попыток
        steps++;
    }


    private boolean isCorrectAnswer() {
        return answer.equals(lastAnswer);
    }


    public String getEncryptedAnswer() {
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
                charMap.put(lastAnswer.charAt(i), charMap.get(lastAnswer.charAt(i)) - 1);
            }
        }
        return sb.toString();
    }


    public boolean isGameOver() {
        return isGameOver;
    }
}
