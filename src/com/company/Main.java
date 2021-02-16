package com.company;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите адрес страницы, статистику которой котите получить");
        String input = bufferedReader.readLine();
        toDowload(input);
    }

    public static void toDowload(String webpage) {
        try {
            // Create URL object
            URL url = new URL(webpage);
            BufferedReader readr =
                    new BufferedReader(new InputStreamReader(url.openStream()));

            // Enter filename in which you want to download
            BufferedWriter writer = new BufferedWriter(new FileWriter("Download.html"));

            // read each line from stream till end
            String line;
            while ((line = readr.readLine()) != null) {
                writer.write(line);
            }

            readr.close();
            writer.close();
            System.out.println("Загрузка завершена");
            System.out.println("Запускаем анализ html страницы ...");

            for (Map.Entry<String, Integer> entry : toAnalyze().entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                System.out.println(key + " - " + value);
            }

        }

        // Exceptions
        catch (MalformedURLException mue) {
            System.out.println("Некорректный адрес");
        } catch (IOException ie) {
            System.out.println("IOException ");
        }
    }

    public static HashMap<String, Integer> toAnalyze() {
        HashMap<Character, Character> separators = new HashMap<>();
        separators.put(' ', ' ');
        separators.put(',', ',');
        separators.put('.', '.');
        separators.put('!', '!');
        separators.put('?', '?');
        separators.put('<', '<');
        separators.put('>', '>');
        separators.put('+', '+');
        separators.put('-', '-');
        separators.put('=', '=');
        separators.put('"', '"');
        separators.put('{', '{');
        separators.put('}', '}');
        separators.put('«', '«');
        separators.put('»', '»');
        separators.put('#', '#');
        separators.put('/', '/');
        separators.put('\\', '\\');
        separators.put('_', '_');
        separators.put('$', '$');
        separators.put('&', '&');
        separators.put('*', '*');
        separators.put('\'', '\'');
        separators.put('|', '|');
        separators.put(';', ';');
        separators.put(':', ':');
        separators.put('[', '[');
        separators.put(']', ']');
        separators.put('(', '(');
        separators.put(')', ')');
        //ArrayList<String> words = new ArrayList<>();
        HashMap<String, Integer> words = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Download.html"));

            StringBuilder stringBuilder = new StringBuilder();
            while (true) {
                int c = 0;
                try {
                    if (!((c = reader.read()) != -1)) {
                        break;

                    }
                    if (separators.containsKey((char) c) != true) {
                        if ((char)c != ' '){

                        stringBuilder.append((char) c);
                        }

                    } else if (separators.containsKey((char) c) == true) {
                        if (words.containsKey(stringBuilder.toString()) == true) {
                            words.put(stringBuilder.toString(), words.get(stringBuilder.toString()) + 1);
                        } else if (words.containsKey(stringBuilder) != true && stringBuilder.length() >0) {

                            words.put(stringBuilder.toString(), 1);
                            stringBuilder.setLength(0);
                        }
                    }

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println("Файла с таким названием нет");
        }

        return words;
    }
}



