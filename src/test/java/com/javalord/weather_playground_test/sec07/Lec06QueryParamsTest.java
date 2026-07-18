package com.javalord.weather_playground_test.sec07;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

@Slf4j
public class Lec06QueryParamsTest extends AbstractWebClient {

    private final WebClient client = createWebClient(b -> b.defaultHeader("caller-id", "order-service"));

    @Test
    public void uriBuilderVariables() throws InterruptedException {
        var path = "/lec06/calculator";
        var query = "first={first}&second={second}&operation={operation}";

        this.client.get()
                .uri(builder -> builder.path(path).query(query).build(10, 20 , "+"))
                .retrieve()
                .bodyToMono(CalculatorResponse.class)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }
}
