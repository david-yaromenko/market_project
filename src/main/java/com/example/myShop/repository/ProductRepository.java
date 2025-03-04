package com.example.myShop.repository;

import com.example.myShop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = """
            SELECT * from Product where name = :name""", nativeQuery = true)
    Product findByName(String name);

}
