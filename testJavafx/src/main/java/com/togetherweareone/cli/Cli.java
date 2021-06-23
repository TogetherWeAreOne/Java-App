package com.togetherweareone.cli;

import com.togetherweareone.utilities.ConsoleColors;

public class Cli {
    public Cli(){
        start();
    }

    private void start() {

        printHugeTitle();

        printInformation("Bienvenue sur Together We Are One ! ( CLI version )\n" +
                "Vous pouvez utilisez les différentes commandes pour naviguer dans l'arborescence !\n" +
                "N'oubliez que vous pouvez toujours éxécuter ce programme en version graphique ou bien aller voir l'interface web sur notre site : *insérer le lien*");

        print("Voici une liste de blablabla");
    }

    private void printHugeTitle(){
        paragraph();
        print("-------------------------------------------------------------------------------------------------");
        print(" _____                 _   _                                                                  \n" +
                "/__   \\___   __ _  ___| |_| |__   ___ _ __  __      _____    __ _ _ __ ___    ___  _ __   ___ \n" +
                "  / /\\/ _ \\ / _` |/ _ \\ __| '_ \\ / _ \\ '__| \\ \\ /\\ / / _ \\  / _` | '__/ _ \\  / _ \\| '_ \\ / _ \\\n" +
                " / / | (_) | (_| |  __/ |_| | | |  __/ |     \\ V  V /  __/ | (_| | | |  __/ | (_) | | | |  __/\n" +
                " \\/   \\___/ \\__, |\\___|\\__|_| |_|\\___|_|      \\_/\\_/ \\___|  \\__,_|_|  \\___|  \\___/|_| |_|\\___|\n" +
                "            |___/                                                                             ");
        print("-------------------------------------------------------------------------------------------------");
        paragraph();
    }

    private void print(String text){
        System.out.println(ConsoleColors.WHITE + text + ConsoleColors.RESET);
        paragraph();
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
        paragraph();
    }

    private void printInformation(String text){
        System.out.println(ConsoleColors.GREEN_ITALIC + text + ConsoleColors.RESET);
        paragraph();
    }

    private void printError(String text){
        System.out.println(ConsoleColors.RED_BRIGHT + text + ConsoleColors.RESET);
        paragraph();
    }

    private void paragraph(){
        System.out.println();
        System.out.println();
    }
}

