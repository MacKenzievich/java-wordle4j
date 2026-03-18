package ru.yandex.practicum;

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
        WordleDictionary dictionary = loader.getDictionary(); // присвоили ссылку на объект.

        for (String word : dictionary.getWords()){
            System.out.println(word);
        }
        System.out.println(dictionary.getWords().size());

    }

}
