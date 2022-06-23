package KataAcademy;

import java.util.Scanner;

public class Main {

    static boolean[] flagRoman = new boolean[3];

    public static void main(String[] args) throws MyException {

        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();
        System.out.println(calc(input));
    }

    public static String calc (String input) throws MyException{

        String[] strings = input.split(" ");
        if (strings.length != 3)
            throw new MyException("Некорректное выражение. Допускаются операции с двумя целыми числами." +
                    "\nЧисла должны быть разделены пробелами от знака операции.");

        int num1, num2;
        String operation = strings[1];
        boolean flagRoman1, flagRoman2;
        int result;

        String[] romanStrings = fromRomanToDecimal(strings);
        flagRoman1 = flagRoman[0];
        flagRoman2 = flagRoman[2];

        if (flagRoman1 != flagRoman2)
            throw new MyException("Запрещено использовать разные системы счисления.");

        num1 = Integer.parseInt(romanStrings[0]);
        num2 = Integer.parseInt(romanStrings[2]);

        if ((num1 < 1 || num1 > 10) || (num2 < 1 || num2 > 10))
            throw new MyException("Допускаются только целые числа от 1 до 10.");

        switch (operation) {
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
                throw new MyException("Неверно введена операция. Доступны символы: '+', '-', '*' и '/'.");
        }
        //На данном этапе переменные flagRoman1 и flagRoman2 гарантированно равны друг другу, поэтому
        //для определения системы счисления мы вправе воспользоваться любой из двух переменных
        if (!flagRoman1)
            return "Ответ: "+ result;
        else if(result < 1)
            throw new MyException("В римской системе счисления не существует числа '0' и отрицательных чисел.");
        else {
            return "Ответ: " + fromDecimalToRoman(result);
        }
    }

    static String[] fromRomanToDecimal(String str[]) {
        String[] result = new String[3];
        for (int i = 0; i <= 2; i+= 2) {
            for (Roman num : Roman.values()) {
                if (num.toString().equals(str[i])) {
                    flagRoman[i] = true;
                    result[i] = num.getNumber();
                    break;
                } else if (num.getNumber() == "10") {
                    try {
                        result[i] =((Integer)Integer.parseInt(str[i])).toString();
                    } catch (NumberFormatException e) {
                        System.out.println("Введены некорректные значения чисел. Допускаются только целые числа от 1 до 10 включительно.");
                        System.out.println(e.getMessage());
                        System.exit(1);
                    }
                }
            }
        }
        return result;
    }

    static String fromDecimalToRoman(int result) {
       StringBuilder str = new StringBuilder();
       Roman[] values = Roman.values();

       for (int i = Roman.values().length - 1; i >= 0; i--) {
           while (result >= Integer.parseInt(values[i].getNumber())) {
               str.append(values[i]);
               result-= Integer.parseInt(values[i].getNumber());
           }
       }
       return str.toString();

    }

}

