package hw10_2_game_gunplay.player;

import hw10_2_game_gunplay.Game;
import hw10_2_game_gunplay.Gun;

//Бот
public class Bot extends Player {

    public Bot(String name, int hitPoint, Gun[] guns, String[] picture) {
        super(name, hitPoint, guns, picture);
    }

    //бот стреляет
    //при случае добавить- если закончились патроны, выбрать другое оружие
    public String getCommand() {
        return Game.KEY_SHOOT;           //просто стреляем
    }

    @Override
    public String getName() {
        return super.getName() + "[БОТ]";
    }
}
