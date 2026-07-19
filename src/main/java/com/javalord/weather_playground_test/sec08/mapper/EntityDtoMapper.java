package com.javalord.weather_playground_test.sec08.mapper;

import com.javalord.weather_playground_test.sec08.dto.ProductDto;
import com.javalord.weather_playground_test.sec08.entity.Product;

public class EntityDtoMapper {

    public static Product toEntity(ProductDto dto) {
        Product product = new Product();
        product.setPrice(dto.price());
        product.setDescription(dto.description());
        product.setId(dto.id());

        return product;
    }

    public static ProductDto toDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getDescription(),
                product.getPrice()
        );
    }

}
