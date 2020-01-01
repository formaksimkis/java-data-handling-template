package com.epam.izh.rd.online.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleDateService implements DateService {

    public static final String MESSAGE_WRONG_DATE_FORMAT = "Wrong date format. Needed yyyy-mm-dd hh:mm";

    public static final String MESSAGE_WRONG_DATE_FORMATTER =  "Formatter is null, will be returned ";

    /**
     * Метод парсит дату в строку
     *
     * @param localDate дата
     * @return строка с форматом день-месяц-год(01-01-1970)
     */
    @Override
    public String parseDate(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY");
        return formatter.format(localDate);
    }

    /**
     * Метод парсит строку в дату
     *
     * @param string строка в формате год-месяц-день часы:минуты (1970-01-01 00:00)
     * @return дата и время
     */
    @Override
    public LocalDateTime parseString(String string) {
        Pattern pattern = Pattern.compile("^([01][0-9][0-9][0-9]|[2][0][01][0-9])\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])\\s([01][0-9]|2[0-3])\\:([0-5][0-9])$");
        Matcher matcher = pattern.matcher(string);
        LocalDateTime dateTime;
        if (matcher.find()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            dateTime = LocalDateTime.parse(string, formatter);
        } else {
            System.out.println(SimpleDateService.MESSAGE_WRONG_DATE_FORMAT);
            dateTime = null;
        }
        return  dateTime;
    }

    /**
     * Метод конвертирует дату в строку с заданным форматом
     *
     * @param localDate исходная дата
     * @param formatter формат даты
     * @return полученная строка
     */
    @Override
    public String convertToCustomFormat(LocalDate localDate, DateTimeFormatter formatter) {
        try {
            return formatter.format(localDate);
        } catch (NullPointerException e) {
            System.out.println(SimpleDateService.MESSAGE_WRONG_DATE_FORMATTER + e.getMessage());
            return null;
        }
    }

    /**
     * Метод получает следующий високосный год
     *
     * @return високосный год
     */
    @Override
    public long getNextLeapYear() {
        long year = LocalDate.now().getYear();
        year++;
        boolean flagIsLeapYear = false;
        while (!flagIsLeapYear) {
            if (year % 100 == 0) {
                if (year % 400 == 0) {
                    flagIsLeapYear = true;
                } else year++;
            } else if (year % 4 == 0) {
                flagIsLeapYear = true;
            } else year++;
        }
        return year;
    }

    /**
     * Метод считает число секунд в заданном году
     *
     * @return число секунд
     */
    @Override
    public long getSecondsInYear(int year) {
        long lengthOfYear = LocalDate.of(year,1,1).lengthOfYear();
        return lengthOfYear * 86400;
    }


}
