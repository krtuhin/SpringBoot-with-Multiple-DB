package com.multipledb.services;

import com.multipledb.oracle.entities.Product;
import com.multipledb.oracle.repo.ProductRepository;
import com.multipledb.postgres.entities.User;
import com.multipledb.postgres.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DbService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    //method to save user and product into two different database
    public void saveUserAndProduct() {

        //create and save user into oracle database
        User user = User.builder().name("Tuhin").city("Kolkata").build();
        this.userRepository.save(user);

        //create and save product into PostgreSQL database
        Product product = Product.builder().name("First Product").price(354).build();
        this.productRepository.save(product);
    }

    //method to get all users and products from two different database
    public void getAllUserAndProduct() {

        //fetching all user from database
        List<User> users = this.userRepository.findAll();

        //fetching all products from database
        List<Product> products = this.productRepository.findAll();

        //print all users
        for (User user : users) {

            System.out.println(user);
        }

        //print all products
        for (Product product : products) {

            System.out.println(product);
        }
    }
}
