package com.togetherweareone;

import com.togetherweareone.api.ApiClient;
import com.togetherweareone.models.User;
import com.togetherweareone.request.authRequest.SignInRequest;
import com.togetherweareone.services.AuthService;
import javafx.application.Application;
import javafx.stage.Stage;
import reactor.core.publisher.Mono;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
       /* Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("TogetherWeAreOne Organisation");
        primaryStage.setScene(new Scene(root, 1200, 800));
        primaryStage.show();*/

        ApiClient client = new ApiClient();
        AuthService service = new AuthService();
        SignInRequest request = new SignInRequest("testBBBBBaaaaaaB@gmail.com","test","montest","coucou","pseudo");
        Mono<User> user = service.signIn(client.getWebClient(), request);
        user.subscribe(user1 -> System.out.println(user1), error -> System.err.println("error : " + error));
    }
}
