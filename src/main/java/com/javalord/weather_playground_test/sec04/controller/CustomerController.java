package com.javalord.weather_playground_test.sec04.controller;

import com.javalord.weather_playground_test.sec04.dto.CustomerDto;
import com.javalord.weather_playground_test.sec04.exception.ApplicationExceptions;
import com.javalord.weather_playground_test.sec04.service.CustomerService;
import com.javalord.weather_playground_test.sec04.validator.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public Flux<CustomerDto> allCustomers() {
        return this.customerService.getAllCustomers();
    }

    @GetMapping(value = "/paginated")
    public Flux<CustomerDto> allCustomersPaginated(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "size",defaultValue = "3") Integer size) {
        return this.customerService.getAllCustomers(page, size);
    }


    @GetMapping(value = "/{id}")
    public Mono<ResponseEntity<CustomerDto>> getCustomer(@PathVariable(value = "id") Integer id) {
        return this.customerService.getCustomerById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(ApplicationExceptions.customerNotFound(id));
    }

    @PostMapping
    public Mono<CustomerDto> saveCustomer(@RequestBody Mono<CustomerDto> mono) {
        return mono.transform(RequestValidator.validate())
                .as(this.customerService::saveCustomer);
    }

    @PutMapping(value = "/{id}")
    public Mono<CustomerDto> updateCustomer(@PathVariable(value = "id") Integer id, @RequestBody Mono<CustomerDto> mono) {
        return mono.transform(RequestValidator.validate())
                .as(validReq -> this.customerService.updateCustomer(id, validReq))
                .switchIfEmpty(ApplicationExceptions.customerNotFound(id));
    }

    @DeleteMapping(value = "/{id}")
    public  Mono<Void> deleteCustomer(@PathVariable(value = "id") Integer id) {
        return this.customerService.deleteCustomerById(id)
                .filter(b -> b)
                .switchIfEmpty(ApplicationExceptions.customerNotFound(id))
                .then();
    }
}
