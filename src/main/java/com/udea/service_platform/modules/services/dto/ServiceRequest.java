package com.udea.service_platform.modules.services.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    private String description;

    @NotNull(message = "La duración es obligatoria")
    @Min(value = 1, message = "La duración debe ser al menos 1 minuto")
    private Integer duration;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 1, message = "El precio debe ser al menos 1")
    private BigDecimal price;

    @NotNull(message = "El proveedor es obligatorio")
    private Long providerId;
}
