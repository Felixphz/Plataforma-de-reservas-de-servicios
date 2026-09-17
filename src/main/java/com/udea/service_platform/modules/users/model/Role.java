package com.udea.service_platform.modules.users.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "\"Roles\"")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Long idRol;

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;
}
