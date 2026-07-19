package com.javalord.weather_playground_test.sec08.controller;

import com.javalord.weather_playground_test.sec08.dto.ProductDto;
import com.javalord.weather_playground_test.sec08.dto.UploadResponse;
import com.javalord.weather_playground_test.sec08.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping(value = "/products")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping(value = "/upload", consumes = MediaType.APPLICATION_NDJSON_VALUE)
    public Mono<UploadResponse> uploadProducts(@RequestBody Flux<ProductDto> flux) {
        return this.productService.saveProducts(flux.doOnNext(dto -> log.info("Received: {}", dto)))
                .then(this.productService.getProductsCount())
                .map(count -> new UploadResponse(UUID.randomUUID(), count));
    }
}
