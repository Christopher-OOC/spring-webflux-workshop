package com.javalord.weather_playground_test.sec07;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.util.Map;

@Slf4j
public class Lec04HeaderTest extends AbstractWebClient {

    private final WebClient client = createWebClient(b -> b.defaultHeader("caller-id", "order-service"));

    @Test
    public void defaultHeader() throws InterruptedException {
        this.client.get()
                .uri("/lec04/product/{id}", 1)
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    @Test
    public void overrideHeader() throws InterruptedException {
        this.client.get()
                .uri("/lec04/product/{id}", 1)
                .header("caller-id", "order-service")
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    @Test
    public void mapHeaders() throws InterruptedException {

        var map = Map.of(
                "caller-id", "order-service",
                "some-key", "order-service"
        );

        this.client.get()
                .uri("/lec04/product/{id}", 1)
                .headers(h -> h.setAll(map))
                .retrieve()
                .bodyToMono(Product.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create   )
                .expectComplete()
                .verify();
    }
}
