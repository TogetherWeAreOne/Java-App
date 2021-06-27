package com.togetherweareone.cli;

import com.togetherweareone.utilities.ConsoleColors;

public class Displays {

    static void print(String text) {
        System.out.println(ConsoleColors.WHITE + text + ConsoleColors.RESET);
        printLine();
    }

    static void printTitleUnderlined(String text) {
        System.out.println(ConsoleColors.CYAN_BOLD_UNDERLINED + text + ConsoleColors.RESET);
        printParagraph();
    }

    static void printTitle(String text) {
        System.out.println(ConsoleColors.CYAN_BRIGHT + text + ConsoleColors.RESET);
        printParagraph();
    }

    static void printAlert(String text) {
        System.out.println(ConsoleColors.YELLOW_BRIGHT + text + ConsoleColors.RESET);
        printLine();
    }

    static void printInformation(String text) {
        System.out.println(ConsoleColors.GREEN_ITALIC + text + ConsoleColors.RESET);
        printParagraph();
    }

    static void printError(String text) {
        System.out.println(ConsoleColors.RED_BRIGHT + text + ConsoleColors.RESET);
        printLine();
    }

    static void printHighlight(String text, String highlight) {
        System.out.print(ConsoleColors.GREEN_ITALIC + text + ConsoleColors.RESET + "  ");
        System.out.println(ConsoleColors.YELLOW_BRIGHT + highlight + ConsoleColors.RESET);
        printLine();
    }

    static void printParagraph() {
        System.out.println();
        System.out.println();
    }

    static void printLine() {
        System.out.println();
    }
}
