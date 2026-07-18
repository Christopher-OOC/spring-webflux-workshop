package com.javalord.weather_playground_test.sec02;

import com.javalord.weather_playground_test.sec02.entity.Customer;
import com.javalord.weather_playground_test.sec02.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

@Slf4j
public class Lec01CustomerRepositoryTest extends AbstractTest {

    @Autowired
    private CustomerRepository repository;

    @Test
    public void findAll() {
        this.repository.findAll()
                .doOnNext(c -> log.info("{}", c))
                .as(StepVerifier::create)
                .expectNextCount(10)
                .expectComplete()
                .verify();
    }

    @Test
    public void findById() {
        this.repository.findById(2)
                .doOnNext(c -> log.info("{}", c))
                .as(StepVerifier::create)
                .assertNext(e -> Assertions.assertEquals("mike", e.getName()))
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }

    @Test
    public void findByName() {
        this.repository.findByName("jake")
                .doOnNext(c -> log.info("{}", c))
                .as(StepVerifier::create)
                .assertNext(e -> Assertions.assertEquals("jake", e.getName()))
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }

    @Test
    public void findByEmailEndingWith() {
        this.repository.findByEmailEndingWith("ke@gmail.com")
                .doOnNext(c -> log.info("{}", c))
                .as(StepVerifier::create)
                .assertNext(e -> Assertions.assertEquals("mike@gmail.com", e.getEmail()))
                .assertNext(e -> Assertions.assertEquals("jake@gmail.com", e.getEmail()))
                .expectComplete()
                .verify();
    }

    @Test
    public void insertAndDeleteCustomer() {
        var customer = new Customer();
        customer.setName("Chris");
        customer.setEmail("chris@gmail.com");

        this.repository.save(customer)
                .doOnNext(c -> log.info("{}", c))
                .as(StepVerifier::create)
                .assertNext(e -> Assertions.assertNotNull(e.getId()))
                .expectComplete()
                .verify();
    }

    @Test
    public void updateCustomer() {
        this.repository.findByName("ethan")
                .doOnNext(c -> c.setName("noel"))
                .flatMap(c -> this.repository.save(c))
                .doOnNext(c -> log.info("{}", c))
                .as(StepVerifier::create)
                .assertNext(e -> Assertions.assertEquals("noel", e.getName()))
                .expectComplete()
                .verify();
    }
}
