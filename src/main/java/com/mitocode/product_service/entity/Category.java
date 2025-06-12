package com.mitocode.product_service.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Product category entity")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Categoría ID", example = "1")
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    @NotBlank(message = "El campo nombre es requerido")
    @Size(max = 100, message = "El nombre de la categoría no debe exceder los 100 caracteres")
    @Schema(description = "Category name", example = "Home Appliances", required = true)
    private String name;

    @Column(name = "description")
    @Schema(description = "Descripción de categoría", example = "Categoría de electrodomésticos")
    private String description;

    @Column(name = "parent_category_id")
    @Schema(description = "Id padre de categoría", example = "1")
    private Long parentCategoryId;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    @Schema(description = "Category active status", example = "true", defaultValue = "true")
    private Boolean isActive = true;

    @Column(name = "display_order")
    @Schema(description = "Orden de muestra de categoría", example = "1")
    private Integer displayOrder;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @Schema(description = "Fecha en timestamp de creación")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    @Schema(description = "Fecha en timestamp de actualización")
    private LocalDateTime updatedAt;
}
