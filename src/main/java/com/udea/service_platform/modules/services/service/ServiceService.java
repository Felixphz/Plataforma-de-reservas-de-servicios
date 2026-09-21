package com.udea.service_platform.modules.services.service;

import com.udea.service_platform.modules.services.dto.ServiceRequest;
import com.udea.service_platform.modules.services.dto.ServiceResponse;
import com.udea.service_platform.modules.services.model.Service;
import com.udea.service_platform.modules.services.repository.ServiceRepository;
import com.udea.service_platform.modules.services.repository.ServiceSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceService {

    private final ServiceRepository serviceRepository;

    public ServiceResponse createService(ServiceRequest request, Long idProveedor) {
        Service service = Service.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .categoria(request.getCategoria())
                .duracion(request.getDuracion())
                .precio(request.getPrecio())
                .idProveedor(idProveedor)
                .idRecursos(request.getIdRecursos())
                .activo(true)
                .build();

        Service savedService = serviceRepository.save(service);

        return ServiceResponse.builder()
                .id(savedService.getId())
                .nombre(savedService.getNombre())
                .descripcion(savedService.getDescripcion())
                .categoria(savedService.getCategoria())
                .duracion(savedService.getDuracion())
                .precio(savedService.getPrecio())
                .idProveedor(savedService.getIdProveedor())
                .idRecursos(savedService.getIdRecursos())
                .activo(savedService.getActivo())
                .build();
    }

    public Page<ServiceResponse> getPublicCatalog(Long providerId, String category, BigDecimal minPrice, BigDecimal maxPrice, Pageable pageable) {
        var spec = ServiceSpecification.buildFilter(providerId, category, minPrice, maxPrice);
        return serviceRepository.findAll(spec, pageable)
                .map(service -> ServiceResponse.builder()
                        .id(service.getId())
                        .nombre(service.getNombre())
                        .descripcion(service.getDescripcion())
                        .categoria(service.getCategoria())
                        .duracion(service.getDuracion())
                        .precio(service.getPrecio())
                        .idProveedor(service.getIdProveedor())
                        .idRecursos(service.getIdRecursos())
                        .activo(service.getActivo())
                        .build());
    }
}
