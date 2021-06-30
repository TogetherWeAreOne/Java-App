package com.togetherweareone.services;

import com.togetherweareone.models.Sticker;
import com.togetherweareone.request.stickerRequest.CreateStickerRequest;
import com.togetherweareone.request.stickerRequest.DeleteStickerRequest;
import com.togetherweareone.request.stickerRequest.UpdateStickerRequest;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class StickerService {

    public Mono<Sticker> createSticker(WebClient webClient, CreateStickerRequest request) {
        return webClient.post()
                .uri("/sticker/create/" + request.getProjectId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(request), CreateStickerRequest.class)
                .retrieve()
                .bodyToMono(Sticker.class);
    }

    public Mono<Void> updateSticker(WebClient webClient, UpdateStickerRequest request) {
        return webClient.put()
                .uri("/sticker/update/" + request.getStickerId())
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<Void> deleteSticker(WebClient webClient, DeleteStickerRequest request) {
        return webClient.delete()
                .uri("/sticker/delete/"  + request.getStrickerId() + "/" + request.getProjectId())
                .retrieve()
                .bodyToMono(Void.class);
    }

    public Mono<Sticker[]> getAllSticker(WebClient webClient) {
        return webClient.get()
                .uri("/sticker/all")
                .retrieve()
                .bodyToMono(Sticker[].class);
    }
}
