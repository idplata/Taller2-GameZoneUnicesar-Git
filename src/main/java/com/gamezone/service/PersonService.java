package com.gamezone.service;

import com.gamezone.model.Customer;
import com.gamezone.model.Seller;
import com.gamezone.persistence.PersonRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class PersonService {

    private final PersonRepository repository;
    private final List<Customer> customers;
    private final List<Seller> sellers;

    public PersonService(PersonRepository repository) {
        this.repository = repository;
        this.customers = repository.loadCustomers();
        this.sellers = repository.loadSellers();

        if (this.sellers.isEmpty()) {
            preloadDefaultSellers();
        }
    }
    
    public Customer registerCustomer(String id, String name, String phone, String email) {
        if (findCustomerById(id).isPresent()) {
            throw new IllegalArgumentException("A customer with id " + id + " already exists");
        }
        Customer customer = new Customer(id, name, phone, email);
        customers.add(customer);
        repository.saveCustomers(customers);
        return customer;
    }

    public List<Customer> listCustomers() {
        return Collections.unmodifiableList(customers);
    }

    public List<Seller> listSellers() {
        return Collections.unmodifiableList(sellers);
    }

    public Optional<Customer> findCustomerById(String id) {
        return customers.stream()
                .filter(customer -> customer.getId().equals(id))
                .findFirst();
    }

        public Optional<Seller> findSellerById(String id) {
        return sellers.stream()
                .filter(seller -> seller.getId().equals(id))
                .findFirst();
    }

        private void preloadDefaultSellers() {
        List<Seller> defaults = new ArrayList<>();
        defaults.add(new Seller("V001", "Carlos Perez", "3001112233", "EMP001", "Morning"));
        defaults.add(new Seller("V002", "Laura Gomez", "3002223344", "EMP002", "Afternoon"));
        defaults.add(new Seller("V003", "Andres Torres", "3003334455", "EMP003", "Evening"));

        sellers.addAll(defaults);
        repository.saveSellers(sellers);
    }
}