package com.mitocode.product_service.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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
@Table(name = "brand")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Entidad de marca de productos")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único de la marca", example = "1") //SWAGGER SCHEMA DESCRIPCION
    private Long id;

    @Column(name = "name", nullable = false, length = 100, unique = true)
    @NotBlank(message = "El campo es requerido")
    @Size(max = 100, message = "Brand name must not exceed 100 characters")
    @Schema(description = "Nombre de la marca", example = "Samsung", required = true)
    private String name;

    @Column(name = "country_origin", length = 50)
    @Size(max = 50, message = "El campo no debe excender los 50 caracteres")
    @Schema(description = "País de origen de la marca", example = "Perú")
    private String countryOrigin;

    @Column(name = "default_warranty_months", nullable = false)
    @Min(value = 1, message = "Warranty months must be at least 1")
    @Builder.Default
    @Schema(description = "Garantía por defecto en meses", example = "12", defaultValue = "12")
    private Integer defaultWarrantyMonths = 12;

    @Column(name = "is_active", nullable = false)
    @Builder.Default
    @Schema(description = "Estado activo de la marca", example = "true", defaultValue = "true")
    private Boolean isActive = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @Schema(description = "Fecha y hora de creación")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    @Schema(description = "Fecha y hora de última actualización")
    private LocalDateTime updatedAt;
}
