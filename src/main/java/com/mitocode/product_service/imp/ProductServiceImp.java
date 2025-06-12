package com.mitocode.product_service.imp;

import com.mitocode.product_service.dao.ProductDAO;
import com.mitocode.product_service.dao.ProductNoSqlDAO;
import com.mitocode.product_service.dto.ProductRequest;
import com.mitocode.product_service.entity.Product;
import com.mitocode.product_service.entitynosql.ProductNoSql;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductServiceImp implements ProductService {

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private ProductNoSqlDAO productNoSqlDAO;


    @Override
    public Product getById(Long id) {
        return productDAO.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Producto no encontrado. ID: " + id));
    }

    @Override
    public ProductNoSql getReadById(Long id) {
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
                .regularPrice(request.getRegularPrice())
                .shortDescription(request.getDescription())
                .build();

        return productDAO.save(product);
    }
}
