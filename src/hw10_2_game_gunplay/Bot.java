package hw10_2_game_gunplay;

import java.util.Scanner;

//Бот
public class Bot extends Player {

    private final static int PAUSE = 1000;

    public Bot(String name, int hitPoint, Gun[] guns, String[] picture) {
        super(name, hitPoint, guns, picture);
    }

    //бот стреляет
    //при случае добавить- если закончились патроны, выбрать другое оружие
    @Override
    public String nextCmd(Scanner sc) {
        Util.sleep(PAUSE);
        return Game.KEY_SHOOT;           //просто стреляем
    }

    @Override
    public String getName() {
        return super.getName() + "[БОТ]";
    }
}
