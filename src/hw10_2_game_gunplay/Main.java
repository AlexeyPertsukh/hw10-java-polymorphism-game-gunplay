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

public class Main {
    public static void main(String[] args) {
        //чит команда '#' валит противника сразу
        Game game = new Game();
        game.go();
    }

}
