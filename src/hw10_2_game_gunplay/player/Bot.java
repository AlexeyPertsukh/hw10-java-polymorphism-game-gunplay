package hw10_2_game_gunplay.player;

import hw10_2_game_gunplay.Game;
import hw10_2_game_gunplay.gun.Gun;

//Бот
public class Bot extends Player {

    public Bot(String name, Gun[] guns) {
        super(name, guns);
    }

    //бот стреляет
    //закончились патроны- берет другое оружие
    public String getCommand() {
        if (getCurrentGun().getCartridge() == 0) {
            return String.valueOf(nextNumGunWithCartridge());
        }
        return Game.KEY_SHOOT;           //просто стреляем
    }

    @Override
    public String getName() {
        return super.getName() + "[БОТ]";
    }

    private int nextNumGunWithCartridge() {
        int num = 0;
        while (num < gunCount()) {
            Gun gun = getGunByNum(num);
            if (gun == getCurrentGun()) {
                break;
            }
        }

        while (true) {
            num++;
            if (num >= gunCount()) {
                num = 0;
            }
            if (getGunByNum(num).getCartridge() > 0) {
                return num;
            }
        }
    }
}
