package com.javatpoint.controller;

import com.javatpoint.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/customerinfo")
public class CustomerController {
    private HashMap<Integer, Customer> fictionalDatabase = new HashMap<>();

    @PostConstruct
    public void initData() {
        fictionalDatabase.put(5126890, new Customer(5126890, "Bob Brudnui", "Current A/c", 450000.00));
        fictionalDatabase.put(7620015, new Customer(7620015, "Oleksandr Nikolaichuk", "Saving A/c", 210089.00));
    }

    @RequestMapping("/all")
    //defines a cache for method's return value
    @Cacheable(cacheNames = "simpleCache")
    public List<Customer> customerInformation() {
        log.info("get customer information");
        log.info("getAll method if without cache we will see a delay");
        delay();
        return new ArrayList<>(fictionalDatabase.values());
    }

    @GetMapping(value = "/{accountNumber}")
    @Cacheable(cacheNames = "simpleCache")
    public Customer getCustomerById(@PathVariable Integer accountNumber) {
        log.info("getCustomerById method if without cache we will see a delay");
        delay();
        return fictionalDatabase.get(accountNumber);
    }

    @DeleteMapping(value = "/{accountNumber}")
    @CacheEvict(cacheNames = "simpleCache", key = "#accountNumber", allEntries = true)
    public ResponseEntity<Customer> delete(@PathVariable Integer accountNumber) {
        if (fictionalDatabase.containsKey(accountNumber)) {
            Customer deleted = fictionalDatabase.remove(accountNumber);
            return ResponseEntity.ok(deleted);
        } else {
            return ResponseEntity.ok(new Customer());
        }
    }

    @PostMapping(value = "/create")
    @CacheEvict(cacheNames = "simpleCache", allEntries = true)
    public ResponseEntity<Customer> create(@RequestBody Customer customer) {
        fictionalDatabase.put(customer.getAccountNumber(), customer);
        log.info("create record {}", customer);
        return ResponseEntity.ok(customer);
    }


    private static void delay() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        }
    }
}
