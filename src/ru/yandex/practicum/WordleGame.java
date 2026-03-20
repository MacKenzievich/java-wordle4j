package ru.yandex.practicum;

import ru.yandex.practicum.exeptions.*;

import java.util.*;

public class WordleGame {
    private WordleDictionary dictionary;
    private String answer;      //Правильный ответ
    private LinkedHashMap<String, String> userAnswers;   // список ответов K - слово V - его символьное представление
    private int steps;      // номер попытки
    private static final int attempts = 6; // максимальное количество попыток
    private boolean isGameOver;  // игра закончилась?
    private String suggestedWord;
    private String userInput;
    private boolean userRight;

    public WordleGame(WordleDictionary dictionary) {
        this.dictionary = dictionary;
        this.answer = dictionary.getWordForGame();
        this.userAnswers = new LinkedHashMap<>();
        this.steps = 0;
        this.isGameOver = false;
        this.suggestedWord = "";
        this.userRight = false;
    }


    public void getUserAnswer(String userWord) throws WordLengthExeption, OnlyRussionWordsExeption,
            NotFoundWordInDictionaryExeption, DictionaryIsEmptyExeption {
        userInput = userWordValidations(userWord); // если прошло валидацию, сохраняем в поле.
        checkGameStatus();
    }

    private String userWordValidations(String userWord) throws WordLengthExeption, OnlyRussionWordsExeption,
            NotFoundWordInDictionaryExeption, DictionaryIsEmptyExeption { // проверяем на длину слова и русские буквы
        userWord = userWord.toLowerCase(); // Не вижу смысла проверять на регистр. Просто приведём всё к одному.
        if (userWord.isEmpty()) {   // если ввод пустой получаем подсказку.
            return getAnswerHint();
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

    private String getAnswerHint() throws DictionaryIsEmptyExeption {
        if (steps == 0) {
            suggestedWord = dictionary.getWordForGame(); // получаем рандомное слово из уже валидного словаря.
            return suggestedWord;

        } else {
            String lastKey = "";
            String lastValue = "";
            for (Map.Entry<String, String> entry : userAnswers.entrySet()) { // в последней итерации будут последние слова
                lastKey = entry.getKey();
                lastValue = entry.getValue();
            }
            suggestedWord = dictionary.getWordHint(lastKey, lastValue);
            return suggestedWord;
        }
    }

    private void checkGameStatus() {          //Проверяем состояние игры.
        countSteps();
        if (isCorrectAnswer()) {
            userRight = true;  // для вывода сообщения по окончании игры
            isGameOver = true;
        } else if (steps == attempts) {
            isGameOver = true;
        }
    }

    private void countSteps() { // увеличиваем количество потраченных попыток
        steps++;
    }

    private boolean isCorrectAnswer() {
        return answer.equals(userInput);
    }


    public String getEncryptedAnswer() {
        StringBuilder sb = new StringBuilder();
        Map<Character, Integer> charMap = new HashMap<>();

        for (char ch : answer.toCharArray()) {
            charMap.put(ch, charMap.getOrDefault(ch, 0) + 1);
        }

        for (int i = 0; i < answer.length(); i++) {
            if (userInput.charAt(i) == answer.charAt(i)) {
                sb.append('+');
                charMap.put(userInput.charAt(i), charMap.get(userInput.charAt(i)) - 1);
            } else {
                sb.append(' ');
            }
        }

        for (int i = 0; i < answer.length(); i++) {
            if (sb.charAt(i) == '+') {
                continue;
            }

            if (!charMap.containsKey(userInput.charAt(i)) || charMap.get(userInput.charAt(i)) == 0) {
                sb.setCharAt(i, '-');
            } else {
                sb.setCharAt(i, '^');
                charMap.put(userInput.charAt(i), charMap.get(userInput.charAt(i)) - 1);
            }
        }
        String encryptedWord = sb.toString();
        userAnswers.put(userInput, encryptedWord);
        return encryptedWord;
    }

    public String getSuggestedWord() {
        return suggestedWord;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public boolean getUserRight() {
        return userRight;
    }
}
