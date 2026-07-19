package com.javalord.weather_playground_test.sec08.service;

import com.javalord.weather_playground_test.sec08.dto.ProductDto;
import com.javalord.weather_playground_test.sec08.mapper.EntityDtoMapper;
import com.javalord.weather_playground_test.sec08.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Flux<ProductDto> saveProducts(Flux<ProductDto> flux) {
        return flux.map(EntityDtoMapper::toEntity)
                .as(this.productRepository::saveAll)
                .map(EntityDtoMapper::toDto);
    }

    public Mono<Long> getProductsCount() {
        return this.productRepository.count();
    }
}
