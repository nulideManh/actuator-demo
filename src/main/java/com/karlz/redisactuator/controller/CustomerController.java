package com.karlz.redisactuator.controller;

import com.karlz.redisactuator.entity.Customer;
import com.karlz.redisactuator.service.CustomerCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerCache customerCache;

    @PostMapping("/")
    public Customer save (@RequestBody Customer customer) {
        return customerCache.save(customer, customer.getId());
    }

    @GetMapping("/")
    public List<Customer> getAllCustomers() {
        return customerCache.findAll();
    }

    @GetMapping("/{id}")
    @Cacheable(key = "#id", value = "Customer")
    public Customer findCustomer(@PathVariable String id) {
        return customerCache.findById(id);
    }

    @DeleteMapping("/{id}")
    @CacheEvict(key = "#id", value = "Customer")
    public String deleteCustomer (@PathVariable String id) {
        return customerCache.deleteById(id);
    }

    @DeleteMapping("/delete/{id}")
    @CacheEvict(key = "#id", value = "Customer")
    public Customer findAndDelete (@PathVariable String id) {
        return customerCache.findAndDelete(id);
    }
}
