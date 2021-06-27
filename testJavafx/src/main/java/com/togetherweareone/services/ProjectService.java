package com.togetherweareone.services;

import com.togetherweareone.models.Column;
import com.togetherweareone.models.Project;
import com.togetherweareone.models.User;
import com.togetherweareone.request.authRequest.LoginRequest;
import com.togetherweareone.request.projectRequest.*;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.function.Predicate;

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

    public Mono<Void> updateProject(WebClient webClient, UpdateProjectRequest request){
        return webClient.put()
                .uri("/project/update/" + request.getProjectId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), UpdateProjectRequest.class)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<Column[]> getAllColumns(WebClient webClient, GetAllColumnsRequest request){
        return webClient.get()
                .uri("/project/" + request.getProjectId() + "/get/allColumn")
                .retrieve()
                .bodyToMono(Column[].class);
    }

    public Mono<Project[]> getAllProjects(WebClient webClient){
        return webClient.get()
                .uri("/project/get/all")
                .retrieve()
                .bodyToMono(Project[].class);
    }

    public Mono<Void> deleteProject(WebClient webClient, DeleteProjectRequest request){
        return webClient.delete()
                .uri("project/" + request.getProjectId() + "/delete")
                .retrieve()
                .bodyToMono(Void.class);
    }
}
