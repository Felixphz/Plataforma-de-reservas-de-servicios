package com.udea.service_platform.modules.users.service;

import com.udea.service_platform.modules.users.dto.RoleResponse;
import com.udea.service_platform.modules.users.dto.UserRequest;
import com.udea.service_platform.modules.users.dto.UserResponse;
import com.udea.service_platform.modules.users.model.Role;
import com.udea.service_platform.modules.users.model.User;
import com.udea.service_platform.modules.users.repository.RoleRepository;
import com.udea.service_platform.modules.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse register(UserRequest request) {
        if (userRepository.existsByCorreo(request.getCorreo())) {
            throw new IllegalArgumentException("El correo ya está en uso");
        }

        Role role = roleRepository.findById(request.getIdRol())
                .orElseThrow(() -> new IllegalArgumentException("El rol seleccionado no existe"));

        if (!RoleConstants.ROLES_PERMITIDOS_AUTO_REGISTRO.contains(role.getNombre())) {
            throw new IllegalArgumentException("El rol seleccionado no está permitido para auto-registro");
        }

        User user = User.builder()
                .nombre(request.getNombre())
                .correo(request.getCorreo())
                .password(passwordEncoder.encode(request.getPassword()))
                .apellido(request.getApellido())
                .idTipoDocumento(request.getIdTipoDocumento())
                .numeroDocumento(request.getNumeroDocumento())
                .telefono(request.getTelefono())
                .idCiudad(request.getIdCiudad())
                .idTipoProveedor(request.getIdTipoProveedor())
                .role(role)
                .build();

        User savedUser = userRepository.save(user);

        RoleResponse roleResponse = RoleResponse.builder()
                .idRol(role.getIdRol())
                .nombre(role.getNombre())
                .build();

        return UserResponse.builder()
                .idUsuario(savedUser.getIdUsuario())
                .nombre(savedUser.getNombre())
                .correo(savedUser.getCorreo())
                .password(savedUser.getPassword())
                .apellido(savedUser.getApellido())
                .idTipoDocumento(savedUser.getIdTipoDocumento())
                .numeroDocumento(savedUser.getNumeroDocumento())
                .telefono(savedUser.getTelefono())
                .idCiudad(savedUser.getIdCiudad())
                .idTipoProveedor(savedUser.getIdTipoProveedor())
                .role(roleResponse)
                .build();
    }
}
