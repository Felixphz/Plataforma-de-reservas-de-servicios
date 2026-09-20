package com.udea.service_platform.modules.users.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    private String jwt;
    private Long idUsuario;
    private String nombre;
    private String correo;
    private String rol;
}
