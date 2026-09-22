package com.udea.service_platform.modules.users.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String password;

    private String apellido;

    private Long idTipoDocumento;

    private String numeroDocumento;

    private String telefono;

    private Long idCiudad;

    private Long idTipoProveedor;

    @NotNull(message = "Debe seleccionar un rol")
    private Long idRol;
}
