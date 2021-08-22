package hw10_2_game_gunplay;

import java.util.Scanner;

//Бот
public class Bot extends Player{

    public Bot(int hitPoint, Gun[] guns, String[] picture) {
        this("noname[БОТ]", hitPoint, guns, picture);
    }
    public Bot(String name, int hitPoint, Gun[] guns, String[] picture) {
        super( name + "[БОТ]", hitPoint, guns, picture);
    }

    //бот стреляет
    //при случае добавить- если закончились патроны, выбрать другое оружие
    @Override
    public String nextCmd(Scanner sc) {
        My.sleep(1000);         //пауза
        String str = "+";           //просто стреляем
        System.out.println(str);
        My.sleep(1000);
        return str;
    }

}
