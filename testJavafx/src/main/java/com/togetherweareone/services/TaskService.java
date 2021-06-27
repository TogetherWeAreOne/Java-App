package com.togetherweareone.services;

import com.togetherweareone.models.Checklist;
import com.togetherweareone.models.Sticker;
import com.togetherweareone.models.Task;
import com.togetherweareone.request.stickerRequest.CreateStickerRequest;
import com.togetherweareone.request.taskRequest.CreateTaskRequest;
import com.togetherweareone.request.taskRequest.DeleteTaskRequest;
import com.togetherweareone.request.taskRequest.GetAllChecklistsRequest;
import com.togetherweareone.request.taskRequest.UpdateTaskRequest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class TaskService {

    public Mono<Task> createTask(WebClient webClient, CreateTaskRequest request){
        return webClient.post()
                .uri("/project/column/" + request.getColumnId() + "/task/create")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CreateTaskRequest.class)
                .retrieve()
                .bodyToMono(Task.class);
    }

    public Mono<Void> updateTask(WebClient webClient, UpdateTaskRequest request){
        return webClient.put()
                .uri("project/task/" + request.getTaskId() + "/update")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), UpdateTaskRequest.class)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<Void> deleteTask(WebClient webClient, DeleteTaskRequest request){
        return webClient.delete()
                .uri("project/" + request.getProjectId() + "/column/task/" + request.getTaskId() + "/delete")
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<Checklist[]> getAllChecklists(WebClient webClient, GetAllChecklistsRequest request){
        return webClient.get()
                .uri("project/column/task/" + request.getTaskId() + "/get/allChecklist")
                .retrieve()
                .bodyToMono(Checklist[].class);
    }
}
