package com.javalord.weather_playground_test.sec03.controller;

import com.javalord.weather_playground_test.sec03.dto.CustomerDto;
import com.javalord.weather_playground_test.sec03.service.CustomerService;
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
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @PostMapping
    public Mono<CustomerDto> saveCustomer(@RequestBody Mono<CustomerDto> mono) {
        return this.customerService.saveCustomer(mono);
    }

    @PutMapping(value = "/{id}")
    public Mono<ResponseEntity<CustomerDto>> updateCustomer(@PathVariable(value = "id") Integer id, @RequestBody Mono<CustomerDto> mono) {
        return this.customerService.updateCustomer(id, mono)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/{id}")
    public  Mono<ResponseEntity<Object>> deleteCustomer(@PathVariable(value = "id") Integer id) {
        return this.customerService.deleteCustomerById(id)
                .filter(b -> b)
                .map(b -> ResponseEntity.ok().build())
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
