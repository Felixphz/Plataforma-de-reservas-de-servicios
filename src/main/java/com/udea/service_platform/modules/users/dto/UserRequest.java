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

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotNull(message = "Debe especificar el tipo de documento")
    private Long idTipoDocumento;

    @NotBlank(message = "El número de documento es obligatorio")
    private String numeroDocumento;

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    @NotNull(message = "Debe especificar la ciudad")
    private Long idCiudad;

    @NotNull(message = "Debe especificar el tipo de proveedor")
    private Long idTipoProveedor;

    @NotNull(message = "Debe seleccionar un rol")
    private Long idRol;
}
