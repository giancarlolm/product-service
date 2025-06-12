package com.mitocode.product_service.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;


@Data
@Schema(description = "RequestBody para la creación de un producto")
public class ProductRequest {

    @NotBlank(message = "El sku es requerido")
    @Schema(description = "El campo SKU del producto", example = "THO-45345SDF", required = true)
    private String sku;


    @NotBlank(message = "El campo nombre es requerido :(")
    @NotNull(message = "El campo no puede ser null")
    @Schema(description = "Es el nombre del producto", example = "Hervidor eléctrico")
    private String name;

    @Schema(description = "Es la descripción del producto", example = "Hervidor eléctrico 700w de potencia")
    private String description;
    @Schema(description = "Es el precio del producto", example = "100.00")
    private BigDecimal regularPrice;
    @Schema(description = "Es el id de la categoria existente del producto", example = "1")
    private Long categoryId;
    @Schema(description = "Es el id de la marca existente del producto", example = "2")
    private Long brandId;

}
