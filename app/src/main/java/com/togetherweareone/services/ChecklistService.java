package com.togetherweareone.services;

import com.togetherweareone.models.Checklist;
import com.togetherweareone.models.Option;
import com.togetherweareone.request.checklistRequest.CreateChecklistRequest;
import com.togetherweareone.request.checklistRequest.DeleteChecklistRequest;
import com.togetherweareone.request.checklistRequest.GetAllOptionsRequest;
import com.togetherweareone.request.checklistRequest.UpdateChecklistRequest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ChecklistService {

    public Mono<Checklist> createChecklist(WebClient webClient, CreateChecklistRequest request) {
        return webClient.post()
                .uri("/checklist/create/" + request.getTaskId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CreateChecklistRequest.class)
                .retrieve()
                .bodyToMono(Checklist.class);
    }

    public Mono<Void> updateChecklist(WebClient webClient, UpdateChecklistRequest request) {
        return webClient.put()
                .uri("/checklist/update/" + request.getChecklistId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), UpdateChecklistRequest.class)
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<Option[]> getAllOptions(WebClient webClient, GetAllOptionsRequest request) {
        return webClient.get()
                .uri("/checklist/allOptions/" + request.getChecklistId())
                .retrieve()
                .bodyToMono(Option[].class);

    }

    public Mono<Void> deleteChecklist(WebClient webClient, DeleteChecklistRequest request) {
        return webClient.delete()
                .uri("/checklist/delete/" + request.getChecklistId() + "/" + request.getProjectId())
                .retrieve()
                .bodyToMono(Void.class);
    }

}
