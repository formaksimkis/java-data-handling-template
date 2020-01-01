package com.epam.izh.rd.online.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Arrays;

public class SimpleBigNumbersService implements BigNumbersService {

    public static final String MESSAGE_DIVISION_BY_ZERO = " Divisor is 0, will be returned -1";

    public static final String MESSAGE_RANGE_IS_NEGATIVE = " Range is negative, will be returned -1";

    /**
     * Метод делит первое число на второе с заданной точностью
     * Например 1/3 с точностью 2 = 0.33
     * @param range точность
     * @return результат
     */
    @Override
    public BigDecimal getPrecisionNumber(int a, int b, int range) {
        BigDecimal aDec = new BigDecimal(a);
        BigDecimal bDec = new BigDecimal(b);
        BigDecimal result = new BigDecimal(-1);
        try {
            result = aDec.divide(bDec, new MathContext(range, RoundingMode.HALF_UP));
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage() + SimpleBigNumbersService.MESSAGE_DIVISION_BY_ZERO);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage() + SimpleBigNumbersService.MESSAGE_RANGE_IS_NEGATIVE);
        }
        return result;
    }

    /**
     * Метод находит простое число по номеру
     *
     * @param range номер числа, считая с числа 2
     * @return простое число
     */
    @Override
    public BigInteger getPrimaryNumber(int range) {
        int size = range * 20;                                    // этот размер позволяет не расширять дополнительно size на случай,
                                                                  // если искомое простое число не будет найдено в диапазоне 20 * range (проверено до range = 30 000 000)
        BigInteger result = BigInteger.valueOf(-1);
        int[] numbers;
        try {
            numbers = new int[size];                              // массив натуральных чисел, среди которых ищем простое по его range
        } catch (NegativeArraySizeException e) {
            System.out.println(SimpleBigNumbersService.MESSAGE_RANGE_IS_NEGATIVE);
            return result;
        }

        int prime[] = new int[range + 1];                         // массив простых чисел, формируемый по мере их нахождения, пока не найдём простое с номером range

        for (int i = 0; i < size; i++) {
            numbers[i] = i;
        }
        prime[0] = 2;
        int counterPrime = 0;                                     // счётчик уже найденных простых
        while (counterPrime < range) {                            // пока не найдено простое с номером range применяется решето Эратосфена
            int p = prime[counterPrime++];
            for (int j = p * 2; j < size; j += p) {
                numbers[j] = 0;
            }
            while ((p + 1 < size) && (numbers[p + 1] == 0)) {
                p++;
            }
            if (p + 1 >= size) {                                  // увеличиваем массив поиска вдвое, если не хватило первоначальных 20 * range
                size *= 2;
                numbers = Arrays.copyOf(numbers, size);
                for (int k = size / 2; k < size; k++) {
                    numbers[k] = k;
                }
                counterPrime = 0;
            } else {
                prime[counterPrime] = p + 1;

            }
        }
        result = BigInteger.valueOf(prime[range]);
        return result;
    }
}
