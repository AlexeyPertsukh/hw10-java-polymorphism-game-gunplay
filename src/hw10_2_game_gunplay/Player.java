package hw10_2_game_gunplay;

import java.util.Scanner;

//Игрок
public class Player {

    public static int HP_MAX = 60;
    public static final String COLOR_FOCUS = Color.ANSI_YELLOW;

    public static final String[] ASCII_PICTURE_LEFT = {
            "( •_•)  ",
            "( ง )--г",
            "/︶\\  ",
    };

    public static final String[] ASCII_PICTURE_RIGHT = {
            "  (•_• ) ",
            "¬--( v)  ",
            "   /︶\\ ",
    };

    private final Gun[] guns;
    private final String name;
    private int hitPoint;
    private int cntShot;        //счетчик выстрелов
    private int cntMiss;        //счетчик промахоа
    private int gunType;        //выбранный тип оружия
    private final String[] picture;

    public Player(int hitPoint, Gun[] guns, String[] picture) {
        this("noname", hitPoint, guns, picture);
    }

    public Player(String name, int hitPoint, Gun[] guns, String[] picture) {
        this.name = name;
        this.hitPoint = hitPoint;
        this.picture = picture;
        //создаем свой массив оружия- доступный только этому юзеру
        this.guns = new Gun[guns.length];
        for (int i = 0; i < guns.length; i++) {
            this.guns[i] = new Gun();
            this.guns[i].loadFrom(guns[i]);
        }
    }

    //ввод команды
    public String nextCmd(Scanner sc) {
        return sc.next();
    }

    //получить повреждение
    public void inputDamage(int damage) {
        if(damage < 0) {    //если прилетит отрицательный урон- это глюк, здоровье не увеличиваем
            damage = 0;
        }
        hitPoint -= damage;
        if (hitPoint < 0) {
            hitPoint = 0;
        }
    }

    //завалить наверняка
    public void kill() {
        hitPoint = 0;
    }

    //живой?
    public boolean isDead() {
        return (0 >= hitPoint);
    }

    //распечатывает рисунок построчно, если все ок- возвращает true
    public boolean printPictLine(int num) {
        if(num < 0 || num >= picture.length) {
            return false;
        }
        System.out.println(getStrPictLine(num));
        return true;
    }

    public String getStrPictLine(int num) {
        if(num < 0 || num >= picture.length) {
            return "";
        }
        return picture[num];
    }

    //строка - линия жизни
    public String getStrHpLine() {
        StringBuilder str = new StringBuilder();
        int n = (hitPoint * 10) / HP_MAX;
        if(hitPoint > 0 && n == 0) {    //даже если жизни совсем чуть-чуть, рисуем одну палку жизни
            n = 1;
        }

        for (int i = 0; i < 10; i++) {
            if(n > i) {
                str.append("+");
            }
            else {
                str.append("-");
            }
        }

        return str.toString();
    }

    //стреляем
    public int shot() {
        Gun gun = guns[gunType];
        int damage = gun.shot();

        if(damage == Gun.NUM_NO_CARTRIDGES) {
            System.out.println("Выстрел не произведен- нет боеприпасов.");
        }
        else {
            String str = (damage == 0) ? "не попал!" : String.format("противнику нанесен урон %d ед.", damage);
            System.out.println(name + ", выстрел: " + str);
            cntShot++;
            if(damage == 0) {
                cntMiss++;
            }
        }

        return damage;
    }

    //распечатываем все свои пушки
    public void printGuns() {
        char ch;
        String color;
        System.out.println("Доступное оружие:");
        for (int i = 0; i < guns.length; i++) {
            if(gunType == i) {
                ch = '>';
                color = COLOR_FOCUS;
            }
            else {
                ch = ' ';
                color = Color.ANSI_RESET;
            }
            Color.printColor(String.format("%c%d. %s   \n", ch, i + 1, guns[i].info()), color);
        }
    }

    //поменять пушку
    public boolean changeGun(int num) {
        num--;
        if(num < 0 || num >= guns.length) {
            return false;
        }
        gunType = num;
        return true;
    }

    //количество пушек
    public int getNumGuns() {
        return guns.length;
    }

    public String nameGun() {
        return guns[gunType].getName();
    }

    public String shortGunInfo() {
        return guns[gunType].shortInfo();
    }

    public int getCntShot() {
        return cntShot;
    }

    public int getCntMiss() {
        return cntMiss;
    }

    public String getName() {
        return name;
    }

    public int getHitPoint() {
        return hitPoint;
    }
}
