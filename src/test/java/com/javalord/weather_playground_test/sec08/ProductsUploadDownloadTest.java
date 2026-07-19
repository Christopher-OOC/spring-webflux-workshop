package com.javalord.weather_playground_test.sec08;

import com.javalord.weather_playground_test.sec08.dto.ProductDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

@Slf4j
public class ProductsUploadDownloadTest {

    private final ProductClient productClient = new ProductClient();

    @Test
    public void upload() {
        var flux = Flux.range(1, 10)
                .map(i -> new ProductDto(null, "product-" + i, i))
                .delayElements(Duration.ofSeconds(10));

        this.productClient.uploadResponseMono(flux)
                .doOnNext(r -> log.info("Received: {}", r))
                .then()
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

}
