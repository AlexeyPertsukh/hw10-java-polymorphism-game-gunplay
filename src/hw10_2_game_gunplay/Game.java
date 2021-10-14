package hw10_2_game_gunplay;

import java.util.Scanner;

public class Game {

    private static final String VERSION = "ver.1.3";
    private static final String COLOR_FOCUS = Player.COLOR_FOCUS;

    private Gun[] guns;
    private Player[] players;
    private Player   player;
    private final Scanner sc;
    private int numPlayer;
    private boolean exit;


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
        int mode = 0;

        do {
            System.out.print("Введите режим игры (1 - с человеком, 2 - с ботом): ");
            String str = sc.next();
            if(Util.isInteger(str)) {
                mode = Integer.parseInt(str);
            }

        } while(mode < 1 || mode > 2);

        players = new Player[2];
        players[0] = new Player("Игрок1", Player.HP_MAX, guns, Player.ASCII_PICTURE_LEFT);
        if(mode == 1) {
            players[1] = new Player("Игрок2", Player.HP_MAX + 20, guns, Player.ASCII_PICTURE_RIGHT);    //второму- больше патронов
        }
        else {
            players[1] = new Bot("Игрок2", Player.HP_MAX + 20, guns, Player.ASCII_PICTURE_RIGHT);
        }

        firstPlayer();  //фокус на первого игрока

        Color.printlnColorRed(VERSION);
        printPage();

        do {
            Color.printColorYellow(player.getName() + ", ваш ход. Введите команду:  ");
            cmd = player.nextCmd(sc);
            processCmd(cmd);

            if(players[0].isDead()) {
                Color.printlnColorRed(players[0].getName() + " был трагически застрелен 💀💀💀💀💀");
                Color.printlnColorGreen("Победил " + players[1].getName() + "!");
                exit = true;
            }
            else if(players[1].isDead()) {
                Color.printlnColorRed(players[1].getName() + " был трагически застрелен 💀💀💀💀💀");
                Color.printlnColorGreen("Победил " + players[0].getName() + "!");
                exit = true;
            }


        }while(!exit);
        //конец игры
        System.out.println("I'll be back");
        System.out.println("JAVA A01 \"ШАГ\", Запорожье 2021");
    }
    //============================================

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
        String str = String.format("? справка     |  + сделать выстрел       |  1-%d сменить оружие       |   0 выход   ", player.getNumGuns());
        Color.printlnColorBlue(str);
        Color.printlnColorBlue(". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .");
        player.printGuns();
        System.out.println(". . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . .");
    }


    private void printPage() {
        printHeader();

        String str1;
        String str2;
        String color1;
        String color2;

        color1 = getColorPlayer(0);
        color2 = getColorPlayer(1);
        Color.printColor(String.format("%-12s   %-25s", "", players[0].getName()), color1);
        Color.printColor(String.format("  %-25s \n",  players[1].getName()), color2 );

        //распечатываем человечков
        int i = 0;
        do {
            str1 = players[0].getStrPictLine(i);
            str2 = players[1].getStrPictLine(i);

            Color.printColor(String.format("               %-15s", str1), color1);
            Color.printColor(String.format("            %-15s \n", str2), color2);

            i++;
        } while(str1.compareToIgnoreCase("") != 0);

        //дополнительная информация по игрокам
        Color.printColor(String.format("              %-15s", players[0].getStrHpLine()), color1);
        Color.printColor(String.format("              %-15s     \n", players[1].getStrHpLine()), color2);

        System.out.printf("здоровье:     %-15d              %-15d     \n", players[0].getHitPoint(), players[1].getHitPoint());
        System.out.printf("выстрелил:    %-15d              %-15d     \n", players[0].getCntShot(), players[1].getCntShot());
        System.out.printf("промазал:     %-15d              %-15d     \n", players[0].getCntMiss(), players[1].getCntMiss());
        System.out.printf("оружие:       %-15s              %-15s     \n", players[0].nameGun(), players[1].nameGun());
        System.out.printf("              %-22s       %-22s               \n", players[0].shortGunInfo(), players[1].shortGunInfo());

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

    private String getColorPlayer(int num) {
        if(num == numPlayer && players[numPlayer].isDead()) {
            return Color.ANSI_RED;
        }
        return (num ==  numPlayer) ? COLOR_FOCUS : Color.ANSI_RESET;
    }

    //команды
    private void processCmd(String cmd) {
        //цифры
        if(Util.isInteger(cmd)) {
            int num = Integer.parseInt(cmd);
            //выход
            if(num == 0) {
                exit = true;
                return;
            }
            //сменить пушку
            if(num > 0 && num < player.getNumGuns() + 1) {
                if(player.changeGun(num)) {
                    printPage();
                }
                else {
                    System.out.println("Поменять пушку не удалось, что-то пошло не так.");
                }
                return;
            }
        }

        if(cmd.compareToIgnoreCase("?") == 0) {
            printHelp();
            return;
        }


        //выстрел
        if(cmd.compareToIgnoreCase("+") == 0) {

            int damage = player.shot();
            if (damage != Gun.NUM_NO_CARTRIDGES) { //делаем выстрел
                nextPlayer();
                player.inputDamage(damage);
                Util.sleep(3000);
                printPage();
            }
            return;
        }

        //чит - завалить противника наповал
        if(cmd.compareToIgnoreCase("#") == 0) {
            nextPlayer();
            player.kill();
            System.out.println(player.getName() + " сражён наповал неизвестным оружием");
            Util.sleep(2000);
            printPage();
            return;
        }
        System.out.println("Неизвестная команда");
    }

    //меняем игрока
    private void nextPlayer() {
        numPlayer++;
        if(numPlayer > 1) {
            numPlayer = 0;
        }
        player = players[numPlayer];
    }

    //устанавливаем первого игрока
    private void firstPlayer() {
        numPlayer = 0;
        player = players[numPlayer];
    }

}
