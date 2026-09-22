package com.udea.service_platform.modules.reservations.repository;

import com.udea.service_platform.modules.reservations.model.Reserva;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    Page<Reserva> findByIdUsuarioOrderByFechaInicioDesc(Long idUsuario, Pageable pageable);

    @Query("SELECT r FROM Reserva r JOIN r.servicio s WHERE s.idProveedor = :providerId ORDER BY r.fechaInicio DESC")
    Page<Reserva> findByProviderIdOrderByFechaInicioDesc(@Param("providerId") Long providerId, Pageable pageable);
}
