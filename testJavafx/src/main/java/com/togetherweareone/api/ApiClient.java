package com.togetherweareone.api;

import org.eclipse.jetty.client.HttpClient;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.JettyClientHttpConnector;
import org.springframework.http.client.reactive.JettyResourceFactory;
import org.springframework.web.reactive.function.client.WebClient;


public class ApiClient {

    private static ApiClient instance;
    private final WebClient webClient;

    public ApiClient() {

        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:3000/organisation-app")
                .clientConnector(new JettyClientHttpConnector())
                .build();
    }

    public WebClient getWebClient() {
        return webClient;
    }
}
