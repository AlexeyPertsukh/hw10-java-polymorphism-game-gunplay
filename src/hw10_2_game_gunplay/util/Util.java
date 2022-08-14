package hw10_2_game_gunplay.util;

import java.util.Scanner;

public class Util {

    private Util(){
    }

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    //пауза
    public static void sleep(int n){
        try {
            Thread.sleep(n);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public static int nextInt(String text, int min, int max){
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.print(text);
            String cmd = sc.next();

            if(isInteger(cmd)) {
                int num = Integer.parseInt(cmd);
                if(num >= min && num <= max) {
                    return num;
                }
            }
        }
    }

    public static void pressEnterForContinue() {
        System.out.println("...");
        System.out.print("для продолжения нажмите <enter>");
        //не выносить инициализацию этого сканера отсюда, не передавать на вход метода!
        //постоянно пересоздаем сканнер в этом методе из-за глюков при переводе фокуса ввода(курсора) из консоли в код и обратно
        Scanner sc = new Scanner(System.in);
        sc.nextLine();
    }

}
