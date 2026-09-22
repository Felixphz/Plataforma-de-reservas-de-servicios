package com.udea.service_platform.modules.reservations.service;

import com.udea.service_platform.modules.reservations.dto.ReservaRequest;
import com.udea.service_platform.modules.reservations.dto.ReservaResponse;
import com.udea.service_platform.modules.reservations.model.Reserva;
import com.udea.service_platform.modules.reservations.repository.ReservaRepository;
import com.udea.service_platform.modules.services.repository.ServiceRepository;
import com.udea.service_platform.modules.users.model.User;
import com.udea.service_platform.modules.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;

    public ReservaResponse createReservation(ReservaRequest request, Long clientUserId) {
        com.udea.service_platform.modules.services.model.Service servicio =
                serviceRepository.findById(request.getIdServicio())
                        .orElseThrow(() -> new IllegalArgumentException("El servicio no existe"));

        if (!servicio.getActivo()) {
            throw new IllegalArgumentException("El servicio no está activo");
        }

        if (servicio.getIdProveedor().equals(clientUserId)) {
            throw new IllegalArgumentException("No puedes reservar tu propio servicio");
        }

        LocalTime fechaInicio;
        try {
            fechaInicio = LocalTime.parse(request.getFechaInicio());
        } catch (Exception e) {
            throw new IllegalArgumentException("Formato de hora inválido. Use HH:mm (ej. 14:30)");
        }

        Reserva reserva = Reserva.builder()
                .servicio(servicio)
                .idUsuario(clientUserId)
                .fechaInicio(fechaInicio)
                .idEstado(1)
                .build();

        Reserva saved = reservaRepository.save(reserva);

        User client = userRepository.findById(clientUserId).orElse(null);

        return ReservaResponse.builder()
                .id(saved.getId())
                .servicioNombre(servicio.getNombre())
                .clienteNombre(client != null ? client.getNombre() + " " + client.getApellido() : "Unknown")
                .clienteCorreo(client != null ? client.getCorreo() : "")
                .fechaInicio(saved.getFechaInicio())
                .estado("ACTIVA")
                .build();
    }

    @Transactional(readOnly = true)
    public Page<ReservaResponse> getProviderReservations(Long providerId, Pageable pageable) {
        return reservaRepository.findByProviderIdOrderByFechaInicioDesc(providerId, pageable)
                .map(reserva -> {
                    User client = userRepository.findById(reserva.getIdUsuario()).orElse(null);
                    return ReservaResponse.builder()
                            .id(reserva.getId())
                            .servicioNombre(reserva.getServicio().getNombre())
                            .clienteNombre(client != null ? client.getNombre() + " " + client.getApellido() : "Unknown")
                            .clienteCorreo(client != null ? client.getCorreo() : "")
                            .fechaInicio(reserva.getFechaInicio())
                            .estado(reserva.getIdEstado() == 1 ? "ACTIVA" : "CANCELADA")
                            .build();
                });
    }
}
