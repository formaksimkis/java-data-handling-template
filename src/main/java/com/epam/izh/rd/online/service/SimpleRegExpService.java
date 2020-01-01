package com.epam.izh.rd.online.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleRegExpService implements RegExpService {

    private static final String fileName = "sensitive_data.txt";

    /**
     * Метод должен читать файл sensitive_data.txt (из директории resources) и маскировать в нем конфиденциальную информацию.
     * Номер счета должен содержать только первые 4 и последние 4 цифры (1234 **** **** 5678). Метод должен содержать регулярное
     * выражение для поиска счета.
     *
     * @return обработанный текст
     */
    @Override
    public String maskSensitiveData() {
        String regExConfidentFind = "(\\d{4}\\s)(\\d{4}\\s)(\\d{4})(\\s\\d{4})";
        String lineResult = "";

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(SimpleRegExpService.fileName).getFile());
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lineResult += line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Pattern patternConfident = Pattern.compile(regExConfidentFind);
        Matcher matcherConfident = patternConfident.matcher(lineResult);

        while (matcherConfident.find()) {
            lineResult = new StringBuilder(lineResult)
                    .replace(matcherConfident.start(2), matcherConfident.end(3), "**** ****")
                    .toString();
        }
        return lineResult;
    }

    /**
     * Метод должен считыввать файл sensitive_data.txt (из директории resources) и заменять плейсхолдер ${payment_amount} и ${balance} на заданные числа. Метод должен
     * содержать регулярное выражение для поиска плейсхолдеров
     *
     * @return обработанный текст
     */
    @Override
    public String replacePlaceholders(double paymentAmount, double balance) {
        String regExPlaceHoldersFind = "(\\$\\{\\w+\\})";
        List<String> linesResult= new ArrayList<String>();                                                      // если строк в файле несколько
        String result = "";                                                                                     // результирующая строка
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(SimpleRegExpService.fileName).getFile());
        try (Scanner scanner = new Scanner(file)) {                                                             // читаем из файла в List строк
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                linesResult.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Pattern patternPlaceholder = Pattern.compile(regExPlaceHoldersFind);
        for (String lines : linesResult) {
            Matcher matcherPlaceholder = patternPlaceholder.matcher(lines);
            StringBuilder builder = new StringBuilder(lines);
            while (matcherPlaceholder.find()) {                                                                 // для каждой строки из List ищем совпадение по регулярному выражению
                                                                                                                // и конкретно по payment_amount или balance
                if (builder.substring(matcherPlaceholder.start(1),
                        matcherPlaceholder.end(1)).contains("payment_amount")) {
                    lines = builder
                            .replace(matcherPlaceholder.start(1), matcherPlaceholder.end(1),
                                    String.valueOf(Math.round(paymentAmount))).toString();
                    matcherPlaceholder = patternPlaceholder.matcher(lines);
                    matcherPlaceholder.find();
                }
                if (builder.substring(matcherPlaceholder.start(1),
                        matcherPlaceholder.end(1)).contains("balance")) {
                    lines = builder
                            .replace(matcherPlaceholder.start(1), matcherPlaceholder.end(1),
                                    String.valueOf(Math.round(balance))).toString();
                    matcherPlaceholder = patternPlaceholder.matcher(lines);
                    matcherPlaceholder.find();
                }
                result += lines;
            }
        }
        return result;
    }
}
