package ru.yandex.practicum;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {   // не очень понял, что хотят в задании по поводу логирования. зачем его предавать во все классы?
    // Делаю его статическим, чтобы логировать из любой части программы.
    private static PrintWriter writer;

    public static void init(String filename) {
        try {
            writer = new PrintWriter(new FileWriter(filename, true), true);
        } catch (IOException e) {
            System.out.println("Ошибка при создании лог-файла: " + e.getMessage());
        }
    }

    public static void log(String message) {
        if (writer != null) {
            writer.println(message);
        }
    }

    public static void close() {
        if (writer != null) {
            writer.close();
        }
    }
}