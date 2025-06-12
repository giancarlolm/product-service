package com.mitocode.product_service.dao;

import com.mitocode.product_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductDAO extends JpaRepository<Product, Long> {

    Product findByName(String name);

    Optional<Product> findOptionalByName(String name);

}
