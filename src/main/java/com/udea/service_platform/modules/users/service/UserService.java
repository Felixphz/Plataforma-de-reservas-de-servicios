package com.udea.service_platform.modules.users.service;

import com.udea.service_platform.modules.reservations.repository.ReservaRepository;
import com.udea.service_platform.modules.users.dto.*;
import com.udea.service_platform.modules.users.model.Role;
import com.udea.service_platform.modules.users.model.User;
import com.udea.service_platform.modules.users.repository.ClientSpecification;
import com.udea.service_platform.modules.users.repository.RoleRepository;
import com.udea.service_platform.modules.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ReservaRepository reservaRepository;
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

    public Page<ClientSummaryResponse> searchClients(String searchTerm, Pageable pageable) {
        var spec = ClientSpecification.buildFilter(searchTerm);
        return userRepository.findAll(spec, pageable)
                .map(user -> ClientSummaryResponse.builder()
                        .idUsuario(user.getIdUsuario())
                        .nombre(user.getNombre())
                        .apellido(user.getApellido())
                        .correo(user.getCorreo())
                        .telefono(user.getTelefono())
                        .estadoCuenta(user.getEstadoCuenta())
                        .build());
    }

    public ClientDetailResponse getClientDetail(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (!"Cliente".equals(user.getRole().getNombre())) {
            throw new IllegalArgumentException("El usuario no tiene rol de Cliente");
        }

        return ClientDetailResponse.builder()
                .idUsuario(user.getIdUsuario())
                .nombre(user.getNombre())
                .apellido(user.getApellido())
                .correo(user.getCorreo())
                .telefono(user.getTelefono())
                .numeroDocumento(user.getNumeroDocumento())
                .idTipoDocumento(user.getIdTipoDocumento())
                .idCiudad(user.getIdCiudad())
                .estadoCuenta(user.getEstadoCuenta())
                .notasEspeciales(user.getNotasEspeciales())
                .build();
    }

    public Page<ReservaSummaryResponse> getClientReservations(Long clienteId, Pageable pageable) {
        User user = userRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (!"Cliente".equals(user.getRole().getNombre())) {
            throw new IllegalArgumentException("El usuario no tiene rol de Cliente");
        }

        return reservaRepository.findByIdUsuarioOrderByFechaInicioDesc(clienteId, pageable)
                .map(reserva -> ReservaSummaryResponse.builder()
                        .id(reserva.getId())
                        .servicioNombre(reserva.getServicio().getNombre())
                        .fechaInicio(reserva.getFechaInicio())
                        .estado("ACTIVA")
                        .build());
    }
}
