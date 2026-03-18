package ru.yandex.practicum;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

public class WordleDictionaryLoader {       //полностью инкапсулировали класс
    private static final Charset encoding = StandardCharsets.UTF_8;
    private WordleDictionary dictionary;

    public WordleDictionaryLoader(){
        this.dictionary = fileLoader();
    }

    public WordleDictionary getDictionary(){
        return dictionary;
    }
    private WordleDictionary fileLoader() {
        List<String> listWords = new LinkedList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader
                (new FileInputStream("words_ru.txt"), encoding))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (stringValidation(line)) {
                    listWords.add(letterReplace(line));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        } catch (IOException e) {
            System.out.println("Ошибка при работе с файлом");
        }
        return new WordleDictionary(listWords);
    }

    private boolean stringValidation(String string) {  // проверяем длину строки.
        return string.length() == 5;
    }

    private String letterReplace(String word) { // все в нижний регистр и заменяем буквы.
        word = word.toLowerCase().replace('ё', 'е');
        return word;
    }
}
