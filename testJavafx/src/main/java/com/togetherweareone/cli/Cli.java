package com.togetherweareone.cli;

import com.togetherweareone.utilities.ConsoleColors;

import java.util.Scanner;

public class Cli {
    public Cli(){
        start();
    }

    private void start() {

        printHugeTitle();
        printPresentation();
        printLogin();

    }

    /*
    Fonctions d'écriture de la console communes
     */
    private void printHugeTitle(){
        paragraph();
        printError("-------------------------------------------------------------------------------------------------");
        printError(" _____                 _   _                                                                  \n" +
                "/__   \\___   __ _  ___| |_| |__   ___ _ __  __      _____    __ _ _ __ ___    ___  _ __   ___ \n" +
                "  / /\\/ _ \\ / _` |/ _ \\ __| '_ \\ / _ \\ '__| \\ \\ /\\ / / _ \\  / _` | '__/ _ \\  / _ \\| '_ \\ / _ \\\n" +
                " / / | (_) | (_| |  __/ |_| | | |  __/ |     \\ V  V /  __/ | (_| | | |  __/ | (_) | | | |  __/\n" +
                " \\/   \\___/ \\__, |\\___|\\__|_| |_|\\___|_|      \\_/\\_/ \\___|  \\__,_|_|  \\___|  \\___/|_| |_|\\___|\n" +
                "            |___/                                                                             ");
        printError("-------------------------------------------------------------------------------------------------");
        paragraph();
    }

    private void printPresentation(){
        printInformation("Bienvenue sur Together We Are One ! ( CLI version ) © 2021 TWAO.  All rights reserved.\n" +
                "Vous pouvez utilisez les différentes commandes pour naviguer dans l'arborescence !\n" +
                "N'oubliez que vous pouvez toujours exécuter ce programme en version graphique ou bien aller voir l'interface web sur notre site : *insérer le lien*");
    }

    private void printLogin(){
        String entry = ask("Veuillez entrer vos informations de connexion :");
        printHighlight("Vous avez entré:", entry);
    }


    /*
    Fonctions usuelles d'écriture de la console
     */
    private void print(String text){
        System.out.println(ConsoleColors.WHITE + text + ConsoleColors.RESET);
        line();
    }

    private void printTitleUnderlined(String text){
        System.out.println(ConsoleColors.CYAN_BOLD_UNDERLINED + text + ConsoleColors.RESET);
        paragraph();
    }


    private void printTitle(String text){
        System.out.println(ConsoleColors.CYAN_BRIGHT + text + ConsoleColors.RESET);
        paragraph();
    }

    private void printAlert(String text){
        System.out.println(ConsoleColors.YELLOW_BRIGHT + text + ConsoleColors.RESET);
        line();
    }

    private void printInformation(String text){
        System.out.println(ConsoleColors.GREEN_ITALIC + text + ConsoleColors.RESET);
        paragraph();
    }

    private void printError(String text){
        System.out.println(ConsoleColors.RED_BRIGHT + text + ConsoleColors.RESET);
        line();
    }

    private void printHighlight(String text, String highlight){
        System.out.print(ConsoleColors.GREEN_ITALIC + text + ConsoleColors.RESET + "  ");
        System.out.println(ConsoleColors.YELLOW_BRIGHT + highlight + ConsoleColors.RESET);
        line();
    }

    private String ask(String text){
        System.out.print(ConsoleColors.GREEN_ITALIC + text + ConsoleColors.RESET + "  ");
        Scanner sc = new Scanner(System.in);
        String entry = sc.nextLine();
        line();
        return entry;
    }

    private void paragraph(){
        System.out.println();
        System.out.println();
    }

    private void line(){
        System.out.println();
    }
}

