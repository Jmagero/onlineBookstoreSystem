package jm.onlineBookstoreSystem.service;


import jm.onlineBookstoreSystem.exceptional.UserNotFoundException;
import jm.onlineBookstoreSystem.entity.Customer;;
import jm.onlineBookstoreSystem.repository.BookstoreRepository;
import jm.onlineBookstoreSystem.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;



    @Autowired
    public CustomerService(CustomerRepository customerRepository, BookstoreRepository bookStoreRepository) {

        this.customerRepository = customerRepository;
    }

    // Create a new user
    public Customer addUser(Customer customer) {
        return customerRepository.save(customer);
    }

    // Get all users
    public List<Customer> getAllUsers() {
        return customerRepository.findAll();
    }

    // Get user by ID
    public Customer getUserById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
    }

    // Delete a user by ID
    public void deleteUser(Long id) {
        customerRepository.deleteById(id);
    }


}
