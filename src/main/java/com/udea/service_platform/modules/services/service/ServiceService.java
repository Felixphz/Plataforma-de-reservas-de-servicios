package com.udea.service_platform.modules.services.service;

import com.udea.service_platform.modules.services.dto.ServiceRequest;
import com.udea.service_platform.modules.services.dto.ServiceResponse;
import com.udea.service_platform.modules.services.model.Service;
import com.udea.service_platform.modules.services.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceService {

    private final ServiceRepository serviceRepository;

    public ServiceResponse createService(ServiceRequest request) {
        Service service = Service.builder()
                .name(request.getName())
                .description(request.getDescription())
                .duration(request.getDuration())
                .price(request.getPrice())
                .providerId(request.getProviderId())
                .build();

        Service savedService = serviceRepository.save(service);

        return ServiceResponse.builder()
                .id(savedService.getId())
                .name(savedService.getName())
                .description(savedService.getDescription())
                .duration(savedService.getDuration())
                .price(savedService.getPrice())
                .providerId(savedService.getProviderId())
                .build();
    }
}
