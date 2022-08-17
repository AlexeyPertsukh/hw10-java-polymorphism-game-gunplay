package hw10_2_game_gunplay.gun;

public class Shotgun extends Gun {
    private static final String NAME = "Обрез";
    private static final int DAMAGE_MIN = 30;
    private static final int DAMAGE_MAX = 50;
    private static final int CARTRIDGE = 10;
    private static final int CHANCE = 40;

    public Shotgun() {
        super(NAME, DAMAGE_MIN, DAMAGE_MAX, CARTRIDGE, CHANCE);
    }
}
