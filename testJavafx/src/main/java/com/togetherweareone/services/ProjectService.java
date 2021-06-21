package com.togetherweareone.services;

import com.togetherweareone.models.Project;
import com.togetherweareone.models.User;
import com.togetherweareone.request.authRequest.LoginRequest;
import com.togetherweareone.request.projectRequest.CreateProjectRequest;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ProjectService {

    public Mono<Project> createProject(WebClient webClient, CreateProjectRequest request){
        return  webClient.post()
                .uri("/project/create")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CreateProjectRequest.class)
                .retrieve()
                .bodyToMono(Project.class);
    }
}
