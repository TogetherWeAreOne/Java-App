package com.togetherweareone.cli;

import com.togetherweareone.utilities.ConsoleColors;

import java.io.Console;

public class Cli {
    public Cli(){
        start();
    }

    private void start() {
        printTitleUnderlined("Test titre souligné");
        clearConsole();
        printTitle("Test titre");
        clearConsole();
        print("Test de Body");
        clearConsole();
        printInformation("Test Informations");
        clearConsole();
        printAlert("Test Alert");
        clearConsole();
        printError("Test Error");
        sleep();
    }

    private void clearConsole(){
        //System.out.print("\033[H\033[2J");
        //System.out.flush();
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                Runtime.getRuntime().exec("cls");
            } else {
                Runtime.getRuntime().exec("clear");
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private void sleep(){
        new Thread(() -> {
            System.out.println ("Before sleep...");
            try {
                Thread.sleep (500);
            } catch (InterruptedException e) {
                e.printStackTrace ();
            }
            System.out.println ("After sleep...");
        }).start();
    }

    private void print(String text){
        System.out.println(ConsoleColors.WHITE + text + ConsoleColors.RESET);
    }

    private void printTitleUnderlined(String text){
        System.out.println(ConsoleColors.CYAN_BOLD_UNDERLINED + text + ConsoleColors.RESET);
    }

    private void printTitle(String text){
        System.out.println(ConsoleColors.CYAN_BRIGHT + text + ConsoleColors.RESET);
    }

    private void printAlert(String text){
        System.out.println(ConsoleColors.YELLOW_BRIGHT + text + ConsoleColors.RESET);
    }

    private void printInformation(String text){
        System.out.println(ConsoleColors.GREEN_ITALIC + text + ConsoleColors.RESET);
    }

    private void printError(String text){
        System.out.println(ConsoleColors.RED_BRIGHT + text + ConsoleColors.RESET);
    }
}

