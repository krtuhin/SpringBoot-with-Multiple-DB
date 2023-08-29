package com.multipledb.oracle.repo;

import com.multipledb.oracle.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
