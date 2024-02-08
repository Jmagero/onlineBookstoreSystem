package jm.onlineBookstoreSystem.controller;

import jm.onlineBookstoreSystem.entity.Customer;
import jm.onlineBookstoreSystem.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/users")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController( CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping()
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        Customer savedUser = customerService.addUser(customer);
        return ResponseEntity.ok(savedUser);
    }

}