/*
*Эмуляция стрелялки.
Есть юзер, у него в руках есть оружие.
Несколько типов оружия в мешке(3штуки).
У каждого оружия своя реализация.
Выбор оружия 1, 2, 3
**Два юзера, стреляют друг в друга поочередно.
Урон у оружия рандомный в определенном диапазоне.
Например пистолет может отнять от 10 до 20 hp.
У оружия есть патроны. У юзеров hp.
 */
package hw10_2_game_gunplay;

import hw10_2_game_gunplay.gun.Grenade;
import hw10_2_game_gunplay.gun.Gun;
import hw10_2_game_gunplay.gun.Revolver;
import hw10_2_game_gunplay.gun.Shotgun;
import hw10_2_game_gunplay.player.Bot;
import hw10_2_game_gunplay.player.Player;
import hw10_2_game_gunplay.util.PictureStorage;

import java.util.Scanner;

public class Main {

    private static final String NAME1 = "Саня Кирпич";
    private static final String NAME2 = "Мишка Япончик";
    private static final String MODE_PLAYER = "1";
    private static final String MODE_BOT = "2";

    public static void main(String[] args) {
        Player player1 = new Player(NAME1, createGuns(), PictureStorage.ASCII_PICTURE_LEFT);
        Player player2 = createPlayer2();

        Game game = new Game(player1, player2);
        game.go();
    }

    private static Player createPlayer2() {
        Scanner sc = new Scanner(System.in);
        final String name = NAME2;
        final Gun[] guns = createGuns();
        final String[] picture = PictureStorage.ASCII_PICTURE_RIGHT;

        while(true) {
            System.out.printf("Второй игрок: %s - человек, %s- бот: ", MODE_PLAYER, MODE_BOT);
            String command = sc.next();

            if(command.equals(MODE_PLAYER)) {
                return new Player(name, guns, picture);
            } else if(command.equals(MODE_BOT)) {
                return new Bot(name, guns, picture);
            }
        }
    }

    private static Gun[] createGuns() {
        return new Gun[]{
                new Revolver(),
                new Shotgun(),
                new Grenade(),
        };
    }

}
