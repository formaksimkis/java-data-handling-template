package com.epam.izh.rd.online.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleTextService implements TextService {

    public static final String MESSAGE_NULL_POINTER = "Null pointer exception, param or params are null";

    /**
     * Реализовать функционал удаления строки из другой строки.
     *
     * Например для базовой строки "Hello, hello, hello, how low?" и строки для удаления ", he"
     * метод вернет "Hellollollo, how low?"
     *
     * @param base - базовая строка с текстом
     * @param remove - строка которую необходимо удалить
     */
    @Override
    public String removeString(String base, String remove) {
        String resultRemove = base;
        try {
            resultRemove = base.replace(remove, "");
        } catch (NullPointerException e) {
            System.out.println(SimpleTextService.MESSAGE_NULL_POINTER);
        }
        return resultRemove;
    }

    /**
     * Реализовать функционал проверки на то, что строка заканчивается знаком вопроса.
     *
     * Например для строки "Hello, hello, hello, how low?" метод вернет true
     * Например для строки "Hello, hello, hello!" метод вернет false
     */
    @Override
    public boolean isQuestionString(String text) {
        if (text != null) {
            String regExQuestionMark = "(.*)(\\?$)";
            Pattern patternQestionMark = Pattern.compile(regExQuestionMark);
            Matcher matcherQestionMark = patternQestionMark.matcher(text);
            StringBuilder builder = new StringBuilder(text);
            if (matcherQestionMark.find()) {
                return builder.substring(matcherQestionMark.start(2), matcherQestionMark.end(2)).contains("?");
            } else return false;
        } else {
            try {
                throw new Exception();
            } catch (Exception e) {
                System.out.println(SimpleTextService.MESSAGE_NULL_POINTER);
                return false;
            }
        }

    }

    /**
     * Реализовать функционал соединения переданных строк.
     *
     * Например для параметров {"Smells", " ", "Like", " ", "Teen", " ", "Spirit"}
     * метод вернет "Smells Like Teen Spirit"
     */
    @Override
    public String concatenate(String... elements) {
        String resultConcatenate = "";
        for (String result : elements) {
            resultConcatenate += result;
        }
        return resultConcatenate;
    }

    /**
     * Реализовать функционал изменения регистра в вид лесенки.
     * Возвращаемый текст должен начинаться с прописного регистра.
     *
     * Например для строки "Load Up On Guns And Bring Your Friends"
     * метод вернет "lOaD Up oN GuNs aNd bRiNg yOuR FrIeNdS".
     */
    @Override
    public String toJumpCase(String text) {
        StringBuilder builderJumpCase = new StringBuilder();
        char[] charsJumpCase = new char[0];
        try {
            charsJumpCase = text.toCharArray();
        } catch (NullPointerException e) {
            System.out.println(SimpleTextService.MESSAGE_NULL_POINTER);
        }
        if (charsJumpCase.length > 0) {
            builderJumpCase.append(Character.toLowerCase(charsJumpCase[0]));
            for (int i = 1; i < charsJumpCase.length; i++) {
                if (i % 2 == 0) {
                    builderJumpCase.append(Character.toLowerCase(charsJumpCase[i]));
                } else builderJumpCase.append(Character.toUpperCase(charsJumpCase[i]));
            }
        }
        return builderJumpCase.toString();
    }

    /**
     * Метод определяет, является ли строка палиндромом.
     *
     * Палиндром - строка, которая одинаково читается слева направо и справа налево.
     *
     * Например для строки "а роза упала на лапу Азора" вернется true, а для "я не палиндром" false
     */
    @Override
    public boolean isPalindrome(String string) {
       char[] palindromeChars = new char[0];
       boolean isEqualsChars = true;
        try {
            palindromeChars = string.replaceAll("[\\s]{1,}", "").toCharArray();
        } catch (NullPointerException e) {
            System.out.println(SimpleTextService.MESSAGE_NULL_POINTER);
            isEqualsChars = false;
        }
       for (int i = 0, j = palindromeChars.length - 1; i < j && isEqualsChars; i++, j--) {
           if (Character.toLowerCase(palindromeChars[i]) != Character.toLowerCase(palindromeChars[j])) {
               isEqualsChars = false;
           }
       }
       return palindromeChars.length != 0 && isEqualsChars;
    }
}
