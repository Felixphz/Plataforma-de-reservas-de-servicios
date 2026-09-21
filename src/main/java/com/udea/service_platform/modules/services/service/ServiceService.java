package com.udea.service_platform.modules.services.service;

import com.udea.service_platform.modules.services.dto.ServiceRequest;
import com.udea.service_platform.modules.services.dto.ServiceResponse;
import com.udea.service_platform.modules.services.model.Service;
import com.udea.service_platform.modules.services.repository.ServiceRepository;
import com.udea.service_platform.modules.services.repository.ServiceSpecification;
import lombok.RequiredArgsConstructor;

import java.util.List;

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
                .active(request.getActive() != null ? request.getActive() : true)
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
                .active(savedService.getActive())
                .build();
    }

    public List<ServiceResponse> getPublicCatalog(Long providerId, String category, Double maxPrice) {
        var spec = ServiceSpecification.buildFilter(providerId, category, maxPrice);
        return serviceRepository.findAll(spec).stream()
                .map(service -> ServiceResponse.builder()
                        .id(service.getId())
                        .nombre(service.getNombre())
                        .descripcion(service.getDescripcion())
                        .categoria(service.getCategoria())
                        .duracion(service.getDuracion())
                        .precio(service.getPrecio())
                        .idProveedor(service.getIdProveedor())
                        .idRecursos(service.getIdRecursos())
                        .active(service.getActive())
                        .build())
                .toList();
    }
}
