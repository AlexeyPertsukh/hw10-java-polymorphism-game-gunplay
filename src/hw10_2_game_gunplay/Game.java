package hw10_2_game_gunplay;

import hw10_2_game_gunplay.player.Bot;
import hw10_2_game_gunplay.player.Player;
import hw10_2_game_gunplay.util.Color;
import hw10_2_game_gunplay.util.PictureStorage;
import hw10_2_game_gunplay.util.Util;

import java.util.Scanner;

public class Game {

    private static final String VERSION = "ver.1.9";
    private static final String COLOR_FOCUS = Color.ANSI_YELLOW;
    private static final String COLOR_DEAD = Color.ANSI_RED;
    private static final String COLOR_HELP = Color.ANSI_BLUE;
    private static final String COLOR_FOOTER = Color.ANSI_BLUE;
    private static final String COLOR_HEADER = Color.ANSI_RED;

    private static final char POINTER = '>';

    private static final String NAME1 = "Саня Кирпич";
    private static final String NAME2 = "Мишка Япончик";
    private static final String KEY_HELP = "?";
    public static final String KEY_SHOOT = "+";
    private static final String KEY_KILL = "#";
    private static final String KEY_EXIT = "END";
    private static final String EMPTY_STR = "";

    private static final int MODE_PLAYER = 1;
    private static final int MODE_BOT = 2;
    private static final int PAUSE_ON_BOT_SHOT = 1000;

    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Player otherPlayer;
    private final Scanner sc;

    public Game() {
        sc = new Scanner(System.in);
    }

    //========== основной блок ==================
    public void go() {
        int mode = inputGameMode();
        initPlayers(mode);
        firstPlayer();  //фокус на первого игрока

        Color.printlnColorRed(VERSION);
        printPage();

        while (true) {
            String command = inputCommand();
            if(isCommandExitGame(command)) {
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

    private String inputCommand() {
        Color.printColorYellow(currentPlayer.getName() + ", ваш ход. Введите команду:  ");
        if(currentPlayer instanceof Bot) {
            Util.sleep(PAUSE_ON_BOT_SHOT);
            return ((Bot) currentPlayer).getCommand();
        }

        return sc.next();
    }
    //============================================

    private void initPlayers(int mode) {
        player1 = new Player(NAME1, Player.HP_MAX, createGuns(), PictureStorage.ASCII_PICTURE_LEFT);
        if(mode == MODE_PLAYER) {
            player2 = new Player(NAME2, Player.HP_MAX, createGuns(), PictureStorage.ASCII_PICTURE_RIGHT);    //второму- больше патронов
        }
        else {
            player2 = new Bot(NAME2, Player.HP_MAX, createGuns(), PictureStorage.ASCII_PICTURE_RIGHT);
        }
    }

    private int inputGameMode() {
        String text = String.format("Введите режим игры (%d - с человеком, %d - с ботом): ", MODE_PLAYER, MODE_BOT);
        return Util.nextInt(text, MODE_PLAYER, MODE_BOT);
    }

    private boolean isWinGame() {
        return player1.isDead() || player2.isDead();
    }

    private Player getWinPlayer() {
        if (!isWinGame()) {
            return null;
        } else {
            return (player1.isDead())? player2 : player1;
        }
    }

    private Player getDeadPlayer() {
        if(isWinGame()) {
            return (player1.isDead())? player1 : player2;
        } else {
            return null;
        }
    }

    private void printHeader() {
        Color.setTextColor(COLOR_HEADER);
        System.out.println("-------------------------------------------------------------------------------------------");
        System.out.println("           💀💀💀💀💀   КРОВАВАЯ ПЕРЕСТРЕЛКА   💀💀💀💀💀        ");
        System.out.println("-------------------------------------------------------------------------------------------");
        Color.resetTextColor();
    }

    private void printFooter() {
        String text = String.format("%s справка     |  %s сделать выстрел       |  1-%d сменить оружие       |   %s выход   ", KEY_HELP,
                KEY_SHOOT,
                currentPlayer.sizeGuns(),
                KEY_EXIT);
        Color.printlnColor(text, COLOR_FOOTER);
        Color.printlnColor(". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .",
                COLOR_FOOTER);

        printGunsByCurrentPlayer();
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
        Color.printColor(String.format("%13s %-15s", EMPTY_STR, player1.getStrHpLine()), color1);
        System.out.printf("%1s", EMPTY_STR);
        Color.printColor(String.format("%13s %-15s \n", EMPTY_STR, player2.getStrHpLine()), color2);

        printItem("здоровье", player1.getHitPoint(), player2.getHitPoint());
        printItem("выстрелил", player1.getCntShot(), player2.getCntShot());
        printItem("попал", player1.getCntHit(), player2.getCntHit());
        printItem("промазал", player1.getCntMiss(), player2.getCntMiss());
        printItem("оружие", player1.nameGun(), player2.nameGun());
        printItem(EMPTY_STR, player1.shortGunInfo(), player2.shortGunInfo());

        Color.printlnColor(". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .", COLOR_FOOTER);

        if (!isWinGame()) {
            printFooter();
        }

    }

    private void printItem(String nameItem, int item1, int item2) {
        printItem(nameItem, String.valueOf(item1), String.valueOf(item2));
    }

    private void printItem(String nameItem, String item1, String item2) {
        if(!nameItem.equalsIgnoreCase(EMPTY_STR)) {
            nameItem += ":";
        }

        nameItem = String.format("%-15s", nameItem);

        String format = "%-17s";
        item1 = String.format(format, item1);
        item2 = String.format(format, item2);

        System.out.print(nameItem);
        Color.printColor(item1, getColorPlayer(player1));
        System.out.printf("%12s",EMPTY_STR);
        Color.printColor(item2, getColorPlayer(player2));
        System.out.println();

    }

    private void printGunsByCurrentPlayer() {
        char ch;
        String color;
        Gun currentGun = currentPlayer.getCurrentGun();

        System.out.println("Доступное оружие:");
        for (int i = 0; i < currentPlayer.sizeGuns(); i++) {
            Gun gun = currentPlayer.getGun(i);
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

    private void printHelp() {
        Color.setTextColor(COLOR_HELP);
        System.out.println("---");
        System.out.println("Бандитская перестрелка в зловещих подворотнях Запорожья");
        System.out.println("Чит-код убить контрагента сразу: " + KEY_KILL);
        System.out.println();
        System.out.println("https://github.com/AlexeyPertsukh/hw10-java-polymorphism-game-gunplay");
        System.out.println("---");
        System.out.println();
        Color.resetTextColor();

    }

    private void printOnWin() {
        Color.printlnColorRed(getDeadPlayer().getName() + " был трагически застрелен 💀💀💀💀💀");
        Color.printlnColorGreen("Победил " + getWinPlayer().getName() + "!");
    }

    private String getColorPlayer(Player player) {
        if(player.isDead()) {
            return COLOR_DEAD;
        }
        return (player ==  currentPlayer) ? COLOR_FOCUS : Color.ANSI_RESET;
    }

    //команды
    private void processCommand(String cmd) {
        //цифры - выбор оружия
        if(Util.isInteger(cmd)) {
            int num = Integer.parseInt(cmd);
            if(changeGun(num)) {
                printPage();
            } else {
                System.out.println("Неправильный номер оружия");
            }
            return;
        }

        if(cmd.equalsIgnoreCase(KEY_HELP)) {
            printHelp();
            return;
        }

        //выстрел
        if (cmd.equalsIgnoreCase(KEY_SHOOT)) {
            if(currentPlayer instanceof Bot) {
                System.out.println(KEY_SHOOT);
            }
            if(shoot()) {
                Util.pressEnterForContinue();
                nextPlayer();
                printPage();
            }
            return;
        }

        //чит - завалить противника наповал
        if(cmd.equalsIgnoreCase(KEY_KILL)) {
            otherPlayer.kill();
            System.out.println(otherPlayer.getName() + " сражён наповал таинственным оружием");
            Util.pressEnterForContinue();
            printPage();
            return;
        }

        //ничего из вышеперечисленного - неизвестная команда
        System.out.println("Неизвестная команда");
    }

    private boolean changeGun(int num) {
        //сменить пушку
        if (num > 0 && num < currentPlayer.sizeGuns() + 1) {
            if (currentPlayer.changeGun(num)) {
                return true;
            } else {
                System.out.println("Поменять пушку не удалось, что-то пошло не так.");
                return false;
            }
        }
        return false;
    }

    //меняем игрока
    private void nextPlayer() {
        if(otherPlayer.isDead()) {
            return;
        }

        if(currentPlayer == player1) {
            currentPlayer = player2;
            otherPlayer = player1;
        } else {
            currentPlayer = player1;
            otherPlayer = player2;
        }

    }

    //устанавливаем первого игрока
    private void firstPlayer() {
        currentPlayer = player1;
        otherPlayer = player2;
    }

    private boolean isCommandExitGame(String cmd) {
        return cmd.equalsIgnoreCase(KEY_EXIT);
    }

    private void printPlayers() {

        String color1 = getColorPlayer(player1);
        String color2 = getColorPlayer(player2);

        Color.printColor(String.format("%-11s   %-25s", EMPTY_STR, player1.getName()), color1);
        System.out.printf("%3s", EMPTY_STR);
        Color.printColor(String.format("  %-25s \n",  player2.getName()), color2 );

        String[] pic1 = player1.getPicture();
        String[] pic2 = player2.getPicture();

        int max = Math.max(pic1.length, pic2.length);
        String str1;
        String str2;
        for (int i = 0; i < max; i++) {
            System.out.printf("%14s",EMPTY_STR);

            str1 = String.format("%-10s", pic1[i]);
            Color.printColor(str1, color1);

            System.out.printf("%19s",EMPTY_STR);

            str2 = String.format("%-10s", pic2[i]);
            Color.printColor(str2, color2);

            System.out.println();
        }
    }

    private boolean shoot() {
        int code = currentPlayer.shot(otherPlayer);
        if(code == Gun.CODE_NO_CARTRIDGES) {
            System.out.println("Выстрел не произведен- нет боеприпасов.");
            return false;
        }

        String shootResult;
        if(code == Gun.CODE_MISSED) {
            shootResult = "не попал!";
        } else {
            shootResult = String.format("противнику нанесен урон %d ед.", code);
        }
        System.out.printf("%s, выстрел(%s): %s \n", currentPlayer.getName(), currentPlayer.getGunNameLowerCase(), shootResult);
        return true;
    }

    private static Gun[] createGuns() {
        return new Gun[]{
                new Gun("Наган", 15, 30, 20, 80),
                new Gun("Обрез", 30, 45, 10, 50),
                new Gun("Граната", 50, 85, 4, 20)
        };
    }

}
