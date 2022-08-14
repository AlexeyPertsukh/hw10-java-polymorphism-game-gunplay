package hw10_2_game_gunplay.gun;

public class Revolver extends Gun{
    private static final String NAME = "Наган";
    private static final int DAMAGE_MIN = 10;
    private static final int DAMAGE_MAX = 20;
    private static final int CARTRIDGE = 80;
    private static final int CHANCE = 80;

    public Revolver() {
        super(NAME, DAMAGE_MIN, DAMAGE_MAX, CARTRIDGE, CHANCE);
    }
}
