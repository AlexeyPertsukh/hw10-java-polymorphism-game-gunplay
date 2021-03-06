package hw10_2_game_gunplay;

public class Gun {

    public static final int CODE_NO_CARTRIDGES = -1;
    public static final int CODE_MISSED = 0;

    private final String name;
    private final int damageMin;
    private final int damageMax;
    private final int percentageHit;    //процент попадания

    private int cartridge;        //количество единиц боеприпасоов

    public Gun(String name, int damageMin, int damageMax, int cartridge, int percentageHit) {
       this.name = name;
       this.damageMin = damageMin;
       this.damageMax = damageMax;
       this.cartridge = cartridge;
       this.percentageHit = percentageHit;
    }

    //сделать выстрел. Возвращает нанесенный урон или -1 если нет боеприпасов
    public int shot() {
        if(cartridge <= 0) { //Выстрел не произведен- нет боеприпасов
            return CODE_NO_CARTRIDGES;
        }

        int damage = (int) (Math.random() * (damageMax - damageMin)) + damageMin;

        int random = (int) (Math.random() * 100);
        if( random > percentageHit) {   //промахнулся?
            damage = CODE_MISSED;
        }
        cartridge--;
        return damage;
    }

    public String shortInfo() {
        return String.format("(%d-%d, %d, %d%%)", damageMin, damageMax, cartridge, percentageHit);
    }

    public String info() {
        return String.format("%s, урон %d-%d, боеприпасы %d, вероятность попадания %d%% ", name, damageMin, damageMax, cartridge, percentageHit);
    }

    public String getName() {
        return name;
    }
}
