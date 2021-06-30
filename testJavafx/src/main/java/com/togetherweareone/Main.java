package com.togetherweareone;

import com.togetherweareone.cli.Cli;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    boolean error = false;

    @Override
    public void start(Stage primaryStage) throws Exception {


        /*Parent root = FXMLLoader.load(getClass().getResource("start.fxml"));
        primaryStage.setTitle("TogetherWeAreOne Organisation");
        primaryStage.setScene(new Scene(root, 1200, 800));

        primaryStage.show();
*/
        /*ApiClient client = new ApiClient();
        AuthService service = new AuthService();
        ProjectService projectService = new ProjectService();
        LoginRequest request = new LoginRequest("test@gmail.com","password");
        CreateProjectRequest createProjectRequest = new CreateProjectRequest("testProjectInline", "testProjectInline");
        //MultiValueMap<String, String> cookies = new LinkedMultiValueMap<String, String>();

        Mono<User> user = service.login(client.getWebClient(), request);

        user.doOnSuccess(System.out::println)
                .doOnError(e ->  {
                    error = true;
                })
                .onErrorReturn(new User())
                .block();

        if (error)
            System.out.println("NO CREATION USER");
        else {
            Mono<Project> project = projectService.createProject(client.getWebClient(), createProjectRequest);
            project.doOnSuccess(System.out::println)
                    .onErrorReturn(new Project())
                    .block();
        }*/


    }

    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new Thread(Cli::endApp));

        if (args.length > 0 && args[0].equals("-cli")) {
            new Cli();
        } else {
            launch(args);
        }
        System.exit(0);
    }
}
