package com.javalord.weather_playground_test.sec07;

import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.test.StepVerifier;

public class Lec05ErrrorResponseTest extends AbstractWebClient  {

    private final WebClient client = createWebClient(b -> b.defaultHeader("caller-id", "order-service"));

    @Test
    public void handlingError() throws InterruptedException {
        this.client.get()
                .uri("/lec05/calculator/{a}/{b}", 10, 20)
                .header("operation", "*")
                .retrieve()
                .bodyToMono(CalculatorResponse.class)
                .doOnError(WebClientRequestException.class, (e) -> {

                })
                .onErrorReturn(new CalculatorResponse(0, 0, null, 0.0))
                .onErrorReturn(WebClientRequestException.class, new CalculatorResponse(0, 0, null, 0.0))
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }
}
