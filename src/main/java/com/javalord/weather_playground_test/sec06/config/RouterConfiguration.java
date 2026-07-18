package com.javalord.weather_playground_test.sec06.config;

import com.javalord.weather_playground_test.sec06.exception.CustomerNotFoundException;
import com.javalord.weather_playground_test.sec06.exception.InvalidInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class RouterConfiguration {

    @Autowired
    private CustomerRequestHandler customerRequestHandler;

    @Autowired
    private ApplicationExceptionHandler exceptionHandler;

    @Bean
    public RouterFunction<ServerResponse> customerRoutes1() {
        return RouterFunctions.route()
                .GET(req -> true, this.customerRequestHandler::allCustomers)
                .path("customers", this::customerRoutes2)
                .GET("/customers", req -> this.customerRequestHandler.allCustomers(req))
                .GET("/customers/{id}", req -> this.customerRequestHandler.getCustomer(req))
                .POST("/customers", req -> this.customerRequestHandler.saveCustomer(req))
                .onError(CustomerNotFoundException.class, this.exceptionHandler::handleException )
                .onError(InvalidInputException.class, this.exceptionHandler::handleException )
                .filter(((request, next) -> {
                    return next.handle(request);
                }))
                .filter(((request, next) -> {
                    return next.handle(request);
                }))
                .build();
    }

    private RouterFunction<ServerResponse> customerRoutes2() {
        return RouterFunctions.route()
                .PUT("/{id}", req -> this.customerRequestHandler.updateCustomer(req))
                .DELETE("/{id}", req -> this.customerRequestHandler.deleteCustomer(req))
                .build();
    }
}

