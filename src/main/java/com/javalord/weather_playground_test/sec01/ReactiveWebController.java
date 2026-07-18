package com.javalord.weather_playground_test.sec01;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(value = "/reactive")
@Slf4j
public class ReactiveWebController {

    private final WebClient webClient = WebClient
            .builder()
            .baseUrl("http://localhost:7070")
            .build();

    @GetMapping(value = "/products", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Product> getProducts() {
        var list = this.webClient.get()
                .uri("/demo01/products")
                .retrieve()
                .bodyToFlux(Product.class)
                .doOnNext(v -> log.info("received data: {}", v));

        log.info("received response: {}", list);
        return list;
    }
}
