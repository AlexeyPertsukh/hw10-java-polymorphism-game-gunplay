package hw10_2_game_gunplay.util;

import hw10_2_game_gunplay.Game;

public class Help {
    private Help() {
    }

    public static void print() {
        Color.setTextColor(Game.COLOR_HELP);
        System.out.println("---");
        System.out.println("Бандитская перестрелка в зловещих подворотнях Запорожья");
        System.out.println("Чит-код убить контрагента сразу: " + Game.KEY_KILL);
        System.out.println();
        System.out.println("https://github.com/AlexeyPertsukh/hw10-java-polymorphism-game-gunplay");
        System.out.println("---");
        System.out.println();
        Color.resetTextColor();
    }
}
