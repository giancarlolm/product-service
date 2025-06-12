package com.mitocode.product_service.dao;

import com.mitocode.product_service.entitynosql.ProductNoSql;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProductNoSqlDAO extends MongoRepository<ProductNoSql, String> {
    Optional<ProductNoSql> findById(Long id);

    Optional<ProductNoSql> findByName(String name);

    Optional<ProductNoSql> findByCustomId(Long id);

    Optional<ProductNoSql> findByDescription(String description);

}
