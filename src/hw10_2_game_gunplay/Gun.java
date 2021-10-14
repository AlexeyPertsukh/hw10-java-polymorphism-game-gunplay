package hw10_2_game_gunplay;

public class Gun {

    public static final int CODE_NO_CARTRIDGES = -1;
    public static final int CODE_MISSED = 0;

    private String name;
    private int damageMin;
    private int damageMax;
    private int cartridge;        //количество единиц боеприпасоов
    private int percentageHit;    //процент попадания

    public Gun() {

    }
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


    public void loadFrom(Gun gun) {
        this.name = gun.name;
        this.damageMin = gun.damageMin;
        this.damageMax = gun.damageMax;
        this.cartridge = gun.cartridge;
        this.percentageHit = gun.percentageHit;
    }

    public String getName() {
        return name;
    }
}
