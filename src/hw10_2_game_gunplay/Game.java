package hw10_2_game_gunplay;

import hw10_2_game_gunplay.gun.Gun;
import hw10_2_game_gunplay.player.Bot;
import hw10_2_game_gunplay.player.Player;
import hw10_2_game_gunplay.util.Color;
import hw10_2_game_gunplay.util.Help;
import hw10_2_game_gunplay.util.Util;

import java.util.Scanner;

public class Game {

    private static final String VERSION = "ver.2.1.1";
    private static final String COLOR_FOCUS = Color.ANSI_YELLOW;
    private static final String COLOR_DEAD = Color.ANSI_RED;
    public static final String COLOR_HELP = Color.ANSI_BLUE;
    private static final String COLOR_FOOTER = Color.ANSI_BLUE;
    private static final String COLOR_HEADER = Color.ANSI_RED;

    private static final char POINTER = '>';

    private static final String KEY_HELP = "?";
    public static final String KEY_SHOOT = "+";
    public static final String KEY_KILL = "#";
    private static final String KEY_EXIT = "END";
    private static final String EMPTY_STR = "";
    private static final int PAUSE_ON_BOT_SHOT = 1000;

    private final Player player1;
    private final Player player2;
    private Player currentPlayer;
    private final Scanner sc = new Scanner(System.in);

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    //========== основной блок ==================
    public void go() {
        firstPlayer();  //фокус на первого игрока

        Color.printlnColorRed(VERSION);
        printPage();

        while (true) {
            String command = inputCommand();
            if (isCommandExitGame(command)) {
                break;
            }
            processCommand(command);

            if (isWinGame()) {
                printOnWin();
                break;
            }
        }

        //конец игры
        System.out.println();
        System.out.println("I'll be back");
        System.out.println("JAVA A01 \"ШАГ\", Запорожье 2021");
        System.out.println("Перцух Алексей");
    }

    private Player otherPlayer() {
        return currentPlayer == player1 ? player2 : player1;
    }

    private String inputCommand() {
        Color.printColorYellow(currentPlayer.getName() + ", ваш ход. Введите команду:  ");
        if (currentPlayer instanceof Bot) {
            Util.sleep(PAUSE_ON_BOT_SHOT);
            return ((Bot) currentPlayer).getCommand();
        }

        return sc.next();
    }
    //============================================

    private boolean isWinGame() {
        return player1.isDead() || player2.isDead();
    }

    private Player getWinPlayer() {
        if (!isWinGame()) {
            return null;
        } else {
            return (player1.isDead()) ? player2 : player1;
        }
    }

    private Player getDeadPlayer() {
        if (isWinGame()) {
            return (player1.isDead()) ? player1 : player2;
        } else {
            return null;
        }
    }

    private static void printHeader() {
        Color.setTextColor(COLOR_HEADER);
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("           💀💀💀💀💀   КРОВАВАЯ ПЕРЕСТРЕЛКА   💀💀💀💀💀        ");
        System.out.println("-------------------------------------------------------------------------------------------");
        Color.resetTextColor();
    }

    private void printFooter() {
        String text = String.format("%s справка     |  %s сделать выстрел       |  1-%d сменить оружие       |   %s выход   ", KEY_HELP,
                KEY_SHOOT,
                currentPlayer.gunCount(),
                KEY_EXIT);
        Color.printlnColor(text, COLOR_FOOTER);
        Color.printlnColor(". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .",
                COLOR_FOOTER);

        printCurrentPlayerGuns();
        System.out.println();
    }


    private void printPage() {
        printHeader();

        String color1;
        String color2;

        color1 = getColorPlayer(player1);
        color2 = getColorPlayer(player2);

        //распечатываем человечков
        printPlayers();

        //дополнительная информация по игрокам
        Color.printColor(String.format("%13s %-15s", EMPTY_STR, lineHitPoints(player1)), color1);
        System.out.printf("%1s", EMPTY_STR);
        Color.printColor(String.format("%13s %-15s \n", EMPTY_STR, lineHitPoints(player2)), color2);

        printItem("здоровье", player1.getHitPoint(), player2.getHitPoint());
        printItem("выстрелил", player1.getShotCount(), player2.getShotCount());
        printItem("попал", player1.getHitCount(), player2.getHitCount());
        printItem("промазал", player1.getMissCount(), player2.getMissCount());
        printItem("оружие", player1.getCurrentGunName(), player2.getCurrentGunName());

        String shortGunInfoPlayer1 = "(" + player1.shortCurrentGunInfo() + ")";
        String shortGunInfoPlayer2 = "(" + player2.shortCurrentGunInfo() + ")";
        printItem(EMPTY_STR, shortGunInfoPlayer1, shortGunInfoPlayer2);

        Color.printlnColor(". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .", COLOR_FOOTER);

        if (!isWinGame()) {
            printFooter();
        }

    }

    private void printItem(String nameItem, int item1, int item2) {
        printItem(nameItem, String.valueOf(item1), String.valueOf(item2));
    }

    private void printItem(String nameItem, String item1, String item2) {
        if (!nameItem.equalsIgnoreCase(EMPTY_STR)) {
            nameItem += ":";
        }

        nameItem = String.format("%-15s", nameItem);

        String format = "%-17s";
        item1 = String.format(format, item1);
        item2 = String.format(format, item2);

        System.out.print(nameItem);
        Color.printColor(item1, getColorPlayer(player1));
        System.out.printf("%12s", EMPTY_STR);
        Color.printColor(item2, getColorPlayer(player2));
        System.out.println();

    }

    private void printCurrentPlayerGuns() {
        char ch;
        String color;
        Gun currentGun = currentPlayer.getCurrentGun();

        System.out.println("Доступное оружие:");
        for (int i = 0; i < currentPlayer.gunCount(); i++) {
            Gun gun = currentPlayer.getGunByNum(i);
            if (currentGun == gun) {
                ch = POINTER;
                color = COLOR_FOCUS;
            } else {
                ch = ' ';
                color = Color.ANSI_RESET;
            }
            String text = String.format("%c%d. %s   \n", ch, i + 1, gun.info());
            Color.printColor(text, color);
        }

    }

    private void printOnWin() {
        Color.printlnColorRed(getDeadPlayer().getName() + " был трагически застрелен 💀💀💀💀💀");
        Color.printlnColorGreen("Победил " + getWinPlayer().getName() + "!");
    }

    private String getColorPlayer(Player player) {
        if (player.isDead()) {
            return COLOR_DEAD;
        }
        return (player == currentPlayer) ? COLOR_FOCUS : Color.ANSI_RESET;
    }

    //команды
    private void processCommand(String command) {
        //цифры - выбор оружия
        if (Util.isInteger(command)) {
            int num = Integer.parseInt(command);
            if (changeGun(num)) {
                printPage();
            } else {
                System.out.println("Неправильный номер оружия");
            }
            return;
        }

        if (command.equalsIgnoreCase(KEY_HELP)) {
            Help.print();
            return;
        }

        //выстрел
        if (command.equalsIgnoreCase(KEY_SHOOT)) {
            if (currentPlayer instanceof Bot) {
                System.out.println(KEY_SHOOT);
            }
            if (shoot()) {
                Util.pressEnterForContinue();
                nextPlayer();
                printPage();
            }
            return;
        }

        //чит - завалить противника наповал
        if (command.equalsIgnoreCase(KEY_KILL)) {
            otherPlayer().kill();
            System.out.println(otherPlayer().getName() + " сражён наповал таинственным оружием");
            Util.pressEnterForContinue();
            printPage();
            return;
        }

        //ничего из вышеперечисленного - неизвестная команда
        System.out.println("Неизвестная команда");
    }

    //сменить пушку
    private boolean changeGun(int num) {
        return currentPlayer.changeGun(num);
    }

    //меняем игрока
    private void nextPlayer() {
        if (otherPlayer().isDead()) {
            return;
        }

        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    //устанавливаем первого игрока
    private void firstPlayer() {
        currentPlayer = player1;
    }

    private boolean isCommandExitGame(String cmd) {
        return cmd.equalsIgnoreCase(KEY_EXIT);
    }

    private void printPlayers() {

        String color1 = getColorPlayer(player1);
        String color2 = getColorPlayer(player2);

        Color.printColor(String.format("%-11s   %-25s", EMPTY_STR, player1.getName()), color1);
        System.out.printf("%3s", EMPTY_STR);
        Color.printColor(String.format("  %-25s \n", player2.getName()), color2);

        String[] picture1 = player1.getPicture();
        String[] picture2 = player2.getPicture();

        int max = Math.max(picture1.length, picture2.length);
        String pictureLine1;
        String pictureLine2;
        for (int i = 0; i < max; i++) {
            System.out.printf("%14s", EMPTY_STR);

            pictureLine1 = String.format("%-10s", picture1[i]);
            Color.printColor(pictureLine1, color1);

            System.out.printf("%19s", EMPTY_STR);

            pictureLine2 = String.format("%-10s", picture2[i]);
            Color.printColor(pictureLine2, color2);

            System.out.println();
        }
    }

    private boolean shoot() {
        int code = currentPlayer.shot(otherPlayer());
        if (code == Gun.CODE_NO_CARTRIDGES) {
            System.out.println("Выстрел не произведен- нет боеприпасов.");
            return false;
        }

        String shootResult;
        if (code == Gun.CODE_MISSED) {
            shootResult = "не попал!";
        } else {
            shootResult = String.format("противнику нанесен урон %d ед.", code);
        }
        System.out.printf("%s, выстрел(%s): %s \n", currentPlayer.getName(), currentPlayer.getCurrentGunName().toLowerCase(), shootResult);
        return true;
    }

    //строка - линия жизни
    public String lineHitPoints(Player player) {
        final int count = 10;
        StringBuilder builder = new StringBuilder();
        int hitPoint = player.getHitPoint();

        int n = (hitPoint * count) / Player.HP_MAX;
        if (hitPoint > 0 && n == 0) {    //даже если жизни совсем чуть-чуть, рисуем одну палку жизни
            n = 1;
        }

        for (int i = 0; i < count; i++) {
            if (n > i) {
                builder.append("+");
            } else {
                builder.append("-");
            }
        }

        return builder.toString();
    }

}
