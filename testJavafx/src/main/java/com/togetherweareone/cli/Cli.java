package com.togetherweareone.cli;

import com.togetherweareone.api.ApiClient;
import com.togetherweareone.models.*;
import com.togetherweareone.request.authRequest.LoginRequest;
import com.togetherweareone.request.authRequest.SignUpRequest;
import com.togetherweareone.request.checklistRequest.CreateChecklistRequest;
import com.togetherweareone.request.checklistRequest.DeleteChecklistRequest;
import com.togetherweareone.request.checklistRequest.GetAllOptionsRequest;
import com.togetherweareone.request.checklistRequest.UpdateChecklistRequest;
import com.togetherweareone.request.columnRequest.CreateColumnRequest;
import com.togetherweareone.request.columnRequest.DeleteColumnRequest;
import com.togetherweareone.request.columnRequest.GetAllTasksRequest;
import com.togetherweareone.request.columnRequest.UpdateColumnRequest;
import com.togetherweareone.request.optionRequest.CreateOptionRequest;
import com.togetherweareone.request.optionRequest.DeleteOptionRequest;
import com.togetherweareone.request.optionRequest.UpdateOptionRequest;
import com.togetherweareone.request.projectRequest.*;
import com.togetherweareone.request.taskRequest.CreateTaskRequest;
import com.togetherweareone.request.taskRequest.DeleteTaskRequest;
import com.togetherweareone.request.taskRequest.GetAllChecklistsRequest;
import com.togetherweareone.request.taskRequest.UpdateTaskRequest;
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
    boolean deleted;
    boolean quit;

    public Cli() {
        this.authService = new AuthService();
        this.apiClient = new ApiClient();
        this.projects = null;
        this.columns = null;
        this.tasks = null;
        this.checklists = null;
        this.options = null;
        this.deleted = false;
        this.quit = true;

        do {
            this.user = null;

            do {
                printAccount();
            } while (loginError);

            printChooseProject();

            end();
        } while (!quit);
    }

    /*
    Fonctions d'écriture de la console communes
     */
    void printAccount() {
        clearConsole();

        Displays.printInformation("Que voulez-vous faire ?");
        Displays.printInformation("0 : S'inscrire\n" +
                "1 : Se connecter"
        );

        int choice = askMultipleChoices(2);

        if (choice == 0) printSignUp();
        else printLogin();
    }

    void printSignUp() {
        clearConsole();

        Displays.printTitle("Inscription");

        String email = ask("Veuillez entrer votre email :");
        String password = ask("Veuillez entrer votre mot de passe :");
        String firstname = ask("Veuillez entrer votre prénom :");
        String lastname = ask("Veuillez entrer votre nom de famille :");
        String pseudo = ask("Veuillez entrer votre pseudo :");

        SignUpRequest signUpRequest = new SignUpRequest(email, password, firstname, lastname, pseudo);
        Mono<User> signUpUser = authService.signUp(apiClient.getWebClient(), signUpRequest);

        signUpUser
                .doOnSuccess(this::handleUserLogin)
                .doOnError(this::handleUserLoginError)
                .onErrorReturn(new User())
                .block();

        LoginRequest loginRequest = new LoginRequest(email, password);
        Mono<User> loginUser = authService.login(apiClient.getWebClient(), loginRequest);

        loginUser
                .doOnSuccess(this::handleUserLogin)
                .doOnError(this::handleUserLoginError)
                .onErrorReturn(new User())
                .block();
    }

    public void end() {
        Mono<Void> logoutRequest = authService.logout(apiClient.getWebClient());
        logoutRequest.block();
        clearConsole();
        this.quit = askQuit();
    }

    void printHeader() {
        printHugeTitle();
        printPresentation();
        if (this.loginError) {
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

            if (chosenChecklist != null) {
                Displays.printHighlight("Checklist sélectionnée : ", this.chosenChecklist.title);
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

        Displays.printTitle("Connexion");

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
        GetAllProjectsRequest getAllProjectsRequest = new GetAllProjectsRequest(this.user.id);
        Mono<Project[]> allProjects = projectService.getAllProjectsForUser(apiClient.getWebClient(), getAllProjectsRequest);
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
        }

        if (this.projects == null || this.projects.length == 0) {
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
            Displays.printInformation("1 : Créer une nouvelle colonne" +
                    "\n" +
                    "2 : Accéder à une colonne existante" +
                    "\n" +
                    "\n" +
                    "3 : Modifier le projet" +
                    "\n" +
                    "4 : Supprimer le projet" +
                    "\n" +
                    "\n" +
                    "0 : Retourner au choix de projet"
            );

            choice = askMultipleChoices(5);

            switch (choice) {
                case 1 -> createColumn();
                case 2 -> useColumn();
                case 3 -> modifyProject();
                case 4 -> deleteProject();
            }
        } while (choice != 0 && !deleted);

        if (deleted)
            deleted = false;
    }

    void printChooseTask() {
        int choice;
        do {
            chosenTask = null;
            clearConsole();
            Displays.printInformation("Que voulez-vous faire ?");
            Displays.printInformation("1 : Créer une nouvelle tâche" +
                    "\n" +
                    "2 : Accéder à une tâche existante" +
                    "\n" +
                    "\n" +
                    "3 : Modifier la colonne" +
                    "\n" +
                    "4 : Supprimer la colonne" +
                    "\n" +
                    "\n" +
                    "0 : Retourner au choix de colonne"
            );

            choice = askMultipleChoices(5);

            switch (choice) {
                case 1 -> createTask();
                case 2 -> useTask();
                case 3 -> modifyColumn();
                case 4 -> deleteColumn();
            }

        } while (choice != 0 && !deleted);

        if (deleted)
            deleted = false;
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
        }

        if (this.tasks == null || this.tasks.length == 0) {
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
            Displays.printInformation("1 : Créer une nouvelle checklist" +
                    "\n" +
                    "2 : Accéder à une checklist existante" +
                    "\n" +
                    "\n" +
                    "3 : Modifier la tâche" +
                    "\n" +
                    "4 : Supprimer la tâche" +
                    "\n" +
                    "\n" +
                    "0 : Retourner au choix de tâche"
            );

            choice = askMultipleChoices(5);

            switch (choice) {
                case 1 -> createChecklist();
                case 2 -> useChecklist();
                case 3 -> modifyTask();
                case 4 -> deleteTask();
            }

        } while (choice != 0 && !deleted);

        if (deleted)
            deleted = false;
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
        }

        if (this.checklists == null || this.checklists.length == 0) {
            Displays.printInformation("Aucune checklist n'est disponible !");
            waitInput();
        }
    }

    void printChooseOption() {
        int choice;
        do {
            clearConsole();
            Displays.printInformation("Que voulez-vous faire ?");
            Displays.printInformation("1 : Créer une nouvelle option" +
                    "\n" +
                    "2 : Voir la liste des options existantes" +
                    "\n" +
                    "3 : Modifier une option" +
                    "\n" +
                    "4 : Supprimer une option" +
                    "\n" +
                    "\n" +
                    "5 : Modifier la checklist" +
                    "\n" +
                    "6 : Supprimer la checklist" +
                    "\n" +
                    "\n" +
                    "0 : Retourner au choix de checklist"
            );

            choice = askMultipleChoices(7);

            switch (choice) {
                case 1 -> createOption();
                case 2 -> seeOptions();
                case 3, 4 -> useOption(choice == 3);
                case 5 -> modifyChecklist();
                case 6 -> deleteChecklist();
            }

        } while (choice != 0 && !this.deleted);

        if (this.deleted)
            deleted = false;
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
        } else Displays.printInformation("Aucune option n'est disponible !");

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
        }

        if (this.columns == null || this.columns.length == 0) {
            Displays.printInformation("Aucune colonne n'est disponible !");
            waitInput();
        }
    }

    void useOption(boolean modification) {
        clearConsole();

        Option chosenOption;

        Displays.printTitle("Choix d'une option");
        ChecklistService checklistService = new ChecklistService();
        GetAllOptionsRequest getAllOptionsRequest = new GetAllOptionsRequest(this.chosenChecklist.id);
        Mono<Option[]> allOptions = checklistService.getAllOptions(apiClient.getWebClient(), getAllOptionsRequest);

        allOptions.doOnSuccess(o -> this.options = o)
                .onErrorReturn(new Option[]{})
                .block();

        if (this.options != null) {
            StringBuilder askOptions = new StringBuilder();

            for (int i = 0; i < this.options.length; i++) {
                Option o = this.options[i];
                askOptions.append(i).append(": ").append(o.id).append(" / ").append(o.title).append("\n");
            }

            Displays.printInformation(askOptions.toString());

            Integer choice = askMultipleChoices(this.options.length);

            if (choice != null) {
                chosenOption = this.options[choice];
                if (modification)
                    modifyOption(chosenOption);
                else
                    deleteOption(chosenOption);
            }
        }

        if (this.options == null || this.options.length == 0) {
            Displays.printInformation("Aucune option n'est disponible !");
            waitInput();
        }
    }

    void modifyProject() {
        clearConsole();

        boolean choice;
        String title;
        String description;

        Displays.printTitle("Modification du projet : " + chosenProject.id + " / " + chosenProject.title);
        ProjectService projectService = new ProjectService();

        choice = askYesNo("Voulez-vous modifier le titre ?");
        if (choice)
            title = ask("Nouveau titre :");
        else
            title = this.chosenProject.getTitle();

        choice = askYesNo("Voulez-vous modifier la description ?");
        if (choice)
            description = ask("Nouvelle description :");
        else
            description = this.chosenProject.getDescription();

        UpdateProjectRequest updateProjectRequest = new UpdateProjectRequest(title, description, this.chosenProject.id);
        Mono<Void> project = projectService.updateProject(apiClient.getWebClient(), updateProjectRequest);

        project.block();
    }

    void modifyColumn() {
        clearConsole();

        boolean choice;
        String title;

        Displays.printTitle("Modification de la colonne : " + chosenColumn.id + " / " + chosenColumn.title);
        ColumnService columnService = new ColumnService();

        choice = askYesNo("Voulez-vous modifier le titre ?");
        if (choice) {
            title = ask("Nouveau titre :");
            Displays.printError(title);
        } else
            title = this.chosenColumn.getTitle();

        UpdateColumnRequest updateColumnRequest = new UpdateColumnRequest(title, this.chosenColumn.id);
        Mono<Void> column = columnService.updateColumn(apiClient.getWebClient(), updateColumnRequest);

        column.block();
    }

    void modifyTask() {
        clearConsole();

        boolean choice;
        String title;
        String description;
        String priority;

        Displays.printTitle("Modification de la tâche : " + chosenTask.id + " / " + chosenTask.title);
        TaskService taskService = new TaskService();

        choice = askYesNo("Voulez-vous modifier le titre ?");
        if (choice)
            title = ask("Nouveau titre :");
        else
            title = this.chosenTask.getTitle();

        choice = askYesNo("Voulez-vous modifier la description ?");
        if (choice)
            description = ask("Nouvelle description :");
        else
            description = this.chosenTask.getDescription();

        choice = askYesNo("Voulez-vous modifier la priorité ?");
        if (choice) {
            Displays.printInformation("Nouvelle description :");
            Displays.printInformation("Niveau de priorité :\n" +
                    "0 : Bas\n" +
                    "1 : Moyen\n" +
                    "2 : Haut"
            );
            int choicePriority = askMultipleChoices(3);
            priority = (choicePriority == 0 ? "LOW" : (choicePriority == 1 ? "MEDIUM" : "HIGH"));
        } else
            priority = this.chosenTask.getDescription();

        UpdateTaskRequest updateTaskRequest = new UpdateTaskRequest(this.chosenTask.id, title, description, priority);
        Mono<Void> task = taskService.updateTask(apiClient.getWebClient(), updateTaskRequest);

        task.block();
    }

    void modifyChecklist() {
        clearConsole();

        boolean choice;
        String title;

        Displays.printTitle("Modification de la checklist : " + chosenChecklist.id + " / " + chosenChecklist.title);
        ChecklistService checklistService = new ChecklistService();

        choice = askYesNo("Voulez-vous modifier le titre ?");
        if (choice)
            title = ask("Nouveau titre :");
        else
            title = this.chosenChecklist.getTitle();

        UpdateChecklistRequest updateChecklistRequest = new UpdateChecklistRequest(title, this.chosenChecklist.id);
        Mono<Void> checklist = checklistService.updateChecklist(apiClient.getWebClient(), updateChecklistRequest);

        checklist.block();
    }

    void modifyOption(Option option) {
        clearConsole();

        boolean choice;
        String title;

        Displays.printTitle("Modification de l'option : " + option.id + " / " + option.title);
        OptionService optionService = new OptionService();

        choice = askYesNo("Voulez-vous modifier le titre ?");
        if (choice)
            title = ask("Nouveau titre :");
        else
            title = this.chosenProject.getTitle();

        UpdateOptionRequest updateOptionRequest = new UpdateOptionRequest(title, this.chosenProject.id);
        Mono<Void> optionUpdate = optionService.updateOption(apiClient.getWebClient(), updateOptionRequest);

        optionUpdate.block();
    }

    void deleteProject() {
        clearConsole();

        Displays.printTitle("Suppression du projet : " + this.chosenProject.id + " / " + this.chosenProject.title);

        boolean choice = askYesNo("Voulez-vous supprimer le projet ?");

        if (choice) {

            ProjectService service = new ProjectService();

            DeleteProjectRequest request = new DeleteProjectRequest(this.chosenProject.id);
            Mono<Void> requestResult = service.deleteProject(apiClient.getWebClient(), request);

            requestResult.block();

            this.deleted = true;
        }
    }

    void deleteColumn() {
        clearConsole();

        Displays.printTitle("Suppression de la colonne : " + this.chosenColumn.id + " / " + this.chosenColumn.title);

        boolean choice = askYesNo("Voulez-vous supprimer la colonne ?");

        if (choice) {

            ColumnService service = new ColumnService();

            DeleteColumnRequest request = new DeleteColumnRequest(this.chosenProject.id, this.chosenColumn.id);
            Mono<Void> requestResult = service.deleteColumn(apiClient.getWebClient(), request);

            requestResult.block();

            this.deleted = true;
        }
    }

    void deleteTask() {
        clearConsole();

        Displays.printTitle("Suppression de la tâche : " + this.chosenTask.id + " / " + this.chosenTask.title);

        boolean choice = askYesNo("Voulez-vous supprimer la tâche ?");

        if (choice) {

            TaskService service = new TaskService();

            DeleteTaskRequest request = new DeleteTaskRequest(this.chosenProject.id, this.chosenTask.id);
            Mono<Void> requestResult = service.deleteTask(apiClient.getWebClient(), request);

            requestResult.block();

            this.deleted = true;
        }
    }

    void deleteChecklist() {
        clearConsole();

        Displays.printTitle("Suppression de la checklist : " + this.chosenChecklist.id + " / " + this.chosenChecklist.title);

        boolean choice = askYesNo("Voulez-vous supprimer la checklist ?");

        if (choice) {

            ChecklistService service = new ChecklistService();

            DeleteChecklistRequest request = new DeleteChecklistRequest(this.chosenProject.id, this.chosenChecklist.id);
            Mono<Void> requestResult = service.deleteChecklist(apiClient.getWebClient(), request);

            requestResult.block();

            this.deleted = true;
        }
    }

    void deleteOption(Option option) {
        clearConsole();

        Displays.printTitle("Suppression de l'option : " + option.id + " / " + option.title);

        boolean choice = askYesNo("Voulez-vous supprimer l'option ?");

        if (choice) {

            OptionService service = new OptionService();

            DeleteOptionRequest request = new DeleteOptionRequest(this.chosenProject.id, this.chosenChecklist.id);
            Mono<Void> requestResult = service.deleteOption(apiClient.getWebClient(), request);

            requestResult.block();
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
        } while (!answer.equals("Y") && !answer.equals("N") && !answer.equals("y") && !answer.equals("n"));

        return (answer.equals("Y") || answer.equals("y"));
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
                try {
                    choiceValue = Integer.parseInt(choice);
                } catch (NumberFormatException ignored) {
                    choiceValue = nbOfChoices;
                }
            } while (choiceValue > nbOfChoices - 1 || choiceValue < 0);
        } else if (nbOfChoices == 1) return 0;
        else return null;

        return choiceValue;
    }

    /*
    Fonctions usuelles d'écriture de la console
     */

    void waitInput() {
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

