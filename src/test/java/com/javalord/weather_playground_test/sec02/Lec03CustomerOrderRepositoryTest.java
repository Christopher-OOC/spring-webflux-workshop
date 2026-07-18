package com.javalord.weather_playground_test.sec02;

import com.javalord.weather_playground_test.sec02.repository.CustomerOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

@Slf4j
public class Lec03CustomerOrderRepositoryTest  extends AbstractTest  {

    @Autowired
    private CustomerOrderRepository customerOrderRepository;

    @Test
    public void productsOrderedByCustomer() {
        this.customerOrderRepository.getProductsOrderedByCustomer("mike")
                .doOnNext(p -> log.info("{}", p))
                .as(StepVerifier::create)
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }

    @Test
    public void OrderDetailsByProduct() {
        this.customerOrderRepository.getOrderDetailsByProduct("iphone 20")
                .doOnNext(p -> log.info("{}", p))
                .as(StepVerifier::create)
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }

}
