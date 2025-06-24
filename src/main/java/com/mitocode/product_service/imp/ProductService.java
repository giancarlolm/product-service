package com.mitocode.product_service.imp;

import com.mitocode.product_service.dto.ProductRequest;
import com.mitocode.product_service.entity.Product;
import com.mitocode.product_service.entitynosql.ProductNoSql;
import jakarta.validation.Valid;

import java.util.List;

public interface ProductService {

    Product getById(Long id);

    ProductNoSql getReadById(Long id);

    ProductNoSql getReadByName(String name);

    Product createProduct(@Valid ProductRequest request);

    List<Product> getAllProducts();

    Product updateProduct(Product product);
}
