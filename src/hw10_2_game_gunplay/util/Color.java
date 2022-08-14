/*
Цвета: http://surl.li/mrnv
 */
package hw10_2_game_gunplay.util;
public class Color {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    //BOLD
    public static final String ANSI_BOLD_BLACK =    "\033[1;30m";  // BLACK
    public static final String ANSI_BOLD_RED =      "\033[1;31m";    // RED
    public static final String ANSI_BOLD_GREEN =    "\033[1;32m";  // GREEN
    public static final String ANSI_BOLD_YELLOW =   "\033[1;33m"; // YELLOW
    public static final String ANSI_BOLD_BLUE =     "\033[1;34m";   // BLUE
    public static final String ANSI_BOLD_PURPLE =   "\033[1;35m"; // PURPLE
    public static final String ANSI_BOLD_CYAN =     "\033[1;36m";   // CYAN
    public static final String ANSI_BOLD_WHITE =    "\033[1;37m";  // WHITE

    //
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    //

    private Color(){

    }

    public static void printColor(String string, String color){
        setTextColor(color);
        System.out.print(string);
        resetTextColor();
    }

    public static void printlnColor(String string, String color){
        printColor(string, color);
        System.out.println();
    }

    public static void printColorYellow(String string) {
        printColor(string, ANSI_YELLOW);
    }
    public static void printlnColorYellow(String string) {
        printlnColor(string, ANSI_YELLOW);
    }

    public static void printColorBlue(String string) {
        printColor(string, ANSI_BLUE);
    }
    public static void printlnColorBlue(String string) {
        printlnColor(string, ANSI_BLUE);
    }

    public static void printColorPurple(String string) {
        printColor(string, ANSI_PURPLE);
    }
    public static void printlnColorPurple(String string) {
        printlnColor(string, ANSI_PURPLE);
    }

    public static void printColorGreen(String string) {
        printColor(string, ANSI_GREEN);
    }
    public static void printlnColorGreen(String string) {
        printlnColor(string, ANSI_GREEN);
    }

    public static void printColorRed(String string) {
        printColor(string, ANSI_RED);
    }
    public static void printlnColorRed(String string) {
        printlnColor(string, ANSI_RED);
    }

    public static void printColorBlack(String string) {
        printColor(string, ANSI_BLACK);
    }
    public static void printlnColorBlack(String string) {
        printlnColor(string, ANSI_BLACK);
    }

    public static void printColorCyan(String string) {
        printColor(string, ANSI_CYAN);
    }
    public static void printlnColorCyan(String string) {
        printlnColor(string, ANSI_CYAN);
    }

    public static void printColorWhite(String string) {
        printColor(string, ANSI_WHITE);
    }
    public static void printlnColorWhite(String string) {
        printlnColor(string, ANSI_WHITE);
    }

    public static void setTextColor(String color){
        System.out.print(color);
    }

    public static void resetTextColor(){
        System.out.print(ANSI_RESET);
    }

}
