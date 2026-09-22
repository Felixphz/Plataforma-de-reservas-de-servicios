package com.udea.service_platform.modules.users.service;

import com.udea.service_platform.modules.users.dto.RoleResponse;
import com.udea.service_platform.modules.users.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<RoleResponse> findAll() {
        return roleRepository.findAll().stream()
                .map(r -> RoleResponse.builder()
                        .idRol(r.getIdRol())
                        .nombre(r.getNombre())
                        .build())
                .toList();
    }
}
