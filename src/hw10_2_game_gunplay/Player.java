package hw10_2_game_gunplay;

import java.util.Scanner;

//Игрок
public class Player {

    public static int HP_MAX = 60;
    public static final int NUM_FIRST_GUN = 1;

    private final Gun[] guns;
    private Gun currentGun;
    private final String name;
    private int hitPoint;
    private int cntShot;        //счетчик выстрелов
    private int cntHit;        //счетчик попаданий
    private int cntMiss;        //счетчик промахов
    private final String[] picture;

    public Player(String name, int hitPoint, Gun[] guns, String[] picture) {
        this.name = name;
        this.hitPoint = hitPoint;
        this.picture = picture;
        this.guns = guns;
        setFirstGun();
    }

    //ввод команды
    public String nextCmd(Scanner sc) {
        return sc.next();
    }

    //получить повреждение
    public void inputDamage(int damage) {
        if (damage < 0) {    //если прилетит отрицательный урон- это глюк, здоровье не увеличиваем
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


    //строка - линия жизни
    public String getStrHpLine() {
        StringBuilder str = new StringBuilder();
        int n = (hitPoint * 10) / HP_MAX;
        if (hitPoint > 0 && n == 0) {    //даже если жизни совсем чуть-чуть, рисуем одну палку жизни
            n = 1;
        }

        for (int i = 0; i < 10; i++) {
            if (n > i) {
                str.append("+");
            } else {
                str.append("-");
            }
        }

        return str.toString();
    }

    //стреляем
    public int shot(Player player) {
        int damage = currentGun.shot();

        if (damage == Gun.CODE_NO_CARTRIDGES) {
            return Gun.CODE_NO_CARTRIDGES;
        }
        cntShot++;

        if (damage == Gun.CODE_MISSED) {
            cntMiss++;
            return Gun.CODE_MISSED;
        } else {
            cntHit++;
        }

        player.inputDamage(damage);
        return damage;

    }

    //поменять пушку
    public boolean changeGun(int num) {
        num--;
        if (num < 0 || num >= guns.length) {
            return false;
        }
        currentGun = guns[num];
        return true;
    }

    private void setFirstGun() {
        changeGun(NUM_FIRST_GUN);
    }

    //количество пушек
    public int sizeGuns() {
        return guns.length;
    }

    public String nameGun() {
        return currentGun.getName();
    }

    public String shortGunInfo() {
        return currentGun.shortInfo();
    }

    public int getCntShot() {
        return cntShot;
    }

    public int getCntMiss() {
        return cntMiss;
    }

    public int getCntHit() {
        return cntHit;
    }

    public String getName() {
        return name;
    }

    public int getHitPoint() {
        return hitPoint;
    }

    public String[] getPicture() {
        return picture;
    }

    public Gun getCurrentGun() {
        return currentGun;
    }

    public String getGunName() {
        return currentGun.getName();
    }

    public String getGunNameLowerCase() {
        return getGunName().toLowerCase();
    }

    public Gun getGun(int num) {
        return guns[num];
    }

}
