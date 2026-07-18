package com.javalord.weather_playground_test.sec07;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.ProblemDetail;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@Slf4j
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

    @Test
    public void exchange() throws InterruptedException {
        this.client.get()
                .uri("/lec05/calculator/{a}/{b}", 10, 20)
                .header("operation", "*")
                .exchangeToMono(this::decode)
                .doOnNext(print())
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    private Mono<CalculatorResponse> decode(ClientResponse clientResponse) {
        log.info("Status code: {}", clientResponse.statusCode());
        if (clientResponse.statusCode().isError()) {
            return clientResponse.bodyToMono(ProblemDetail.class)
                    .doOnNext(pd -> log.info("{}", pd))
                    .then(Mono.empty());
        }

        return clientResponse.bodyToMono(CalculatorResponse.class);
    }
}
