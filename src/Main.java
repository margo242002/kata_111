import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите выражение (например, 1 + 2): ");
        String input = scanner.nextLine();

        String[] parts = input.split(" ");
        try {
            if (parts.length != 3) {
                throw new InvalidInputFormatException("Неверный формат ввода");
            }
            if ((isRoman(parts[0]) && !isRoman(parts[2])) || (!isRoman(parts[0]) && isRoman(parts[2]))) {
                throw new MixedNumberSystemsException("Используются одновременно разные системы счисления");
            }
            int num1;
            int num2;
            num1 = parseOperand(parts[0]);
            num2 = parseOperand(parts[2]);

            if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
                throw new OutOfRangeException("Числа должны быть от 1 до 10");
            }
            String operator = parts[1];
            int result;
            switch (operator) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    result = num1 / num2;
                    break;
                default:
                    throw new InvalidOperatorException("Неверный оператор");
            }
            if (isRoman(parts[0]) && isRoman(parts[2]) && result < 0) {
                throw new NegativeNumberException("В римской системе нет отрицательных чисел");
            }
            if (isRoman(parts[0]) && isRoman(parts[2])) {
                String romanResult = arabicToRoman(result);
                System.out.println("Результат: " + romanResult);
            } else {
                System.out.println("Результат: " + result);
            }

        } catch (InvalidInputFormatException | MixedNumberSystemsException | OutOfRangeException | NegativeNumberException | InvalidOperatorException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
    private static int parseOperand(String operand) {
        if (isRoman(operand)) {
            return romanToArabic(operand);
        } else {
            return Integer.parseInt(operand);
        }
    }
    private static boolean isRoman(String str) {
        return str.matches("^[IVXLC]+$");
    }
    private static int romanToArabic(String roman) {
        Map<Character, Integer> romanNumerals = new HashMap<>();
        romanNumerals.put('I', 1);
        romanNumerals.put('V', 5);
        romanNumerals.put('X', 10);
        romanNumerals.put('L', 50);
        romanNumerals.put('C', 100);

        int result = 0;
        int prevValue = 0;
        for (int i = roman.length() - 1; i >= 0; i--) {
            int value = romanNumerals.get(roman.charAt(i));
            if (value < prevValue) {
                result -= value;
            } else {
                result += value;
            }
            prevValue = value;
        }
        return result;
    }
    private static String arabicToRoman(int number) {
        String[] romanNumerals = {"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] arabicValues = {100, 90, 50, 40, 10, 9, 5, 4, 1};

        StringBuilder romanNumber = new StringBuilder();
        for (int i = 0; i < arabicValues.length; i++) {
            while (number >= arabicValues[i]) {
                romanNumber.append(romanNumerals[i]);
                number -= arabicValues[i];
            }
        }
        return romanNumber.toString();
    }
    static class InvalidInputFormatException extends Exception {
        public InvalidInputFormatException(String message) {super(message);}
    }
    static class MixedNumberSystemsException extends Exception {
        public MixedNumberSystemsException(String message) {super(message);}
    }
    static class OutOfRangeException extends Exception {
        public OutOfRangeException(String message) {super(message);}
    }
    static class NegativeNumberException extends Exception {
        public NegativeNumberException(String message) {super(message);}
    }
    static class InvalidOperatorException extends Exception {
        public InvalidOperatorException(String message) {super(message);}
    }
}