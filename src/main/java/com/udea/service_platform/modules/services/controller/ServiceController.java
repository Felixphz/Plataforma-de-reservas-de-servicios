package com.udea.service_platform.modules.services.controller;

import com.udea.service_platform.modules.core.security.UserDetailsImpl;
import com.udea.service_platform.modules.services.dto.ServiceRequest;
import com.udea.service_platform.modules.services.dto.ServiceResponse;
import com.udea.service_platform.modules.services.service.ServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {

    private final ServiceService serviceService;

    @PostMapping
    @PreAuthorize("hasRole('PROVEEDOR')")
    public ResponseEntity<ServiceResponse> createService(
            @Valid @RequestBody ServiceRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ServiceResponse response = serviceService.createService(request, userDetails.getIdUsuario());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
