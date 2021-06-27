package com.togetherweareone.services;

import com.togetherweareone.models.Option;
import com.togetherweareone.request.optionRequest.CreateOptionRequest;
import com.togetherweareone.request.optionRequest.DeleteOptionRequest;
import com.togetherweareone.request.optionRequest.UpdateOptionRequest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class OptionService {

    public Mono<Option> createOption(WebClient webClient, CreateOptionRequest request) {
        return webClient.post()
                .uri("/project/column/task/checklist/" + request.getChecklistId() + "/option/create")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CreateOptionRequest.class)
                .retrieve()
                .bodyToMono(Option.class);
    }

    public Mono<Void> updateOption(WebClient webClient, UpdateOptionRequest request) {
        return webClient.put()
                .uri("/project/option/" + request.getOptionId() + "/update")
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<Void> deleteOption(WebClient webClient, DeleteOptionRequest request) {
        return webClient.delete()
                .uri("/project/" + request.getProjectId() + "/column/task/checklist/option/" + request.getOptionId() + "/delete")
                .retrieve()
                .bodyToMono(Void.class);
    }
}
