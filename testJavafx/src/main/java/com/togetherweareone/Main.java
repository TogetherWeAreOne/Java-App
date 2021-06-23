package com.togetherweareone;

import com.togetherweareone.api.ApiClient;
import com.togetherweareone.cli.Cli;
import com.togetherweareone.models.Project;
import com.togetherweareone.models.User;
import com.togetherweareone.request.authRequest.LoginRequest;
import com.togetherweareone.request.projectRequest.CreateProjectRequest;
import com.togetherweareone.services.AuthService;
import com.togetherweareone.services.ProjectService;
import javafx.application.Application;
import javafx.stage.Stage;
import reactor.core.publisher.Mono;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        /*Parent root = FXMLLoader.load(getClass().getResource("start.fxml"));
        primaryStage.setTitle("TogetherWeAreOne Organisation");
        primaryStage.setScene(new Scene(root, 1200, 800));

        primaryStage.show();
*/
        ApiClient client = new ApiClient();
        AuthService service = new AuthService();
        ProjectService projectService = new ProjectService();
        LoginRequest request = new LoginRequest("test2@gmail.com","test");
        CreateProjectRequest createProjectRequest = new CreateProjectRequest("TEST JAVA", "TEST JAVA DESCRIPTION");
        //MultiValueMap<String, String> cookies = new LinkedMultiValueMap<String, String>();

        Mono<User> user = service.login(client.getWebClient(), request);

        user.doOnSuccess(System.out::println)
                .block();

        Mono<Project> project = projectService.createProject(client.getWebClient(), createProjectRequest);
        project.doOnSuccess(System.out::println)
                .block();

    }

    public static void main(String[] args){
        if (args.length > 0 && args[0].equals("-cli")){
            new Cli();
        } else {
            launch(args);
        }
    }
}
