package com.togetherweareone.cli;

import com.togetherweareone.api.ApiClient;
import com.togetherweareone.models.*;
import com.togetherweareone.request.authRequest.LoginRequest;
import com.togetherweareone.request.checklistRequest.CreateChecklistRequest;
import com.togetherweareone.request.checklistRequest.GetAllOptionsRequest;
import com.togetherweareone.request.columnRequest.CreateColumnRequest;
import com.togetherweareone.request.columnRequest.GetAllTasksRequest;
import com.togetherweareone.request.optionRequest.CreateOptionRequest;
import com.togetherweareone.request.projectRequest.CreateProjectRequest;
import com.togetherweareone.request.projectRequest.GetAllColumnsRequest;
import com.togetherweareone.request.taskRequest.CreateTaskRequest;
import com.togetherweareone.request.taskRequest.GetAllChecklistsRequest;
import com.togetherweareone.services.*;
import com.togetherweareone.utilities.ConsoleColors;
import reactor.core.publisher.Mono;

import java.util.Scanner;

public class Cli {

    AuthService authService;
    ApiClient apiClient;
    User user;
    boolean loginError;
    Project[] projects;
    Project chosenProject;
    Column[] columns;
    Column chosenColumn;
    Task[] tasks;
    Task chosenTask;
    Checklist[] checklists;
    Checklist chosenChecklist;
    Option[] options;

    public Cli() {
        this.authService = new AuthService();
        this.apiClient = new ApiClient();
        this.user = null;
        do {
            printLogin();
        }
        while (loginError);

        printChooseProject();

        end();
    }

    /*
    Fonctions d'écriture de la console communes
     */

    void end() {
        Mono<Void> logoutRequest = authService.logout(apiClient.getWebClient());
        logoutRequest.block();
        clearConsole();
        if (!askQuit()) {
            this.user = null;
            clearConsole();
            printLogin();
        }
    }

    void printHeader() {
        printHugeTitle();
        printPresentation();
        if (loginError) {
            Displays.printAlert("Votre identifiant ou mot de passe est incorrect !");
            Displays.printLine();
        } else if (user != null) {
            Displays.printHighlight("Votre identifiant : ", this.user.id);
            Displays.printLine();
        }

        if (chosenProject != null) {
            Displays.printHighlight("Projet sélectionné : ", this.chosenProject.title);
            if (chosenColumn != null) {
                Displays.printHighlight("Colonne sélectionnée : ", this.chosenColumn.title);
            }
            if (chosenTask != null) {
                Displays.printHighlight("Tâche sélectionnée : ", this.chosenTask.title);
            }
            Displays.printLine();
        }
    }

    void printHugeTitle() {
        Displays.printParagraph();
        Displays.printError("-------------------------------------------------------------------------------------------------");
        Displays.printError(" _____                 _   _                                                                  \n" +
                "/__   \\___   __ _  ___| |_| |__   ___ _ __  __      _____    __ _ _ __ ___    ___  _ __   ___ \n" +
                "  / /\\/ _ \\ / _` |/ _ \\ __| '_ \\ / _ \\ '__| \\ \\ /\\ / / _ \\  / _` | '__/ _ \\  / _ \\| '_ \\ / _ \\\n" +
                " / / | (_) | (_| |  __/ |_| | | |  __/ |     \\ V  V /  __/ | (_| | | |  __/ | (_) | | | |  __/\n" +
                " \\/   \\___/ \\__, |\\___|\\__|_| |_|\\___|_|      \\_/\\_/ \\___|  \\__,_|_|  \\___|  \\___/|_| |_|\\___|\n" +
                "            |___/                                                                             ");
        Displays.printError("-------------------------------------------------------------------------------------------------");
        Displays.printParagraph();
    }

    void printPresentation() {
        Displays.printInformation("Bienvenue sur Together We Are One ! ( CLI version ) © 2021 TWAO.  All rights reserved.\n" +
                "Vous pouvez utilisez les différentes commandes pour naviguer dans l'arborescence !\n" +
                "N'oubliez que vous pouvez toujours exécuter ce programme en version graphique ou bien aller voir l'interface web sur notre site : *insérer le lien*");
    }

    void printLogin() {
        clearConsole();

        String email = ask("Veuillez entrer votre email :");
        String password = ask("Veuillez entrer votre mot de passe :");

        LoginRequest request = new LoginRequest(email, password);
        Mono<User> user = authService.login(apiClient.getWebClient(), request);

        user
                .doOnSuccess(this::handleUserLogin)
                .doOnError(this::handleUserLoginError)
                .onErrorReturn(new User())
                .block();
    }

    void printChooseProject() {
        int choice;
        do {
            chosenProject = null;
            clearConsole();
            Displays.printInformation("Que voulez-vous faire ?");
            Displays.printInformation("1 : Créer un nouveau projet\n" +
                    "2 : Accéder à un projet existant\n" +
                    "0 : Se déconnecter"
            );

            choice = askMultipleChoices(3);

            switch (choice) {
                case 1 -> createProject();
                case 2 -> useProject();
            }

        } while (choice != 0);
    }

    void createProject() {
        clearConsole();
        Displays.printTitle("Création d'un nouveau projet");
        ProjectService projectService = new ProjectService();
        String title = ask("Titre :");
        String description = ask("Description :");
        CreateProjectRequest createProjectRequest = new CreateProjectRequest(title, description);
        Mono<Project> project = projectService.createProject(apiClient.getWebClient(), createProjectRequest);
        project.onErrorReturn(new Project())
                .block();
    }

    void useProject() {
        clearConsole();
        Displays.printTitle("Choix d'un projet");
        ProjectService projectService = new ProjectService();
        Mono<Project[]> allProjects = projectService.getAllProjects(apiClient.getWebClient());
        allProjects.doOnSuccess(p -> this.projects = p)
                .onErrorReturn(new Project[]{})
                .block();

        if (this.projects != null) {
            StringBuilder askProjects = new StringBuilder();
            for (int i = 0; i < this.projects.length; i++) {
                Project p = this.projects[i];
                askProjects.append(i).append(": ").append(p.id).append(" / ").append(p.title).append("\n");
            }

            Displays.printInformation(askProjects.toString());

            Integer choice = askMultipleChoices(this.projects.length);
            if (choice != null) {
                chosenProject = this.projects[choice];
                printChooseColumns();
            }
        } else {
            Displays.printInformation("Aucun projet n'est disponible !");
            waitInput();
        }
    }

    void printChooseColumns() {
        int choice;
        do {
            chosenColumn = null;
            clearConsole();
            Displays.printInformation("Que voulez-vous faire ?");
            Displays.printInformation("1 : Créer une nouvelle colonne\n" +
                    "2 : Accéder à une colonne existante\n" +
                    "0 : Retourner au choix de projet"
            );

            choice = askMultipleChoices(3);

            switch (choice) {
                case 1 -> createColumn();
                case 2 -> useColumn();
            }

        } while (choice != 0);
    }

    void printChooseTask() {
        int choice;
        do {
            chosenTask = null;
            clearConsole();
            Displays.printInformation("Que voulez-vous faire ?");
            Displays.printInformation("1 : Créer une nouvelle tâche\n" +
                    "2 : Accéder à une tâche existante\n" +
                    "0 : Retourner au choix de colonne"
            );

            choice = askMultipleChoices(3);

            switch (choice) {
                case 1 -> createTask();
                case 2 -> useTask();
            }

        } while (choice != 0);
    }

    private void createTask() {
        clearConsole();
        Displays.printTitle("Création d'une nouvelle tâche");
        TaskService taskService = new TaskService();
        String title = ask("Titre :");
        String description = ask("Description :");
        Displays.printInformation("Niveau de priorité :\n" +
                "0 : Bas\n" +
                "1 : Moyen\n" +
                "2 : Haut"
        );
        int choice = askMultipleChoices(3);
        CreateTaskRequest createTaskRequest = new CreateTaskRequest(this.chosenColumn.id, title, description, (choice == 0 ? "LOW" : (choice == 1 ? "MEDIUM" : "HIGH")));
        Mono<Task> task = taskService.createTask(apiClient.getWebClient(), createTaskRequest);
        task.onErrorReturn(new Task())
                .block();
    }

    private void useTask() {
        clearConsole();
        Displays.printTitle("Choix d'une tâche");
        ColumnService columnService = new ColumnService();
        GetAllTasksRequest getAllTasksRequest = new GetAllTasksRequest(this.chosenColumn.id);
        Mono<Task[]> allTasks = columnService.getAllTasks(apiClient.getWebClient(), getAllTasksRequest);

        allTasks.doOnSuccess(t -> this.tasks = t)
                .onErrorReturn(new Task[]{})
                .block();

        if (this.tasks != null) {
            StringBuilder askTasks = new StringBuilder();
            for (int i = 0; i < this.tasks.length; i++) {
                Task t = this.tasks[i];
                askTasks.append(i).append(": ").append(t.id).append(" / ").append(t.title).append("\n");
            }

            Displays.printInformation(askTasks.toString());

            Integer choice = askMultipleChoices(this.tasks.length);

            if (choice != null) {
                chosenTask = this.tasks[choice];
                printChooseChecklist();
            }
        } else {
            Displays.printInformation("Aucune tâche n'est disponible !");
            waitInput();
        }
    }

    void printChooseChecklist() {
        int choice;
        do {
            chosenChecklist = null;
            clearConsole();
            Displays.printInformation("Que voulez-vous faire ?");
            Displays.printInformation("1 : Créer une nouvelle checklist\n" +
                    "2 : Accéder à une checklist existante\n" +
                    "0 : Retourner au choix de tâche"
            );

            choice = askMultipleChoices(3);

            switch (choice) {
                case 1 -> createChecklist();
                case 2 -> useChecklist();
            }

        } while (choice != 0);
    }

    void createChecklist() {
        clearConsole();
        Displays.printTitle("Création d'une nouvelle checklist");
        ChecklistService checklistService = new ChecklistService();
        String title = ask("Titre :");
        CreateChecklistRequest createChecklistRequest = new CreateChecklistRequest(title, this.chosenTask.id);
        Mono<Checklist> checklist = checklistService.createChecklist(apiClient.getWebClient(), createChecklistRequest);
        checklist.onErrorReturn(new Checklist())
                .block();
    }

    void useChecklist() {
        clearConsole();
        Displays.printTitle("Choix d'une checklist");
        TaskService taskService = new TaskService();
        GetAllChecklistsRequest getAllChecklistsRequest = new GetAllChecklistsRequest(this.chosenTask.id);
        Mono<Checklist[]> allChecklists = taskService.getAllChecklists(apiClient.getWebClient(), getAllChecklistsRequest);

        allChecklists.doOnSuccess(c -> this.checklists = c)
                .onErrorReturn(new Checklist[]{})
                .block();

        if (this.checklists != null) {
            StringBuilder askChecklists = new StringBuilder();
            for (int i = 0; i < this.checklists.length; i++) {
                Checklist c = this.checklists[i];
                askChecklists.append(i).append(": ").append(c.id).append(" / ").append(c.title).append("\n");
            }

            Displays.printInformation(askChecklists.toString());

            Integer choice = askMultipleChoices(this.checklists.length);

            if (choice != null) {
                chosenChecklist = this.checklists[choice];
                printChooseOption();
            }
        } else {
            Displays.printInformation("Aucune checklist n'est disponible !");
            waitInput();
        }
    }

    void printChooseOption() {
        int choice;
        do {
            clearConsole();
            Displays.printInformation("Que voulez-vous faire ?");
            Displays.printInformation("1 : Créer une nouvelle option\n" +
                    "2 : Voir la liste des options existantes\n" +
                    "0 : Retourner au choix de checklist"
            );

            choice = askMultipleChoices(3);

            switch (choice) {
                case 1 -> createOption();
                case 2 -> seeOptions();
            }

        } while (choice != 0);
    }

    void createOption() {
        clearConsole();
        Displays.printTitle("Création d'une nouvelle option");
        OptionService optionService = new OptionService();
        String title = ask("Titre :");
        CreateOptionRequest createOptionRequest = new CreateOptionRequest(title, this.chosenChecklist.id);
        Mono<Option> option = optionService.createOption(apiClient.getWebClient(), createOptionRequest);
        option.onErrorReturn(new Option())
                .block();
    }

    void seeOptions() {
        clearConsole();
        Displays.printTitle("Affichage des options");
        ChecklistService checklistService = new ChecklistService();
        GetAllOptionsRequest getAllOptionsRequest = new GetAllOptionsRequest(this.chosenChecklist.id);
        Mono<Option[]> allOptions = checklistService.getAllOptions(apiClient.getWebClient(), getAllOptionsRequest);

        allOptions.doOnSuccess(o -> this.options = o)
                .onErrorReturn(new Option[]{})
                .block();

        if (this.options != null) {
            StringBuilder askOptions = new StringBuilder();
            for (int i = 0; i < this.options.length; i++) {
                Option c = this.options[i];
                askOptions.append(i).append(": ").append(c.id).append(" / ").append(c.title).append("\n");
            }

            Displays.printInformation(askOptions.toString());
        } else Displays.printInformation("Aucune checklist n'est disponible !");

        waitInput();
    }

    void createColumn() {
        clearConsole();
        Displays.printTitle("Création d'une nouvelle colonne");
        ColumnService columnService = new ColumnService();
        String title = ask("Titre :");
        CreateColumnRequest createColumnRequest = new CreateColumnRequest(title, this.chosenProject.id);
        Mono<Column> column = columnService.createColumn(apiClient.getWebClient(), createColumnRequest);
        column.onErrorReturn(new Column())
                .block();
    }

    void useColumn() {
        clearConsole();
        Displays.printTitle("Choix d'une colonne");
        ProjectService projectService = new ProjectService();
        GetAllColumnsRequest getAllColumnsRequest = new GetAllColumnsRequest(this.chosenProject.id);
        Mono<Column[]> allColumns = projectService.getAllColumns(apiClient.getWebClient(), getAllColumnsRequest);

        allColumns.doOnSuccess(c -> this.columns = c)
                .onErrorReturn(new Column[]{})
                .block();

        if (this.columns != null) {
            StringBuilder askColumns = new StringBuilder();
            for (int i = 0; i < this.columns.length; i++) {
                Column c = this.columns[i];
                askColumns.append(i).append(": ").append(c.id).append(" / ").append(c.title).append("\n");
            }

            Displays.printInformation(askColumns.toString());

            Integer choice = askMultipleChoices(this.columns.length);

            if (choice != null) {
                chosenColumn = this.columns[choice];
                printChooseTask();
            }
        } else {
            Displays.printInformation("Aucune colonne n'est disponible !");
            waitInput();
        }
    }

    void handleUserLogin(User user) {
        this.loginError = false;
        this.user = user;
    }

    void handleUserLoginError(Throwable throwable) {
        this.loginError = true;
    }

    boolean askYesNo(String text) {

        String answer;

        do {
            answer = ask(text + " (Y/N)");
        } while (!answer.equals("Y") && !answer.equals("N"));

        return answer.equals("Y");
    }

    boolean askQuit() {

        String answer;

        do {
            answer = askImportant("Quitter le programme ? (Y/N)");
        } while (!answer.equals("Y") && !answer.equals("N") && !answer.equals("y") && !answer.equals("n"));

        return (answer.equals("Y") || answer.equals("y"));
    }

    Integer askMultipleChoices(int nbOfChoices) {
        String choice;
        int choiceValue;

        if (nbOfChoices > 1) {
            do {
                choice = ask("Votre choix :");
                choiceValue = Integer.parseInt(choice);
            } while (choiceValue > nbOfChoices - 1 || choiceValue < 0);
        } else if (nbOfChoices == 1) return 0;
        else return 0;

        return choiceValue;
    }

    /*
    Fonctions usuelles d'écriture de la console
     */

    void waitInput(){
        ask("Appuyez sur Entrée pour continuer ...");
    }

    String ask(String text) {
        return askDefault(text, false);
    }

    String askImportant(String text) {
        return askDefault(text, true);
    }

    String askDefault(String text, boolean important) {
        System.out.print((important ? ConsoleColors.YELLOW_ITALIC : ConsoleColors.GREEN_ITALIC) + text + ConsoleColors.RESET + "  ");
        Scanner sc = new Scanner(System.in);
        String entry = sc.nextLine();
        Displays.printLine();
        return entry;
    }

    void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        printHeader();
    }
}

