package com.mitocode.product_service.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Entidad de producto con todas las especificaciones")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único del producto", example = "1")
    private Long id;

    @Column(name = "sku", nullable = false, length = 50, unique = true)
    @NotBlank(message = "SKU es requerido")
    @Size(max = 50, message = "El SKU no debe exceder los 50 caracteres")
    @Schema(description = "Código SKU del producto", example = "SAM-RF28T5001SR", required = true)
    private String sku;

    @Column(name = "name", nullable = false, length = 200)
    @NotBlank(message = "El nombre del producto es requerido")
    @Size(max = 200, message = "Product name must not exceed 200 characters")
    @Schema(description = "Nombre del producto", example = "Samsung Side by Side Refrigerator 617L", required = true)
    private String name;

    @Column(name = "short_description", length = 500)
    @Size(max = 500, message = "Short description must not exceed 500 characters")
    @Schema(description = "Descripción corta del producto", example = "Refrigeradora No Frost con dispensador de agua")
    private String shortDescription;

    @Column(name = "category_id", nullable = false)
    @NotNull(message = "Category es requerido")
    @Schema(description = "ID de la categoría", example = "4", required = true)
    private Long categoryId;

    @Column(name = "brand_id", nullable = false)
    @NotNull(message = "Brand es requerido")
    @Schema(description = "ID de la marca", example = "1", required = true)
    private Long brandId;

    @Column(name = "regular_price", nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Regular price is required")
    @DecimalMin(value = "0.01", message = "Regular price must be greater than 0")
    @Schema(description = "Precio regular en soles", example = "3299.00", required = true)
    private BigDecimal regularPrice;

    @Column(name = "sale_price", precision = 10, scale = 2)
    @DecimalMin(value = "0.01", message = "Sale price must be greater than 0")
    @Schema(description = "Precio de oferta en soles", example = "2799.00")
    private BigDecimal salePrice;

    @Column(name = "stock_quantity", nullable = false)
    @Min(value = 0, message = "Stock quantity cannot be negative")
    @Builder.Default
    @Schema(description = "Cantidad disponible en stock", example = "25", defaultValue = "0")
    private Integer stockQuantity = 0;

    @Column(name = "color", length = 50)
    @Size(max = 50, message = "Color must not exceed 50 characters")
    @Schema(description = "Color del producto", example = "Silver")
    private String color;

    @Column(name = "model", length = 100)
    @Size(max = 100, message = "Model must not exceed 100 characters")
    @Schema(description = "Modelo del producto", example = "RF28T5001SR")
    private String model;

    @Column(name = "warranty_months", nullable = false)
    @Min(value = 1, message = "Warranty months must be at least 1")
    @Builder.Default
    @Schema(description = "Período de garantía en meses", example = "24", defaultValue = "12")
    private Integer warrantyMonths = 12;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    @Schema(description = "Estado activo del producto", example = "true", defaultValue = "true")
    private Boolean isActive = true;

    @Column(name = "is_featured", nullable = false)
    @Builder.Default
    @Schema(description = "Estado destacado del producto", example = "false", defaultValue = "false")
    private Boolean isFeatured = false;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @Schema(description = "Fecha y hora de creación")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    @Schema(description = "Fecha y hora de última actualización")
    private LocalDateTime updatedAt;


}

