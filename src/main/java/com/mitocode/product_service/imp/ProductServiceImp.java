package com.mitocode.product_service.imp;

import com.mitocode.product_service.dao.ProductDAO;
import com.mitocode.product_service.dao.ProductNoSqlDAO;
import com.mitocode.product_service.dto.ProductRequest;
import com.mitocode.product_service.entity.Product;
import com.mitocode.product_service.entitynosql.ProductNoSql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private ProductNoSqlDAO productNoSqlDAO;


    @Override
    @Cacheable(value = "products", key = "#id")
    public Product getById(Long id) {
        this.sleepSeconds(3L);
        return productDAO.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Producto no encontrado. ID: " + id));
    }

    @Override
    public ProductNoSql getReadById(Long id) {
        this.sleepSeconds(3L);
        return productNoSqlDAO.findByCustomId(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Producto no encontrado. ID " + id
        ));
    }


    @Override
    public ProductNoSql getReadByName(String name) {
        return productNoSqlDAO.findByName(name).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Producto de lectura no encontrado. ID: " + name
        ));
    }


    @Override
    public Product createProduct(ProductRequest request) {


        Product product = Product.builder()
                .name(request.getName())
                .sku(request.getSku())
                .brandId(request.getBrandId())
                .categoryId(request.getCategoryId())
                .shortDescription(request.getDescription())
                .build();

        return productDAO.save(product);
    }

    @Override
    @Cacheable(value = "productList", key = "'all'")
    public List<Product> getAllProducts() {
        this.sleepSeconds(3L);
        return productDAO.findAll();
    }

    @Override
    @CacheEvict(value = "products", key = "#product.id")
    public Product updateProduct(Product product) {
        Product productUpdate = productDAO.findById(product.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Producto de lectura no encontrado. ID: " + product.getId()
                ));
        productUpdate.setShortDescription(product.getShortDescription());
        productUpdate.setName(product.getName());
        return productDAO.save(productUpdate);
    }

    private void sleepSeconds(long time) {
        try {
            Thread.sleep(time * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
