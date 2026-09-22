package com.udea.service_platform.modules.reservations.dto;

import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaResponse {

    private Long id;
    private String servicioNombre;
    private String clienteNombre;
    private String clienteCorreo;
    private LocalTime fechaInicio;
    private String estado;
}
