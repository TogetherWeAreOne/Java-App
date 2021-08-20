package com.togetherweareone.services;

import com.togetherweareone.models.Column;
import com.togetherweareone.models.Project;
import com.togetherweareone.request.projectRequest.*;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ProjectService {

    public Mono<Project> createProject(WebClient webClient, CreateProjectRequest request) {
        return webClient.post()
                .uri("/project/create")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CreateProjectRequest.class)
                .retrieve()
                .bodyToMono(Project.class);
    }

    public Mono<Void> updateProject(WebClient webClient, UpdateProjectRequest request) {
        return webClient.put()
                .uri("/project/update/" + request.getProjectId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), UpdateProjectRequest.class)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<Column[]> getAllColumns(WebClient webClient, GetAllColumnsRequest request) {
        return webClient.get()
                .uri("/project/allColumns/" + request.getProjectId())
                .retrieve()
                .bodyToMono(Column[].class);
    }

    public Mono<Project[]> getAllProjects(WebClient webClient) {
        return webClient.get()
                .uri("/project/all/")
                .retrieve()
                .bodyToMono(Project[].class);
    }

    public Mono<Project[]> getAllProjectsForUser(WebClient webClient, GetAllProjectsRequest request) {
        return webClient.get()
                .uri("/project/all/" + request.getUserId())
                .retrieve()
                .bodyToMono(Project[].class);
    }

    public Mono<Void> deleteProject(WebClient webClient, DeleteProjectRequest request) {
        return webClient.delete()
                .uri("/project/delete/" + request.getProjectId())
                .retrieve()
                .bodyToMono(Void.class);
    }
}
