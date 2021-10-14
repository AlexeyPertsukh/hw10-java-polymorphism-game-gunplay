package hw10_2_game_gunplay;

import java.util.Scanner;

public class Game {

    private static final String VERSION = "ver.1.4";
    private static final String COLOR_FOCUS = Player.COLOR_FOCUS;
    private static final String NAME1 = "Игрок1";
    private static final String NAME2 = "Игрок2";
    private static final String KEY_HELP = "?";
    private static final String KEY_SHOOT = "+";
    private static final String KEY_EXIT = "0";

    private static final int MODE_PLAYER = 1;
    private static final int MODE_BOT = 2;

    private Gun[] guns;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Player otherPlayer;
    private final Scanner sc;


    public Game() {
        guns = new Gun[0];
        sc = new Scanner(System.in);

        addGun("Пистолет", 10, 30, 20, 80);
        addGun("Обрез", 40, 60, 10, 50);
        addGun("Тротиловая шашка", 60,  85, 4, 20 );

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
        System.out.println("I'll be back");
        System.out.println("JAVA A01 \"ШАГ\", Запорожье 2021");
    }
    //============================================

    private void initPlayers(int mode) {
        player1 = new Player(NAME1, Player.HP_MAX, guns, PictureStorage.ASCII_PICTURE_LEFT);
        if(mode == MODE_PLAYER) {
            player2 = new Player(NAME2, Player.HP_MAX + 10, guns, PictureStorage.ASCII_PICTURE_RIGHT);    //второму- больше патронов
        }
        else {
            player2 = new Bot(NAME2, Player.HP_MAX + 10, guns, PictureStorage.ASCII_PICTURE_RIGHT);
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


    private void addGun(String name, int damageMin, int damageMax, int cartridge, int percentageHit) {
        Gun[] tmp = new Gun[guns.length + 1];
        System.arraycopy(guns, 0, tmp, 0, guns.length);
        tmp[tmp.length - 1] = new Gun(name, damageMin, damageMax, cartridge, percentageHit);
        guns = tmp;
    }

    private void printHeader() {
        Color.printlnColorRed("-------------------------------------------------------------------------------------------");
        Color.printlnColorRed("           💀💀💀💀💀   КРОВАВАЯ ПЕРЕСТРЕЛКА   💀💀💀💀💀        ");
        Color.printlnColorRed("-------------------------------------------------------------------------------------------");
    }

    private void printFooter() {
        Color.printlnColorBlue(". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .");
        String str = String.format("? справка     |  + сделать выстрел       |  1-%d сменить оружие       |   0 выход   ", currentPlayer.getNumGuns());
        Color.printlnColorBlue(str);
        Color.printlnColorBlue(". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .");
        currentPlayer.printGuns();
        System.out.println(". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .");
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

        System.out.printf("здоровье:     %-15d              %-15d     \n", player1.getHitPoint(), player2.getHitPoint());
        System.out.printf("выстрелил:    %-15d              %-15d     \n", player1.getCntShot(), player2.getCntShot());
        System.out.printf("промазал:     %-15d              %-15d     \n", player1.getCntMiss(), player2.getCntMiss());
        System.out.printf("оружие:       %-15s              %-15s     \n", player1.nameGun(), player2.nameGun());
        System.out.printf("              %-22s       %-22s               \n", player1.shortGunInfo(), player2.shortGunInfo());

        printFooter();
    }

    private void printHelp() {
        Color.setTextColor(Color.ANSI_BLUE);
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
            return Color.ANSI_RED;
        }
        return (player ==  currentPlayer) ? COLOR_FOCUS : Color.ANSI_RESET;
    }

    //команды
    private void processCmd(String cmd) {
        //цифры
        if(Util.isInteger(cmd)) {
            int num = Integer.parseInt(cmd);

            //сменить пушку
            if(num > 0 && num < currentPlayer.getNumGuns() + 1) {
                if(currentPlayer.changeGun(num)) {
                    printPage();
                }
                else {
                    System.out.println("Поменять пушку не удалось, что-то пошло не так.");
                }
                return;
            }
        }

        if(cmd.equalsIgnoreCase(KEY_HELP)) {
            printHelp();
            return;
        }

        //выстрел
        if (cmd.equalsIgnoreCase(KEY_SHOOT)) {

            currentPlayer.shot(otherPlayer);
            nextPlayer();
            Util.sleep(3000);
            printPage();
            return;
        }

        //чит - завалить противника наповал
        if(cmd.equalsIgnoreCase("#")) {
            otherPlayer.kill();
            nextPlayer();
            System.out.println(currentPlayer.getName() + " сражён наповал неизвестным оружием");
            Util.sleep(2000);
            printPage();
            return;
        }
        System.out.println("Неизвестная команда");
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

}
