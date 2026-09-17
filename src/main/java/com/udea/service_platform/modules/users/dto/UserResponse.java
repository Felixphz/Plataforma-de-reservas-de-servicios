package com.udea.service_platform.modules.users.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {

    private Long idUsuario;
    private String nombre;
    private String correo;

    @JsonIgnore
    private String password;

    private String apellido;
    private Long idTipoDocumento;
    private String numeroDocumento;
    private String telefono;
    private Long idCiudad;
    private Long idTipoProveedor;
    private RoleResponse role;
}
