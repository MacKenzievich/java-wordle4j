package ru.yandex.practicum;

import ru.yandex.practicum.exeptions.DictionaryIsEmptyExeption;

import java.util.*;

public class WordleDictionary {

    private List<String> words;

    private final Random random = new Random();

    private Set<Character> unnecessaryLetters;
    private Set<Character> necessaryLetters;
    private Map<Integer, Character> fixedLetters;

    public WordleDictionary(List<String> words) {
        this.words = words;
        this.unnecessaryLetters = new HashSet<>();
        this.necessaryLetters = new HashSet<>();
        this.fixedLetters = new HashMap<>();
    }

    protected String getWordForGame() {   // получаем загаданое слово

        return words.get(random.nextInt(words.size()));
    }

    protected List<String> getWords() {
        return words;
    }

    protected String getWordHint(String word, String encryptWord) throws DictionaryIsEmptyExeption {
        destructionWord(word, encryptWord);
        List<String> helpWords = foundValidWords(deleteWordsWithUnnecessaryLetters(foundWordsWithRequiredLetters()));
        if (!helpWords.isEmpty()) return helpWords.get(random.nextInt(helpWords.size()));
        throw new DictionaryIsEmptyExeption("Словарь пуст!");
    }

    private void destructionWord(String word, String encryptWord) {
        for (int i = 0; i < encryptWord.length(); i++) {
            if (encryptWord.charAt(i) == '+') {
                fixedLetters.put(i, word.charAt(i)); // буква на нужном месте
                necessaryLetters.add(word.charAt(i)); // если будет 2 одинаковые буквы. Она тоже нужна.
                unnecessaryLetters.remove(word.charAt(i)); //  если буква есть в нужных удаляем из ненужных
            } else if (encryptWord.charAt(i) == '^') {
                necessaryLetters.add(word.charAt(i)); // буквы нужны
                unnecessaryLetters.remove(word.charAt(i)); // та же песня
            } else if (encryptWord.charAt(i) == '-' && !necessaryLetters.contains(word.charAt(i))) { // береженого бог бережет
                unnecessaryLetters.add(word.charAt(i)); // буквы не нужны

            }
        }
    }

    private List<String> foundWordsWithRequiredLetters() {
        List<String> wordsWithCorrectLettersList = new LinkedList<>();
        for (String word : words) {
            boolean flag = true;
            for (Character ch : necessaryLetters) {
                if (word.indexOf(ch) == -1) {
                    flag = false;
                    break;
                }
            }
            if (flag) wordsWithCorrectLettersList.add(word);
        }
        return wordsWithCorrectLettersList;
    }

    private List<String> deleteWordsWithUnnecessaryLetters(List<String> list) {
        Iterator<String> iterator = list.iterator(); /*Пришлось выучить. Так как были проблемы
        с удалением. Жаль в курсе об этом ни слова. Сэкономил бы кучу времени*/
        while (iterator.hasNext()) {
            String word = iterator.next();
            boolean flag = false;
            for (Character ch : unnecessaryLetters) {
                if (word.indexOf(ch) != -1) {
                    flag = true;
                    break;
                }
            }
            if (flag) iterator.remove();
        }
        return list;
    }

    private List<String> foundValidWords(List<String> list) {
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String word = iterator.next();
            boolean flag = false;
            for (Map.Entry<Integer, Character> entry : fixedLetters.entrySet()) {
                if (word.charAt(entry.getKey()) != entry.getValue()) {
                    flag = true;
                    break;
                }
            }
            if (flag) iterator.remove();
        }
        return list;
    }

}
