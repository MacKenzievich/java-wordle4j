package ru.yandex.practicum;

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

    protected String getHelpWord(String word, String encryptWord) {
        destructionWord(word, encryptWord);
        List<String> helpWords = foundValidWords(deleteWordsWithUnnecessaryLetters(foundWordsWithCorrectWord()));
        System.out.println(helpWords.size());
        if(!helpWords.isEmpty())  return helpWords.get(random.nextInt(helpWords.size()));
        return "янезнаю";
    }

    private void destructionWord(String word, String encryptWord) {
        for (int i = 0; i < encryptWord.length(); i++) {
            if (encryptWord.charAt(i) == '-') {
                unnecessaryLetters.add(word.charAt(i));
            } else if (encryptWord.charAt(i) == '^'){
                necessaryLetters.add(word.charAt(i));
            } else if (encryptWord.charAt(i) == '+') {
                fixedLetters.put(i, word.charAt(i));
                necessaryLetters.add(word.charAt(i));
            }
        }
    }

    private List<String> foundWordsWithCorrectWord() {
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
