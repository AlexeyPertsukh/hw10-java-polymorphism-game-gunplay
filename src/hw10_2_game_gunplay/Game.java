package hw10_2_game_gunplay;

import java.util.Scanner;

public class Game {

    private static final String VERSION = "ver.1.5";
    private static final String COLOR_FOCUS = Color.ANSI_YELLOW;
    private static final String COLOR_DEAD = Color.ANSI_RED;
    private static final String COLOR_HELP = Color.ANSI_BLUE;
    private static final String COLOR_FOOTER = Color.ANSI_BLUE;
    private static final String COLOR_HEADER = Color.ANSI_RED;

    private static final char POINTER = '>';

    private static final String NAME1 = "Игрок1";
    private static final String NAME2 = "Игрок2";
    private static final String KEY_HELP = "?";
    private static final String KEY_SHOOT = "+";
    private static final String KEY_KILL = "#";
    private static final String KEY_EXIT = "END";

    private static final int MODE_PLAYER = 1;
    private static final int MODE_BOT = 2;
    private static final int PAUSE = 3000;


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
        String cmd;
        int mode = inputGameMode();
        initPlayers(mode);
        firstPlayer();  //фокус на первого игрока

        Color.printlnColorRed(VERSION);
        printPage();

        while (true) {
            Color.printColorYellow(currentPlayer.getName() + ", ваш ход. Введите команду:  ");
            cmd = currentPlayer.nextCmd(sc);
            if(isExitGame(cmd)) {
                break;
            }
            processCmd(cmd);

            if (isWinGame()) {
                printOnWin();
                break;
            }
        }

        //конец игры
        System.out.println();
        System.out.println("I'll be back");
        System.out.println("JAVA A01 \"ШАГ\", Запорожье 2021");
    }
    //============================================

    private void initPlayers(int mode) {
        player1 = new Player(NAME1, Player.HP_MAX, createGuns(), PictureStorage.ASCII_PICTURE_LEFT);
        if(mode == MODE_PLAYER) {
            player2 = new Player(NAME2, Player.HP_MAX + 10, createGuns(), PictureStorage.ASCII_PICTURE_RIGHT);    //второму- больше патронов
        }
        else {
            player2 = new Bot(NAME2, Player.HP_MAX + 10, createGuns(), PictureStorage.ASCII_PICTURE_RIGHT);
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
        if(isWinGame()) {
            return (player1.isDead())? player2 : player1;
        } else {
            return null;
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
                currentPlayer.getNumGuns(),
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
        Color.printColor(String.format("              %-15s", player1.getStrHpLine()), color1);
        Color.printColor(String.format("              %-15s     \n", player2.getStrHpLine()), color2);

        System.out.printf("здоровье:     %-17d            %-17d     \n", player1.getHitPoint(), player2.getHitPoint());
        System.out.printf("выстрелил:    %-17d            %-17d     \n", player1.getCntShot(), player2.getCntShot());
        System.out.printf("промазал:     %-17d            %-17d     \n", player1.getCntMiss(), player2.getCntMiss());
        System.out.printf("оружие:       %-17s            %-17s     \n", player1.nameGun(), player2.nameGun());
        System.out.printf("              %-17s            %-17s            \n", player1.shortGunInfo(), player2.shortGunInfo());
        Color.printlnColor(". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .", COLOR_FOOTER);

        if (!isWinGame()) {
            printFooter();
        }

    }

    private void printGunsByCurrentPlayer() {
        Gun[] guns = currentPlayer.getGuns();
        Gun currentGun = currentPlayer.getCurrentGun();
        char ch;
        String color;
        System.out.println("Доступное оружие:");
        for (int i = 0; i < guns.length; i++) {
            if (currentGun == guns[i]) {
                ch = POINTER;
                color = COLOR_FOCUS;
            } else {
                ch = ' ';
                color = Color.ANSI_RESET;
            }
            Color.printColor(String.format("%c%d. %s   \n", ch, i + 1, guns[i].info()), color);
        }

    }

    private void printHelp() {
        Color.setTextColor(COLOR_HELP);
        System.out.println("-----");
        System.out.println("Бандитская перестрелка в зловещих подворотнях Запорожья");
        System.out.println("https://github.com/AlexeyPertsukh/hw10-java-polymorphism-game-gunplay");
        System.out.println("-----");
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
    private void processCmd(String cmd) {
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
            if(shoot()) {
                nextPlayer();
                System.out.println("пауза...");
                Util.sleep(PAUSE);
                printPage();
            }
            return;
        }

        //чит - завалить противника наповал
        if(cmd.equalsIgnoreCase(KEY_KILL)) {
            otherPlayer.kill();
            System.out.println(otherPlayer.getName() + " сражён наповал таинственным оружием");
            System.out.println("пауза...");
            Util.sleep(PAUSE);
//            nextPlayer();
            printPage();
            return;
        }

        //ничего из вышеперечисленного - неизвестная команда
        System.out.println("Неизвестная команда");
    }

    private boolean changeGun(int num) {
        //сменить пушку
        if (num > 0 && num < currentPlayer.getNumGuns() + 1) {
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

    private boolean isExitGame(String cmd) {
        return cmd.equalsIgnoreCase(KEY_EXIT);
    }

    private void printPlayers() {

        String color1 = getColorPlayer(player1);
        String color2 = getColorPlayer(player2);

        Color.printColor(String.format("%-12s   %-25s", "", player1.getName()), color1);
        Color.printColor(String.format("  %-25s \n",  player2.getName()), color2 );

        String[] pic1 = player1.getPicture();
        String[] pic2 = player2.getPicture();

        int max = Math.max(pic1.length, pic2.length);
        String str1;
        String str2;
        for (int i = 0; i < max; i++) {
            System.out.printf("%14s","");

            str1 = String.format("%-10s", pic1[i]);
            Color.printColor(str1, color1);

            System.out.printf("%19s","");

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

        String str;
        if(code == Gun.CODE_MISSED) {
            str = "не попал!";
        } else {
            str = String.format("противнику нанесен урон %d ед.", code);
        }

        System.out.println(currentPlayer.getName() + ", выстрел: " + str);
        return true;
    }

    private Gun[] createGuns() {
        return new Gun[]{
                new Gun("Пистолет", 10, 30, 20, 80),
                new Gun("Обрез", 40, 60, 10, 50),
                new Gun("Тротиловая шашка", 60, 85, 4, 20)
        };
    }

}
