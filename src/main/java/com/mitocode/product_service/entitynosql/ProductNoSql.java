package com.mitocode.product_service.entitynosql;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "product")
public class ProductNoSql {

    @Id
    private String id; //_id: MONGODB

    private Long customId; // customId: mysql

    private String name;

    private BigDecimal price;

    private String description;

    @Field(name = "prod_id") //nombre del atributo nosql
    private Long prodId;

}
