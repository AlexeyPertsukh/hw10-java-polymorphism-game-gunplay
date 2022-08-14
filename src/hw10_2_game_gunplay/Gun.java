package hw10_2_game_gunplay;

import hw10_2_game_gunplay.util.Util;

public class Gun {

    public static final int CODE_NO_CARTRIDGES = -1;
    public static final int CODE_MISSED = 0;

    private final String name;
    private final int damageMin;
    private final int damageMax;
    private final int chance;    //вероятность попадания, 0...100 %

    private int cartridge;        //количество единиц боеприпасоов

    public Gun(String name, int damageMin, int damageMax, int cartridge, int chance) {
       this.name = name;
       this.damageMin = damageMin;
       this.damageMax = damageMax;
       this.cartridge = cartridge;
       this.chance = chance;
    }

    //сделать выстрел. Возвращает нанесенный урон или -1 если нет боеприпасов
    public int shot() {
        if(cartridge <= 0) { //Выстрел не произведен- нет боеприпасов
            return CODE_NO_CARTRIDGES;
        }
        cartridge--;

        int random = Util.randomInt(100);
        if( random > chance) {   //промахнулся?
            return CODE_MISSED;
        }

        return Util.randomInt(damageMin, damageMax);
    }

    public String shortInfo() {
        return String.format("(%d-%d, %d, %d%%)", damageMin, damageMax, cartridge, chance);
    }

    public String info() {
        return String.format("%s, урон %d-%d, боеприпасы %d, вероятность попадания %d%% ", name, damageMin, damageMax, cartridge, chance);
    }

    public String getName() {
        return name;
    }


}
