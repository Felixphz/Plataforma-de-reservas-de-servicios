package com.udea.service_platform.modules.services.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceResponse {

    private Long id;
    private String name;
    private String description;
    private Integer duration;
    private BigDecimal price;
    private Long providerId;
}
