package com.togetherweareone.services;

import com.togetherweareone.models.Column;
import com.togetherweareone.models.Task;
import com.togetherweareone.request.columnRequest.CreateColumnRequest;
import com.togetherweareone.request.columnRequest.DeleteColumnRequest;
import com.togetherweareone.request.columnRequest.GetAllTasksRequest;
import com.togetherweareone.request.columnRequest.UpdateColumnRequest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ColumnService {

    public Mono<Column> createColumn(WebClient webClient, CreateColumnRequest request) {
        return webClient.post()
                .uri("/project/" + request.getProjectId() + "/column/create")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CreateColumnRequest.class)
                .retrieve()
                .bodyToMono(Column.class);
    }

    public Mono<Void> updateColumn(WebClient webClient, UpdateColumnRequest request) {
        return webClient.put()
                .uri("project/column/" + request.getColumnId() + "/update")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), UpdateColumnRequest.class)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<Task[]> getAllTasks(WebClient webClient, GetAllTasksRequest request) {
        return webClient.get()
                .uri("project/column/" + request.getColumnId() + "/get/allTask")
                .retrieve()
                .bodyToMono(Task[].class);
    }

    public Mono<Void> deleteColumn(WebClient webClient, DeleteColumnRequest request) {
        return webClient.delete()
                .uri("project/" + request.getProjectId() + "/column/" + request.getColumnId() + "/delete")
                .retrieve()
                .bodyToMono(Void.class);
    }
}
