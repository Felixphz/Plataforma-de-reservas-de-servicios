package com.udea.service_platform.modules.users.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "\"Usuarios\"")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "correo", unique = true, nullable = false)
    private String correo;

    @Column(name = "password_hash", nullable = false)
    private String password;

    @Column(name = "id_tipo_documento")
    private Long idTipoDocumento;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "numero_documento")
    private String numeroDocumento;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "id_ciudad")
    private Long idCiudad;

    @Column(name = "id_tipo_proveedor")
    private Long idTipoProveedor;

    @Column(name = "estado_cuenta", nullable = false)
    @Builder.Default
    private String estadoCuenta = "ACTIVA";

    @Column(name = "notas_especiales")
    private String notasEspeciales;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol", referencedColumnName = "id_rol", nullable = false)
    private Role role;
}
