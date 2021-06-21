package com.togetherweareone.services;


import com.togetherweareone.models.User;
import com.togetherweareone.request.authRequest.LoginRequest;
import com.togetherweareone.request.authRequest.SignInRequest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


public class AuthService {


    public Mono<User> login(WebClient webClient, LoginRequest request) {

        return  webClient.post()
                .uri("/auth/login")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request),LoginRequest.class)
                .retrieve()
                .bodyToMono(User.class);
    }

    public Mono<User> signIn(WebClient webClient, SignInRequest request) {

        return  webClient.post()
                .uri("/auth/signin")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request),SignInRequest.class)
                .retrieve()
                .bodyToMono(User.class);
    }

    public Mono<Void> logout(WebClient webClient) {

        return  webClient.delete()
                .uri("/auth/logout")
                .retrieve()
                .bodyToMono(Void.class);
    }


}
