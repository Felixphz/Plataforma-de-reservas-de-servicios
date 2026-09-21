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

import java.util.List;

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

    @GetMapping("/public")
    public ResponseEntity<List<ServiceResponse>> getPublicCatalog(
            @RequestParam(required = false) Long providerId,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double maxPrice) {
        List<ServiceResponse> catalog = serviceService.getPublicCatalog(providerId, category, maxPrice);
        return ResponseEntity.ok(catalog);
    }
}
