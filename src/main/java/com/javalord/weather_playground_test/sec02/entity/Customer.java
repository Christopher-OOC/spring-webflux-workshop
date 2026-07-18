package com.javalord.weather_playground_test.sec02.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(value = "customer")
public class Customer {

    @Id
    private Integer id;
    private String name;
    private String email;

}
