package hw10_2_game_gunplay.gun;

public class Grenade extends Gun {
    private static final String NAME = "Граната";
    private static final int DAMAGE_MIN = 50;
    private static final int DAMAGE_MAX = 85;
    private static final int CARTRIDGE = 4;
    private static final int CHANCE = 20;

    public Grenade() {
        super(NAME, DAMAGE_MIN, DAMAGE_MAX, CARTRIDGE, CHANCE);
    }
}
