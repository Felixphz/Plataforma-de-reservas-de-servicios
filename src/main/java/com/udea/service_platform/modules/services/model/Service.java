package com.udea.service_platform.modules.services.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "\"Servicios\"")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servicio")
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String name;

    @Column(name = "descripcion")
    private String description;

    @Column(name = "duracion", nullable = false)
    private Integer duration;

    @Column(name = "precio", nullable = false)
    private BigDecimal price;

    @Column(name = "id_usuario_proveedor", nullable = false)
    private Long providerId;
}
