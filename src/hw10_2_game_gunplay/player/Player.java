package hw10_2_game_gunplay.player;

import hw10_2_game_gunplay.Gun;

//Игрок
public class Player {
    public static int HP_MAX = 100;

    private final Gun[] guns;
    private Gun currentGun;
    private final String name;
    private int hitPoint = HP_MAX;
    private int shotCount;        //счетчик выстрелов
    private int hitCount;        //счетчик попаданий
    private int missCount;        //счетчик промахов
    private final String[] picture;

    public Player(String name, Gun[] guns, String[] picture) {
        this.name = name;
        this.guns = guns;
        this.picture = picture;

        currentGun = guns[0];
    }

    //получить повреждение
    public void subtractHitPoints(int damage) {
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

    //стреляем
    public int shot(Player enemy) {
        int damage = currentGun.shot();

        if (damage == Gun.CODE_NO_CARTRIDGES) {
            return Gun.CODE_NO_CARTRIDGES;
        }
        shotCount++;

        if (damage == Gun.CODE_MISSED) {
            missCount++;
            return Gun.CODE_MISSED;
        } else {
            hitCount++;
        }

        enemy.subtractHitPoints(damage);
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

    //количество пушек
    public int gunCount() {
        return guns.length;
    }

    public String shortCurrentGunInfo() {
        return currentGun.shortInfo();
    }

    public int getShotCount() {
        return shotCount;
    }

    public int getMissCount() {
        return missCount;
    }

    public int getHitCount() {
        return hitCount;
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

    public String getCurrentGunName() {
        return currentGun.getName();
    }

    public Gun getGunByNum(int num) {
        return guns[num];
    }

}
