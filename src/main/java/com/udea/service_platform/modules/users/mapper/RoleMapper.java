package com.udea.service_platform.modules.users.mapper;

import com.udea.service_platform.modules.users.dto.RoleResponse;
import com.udea.service_platform.modules.users.model.Role;

/**
 * Single Responsibility: owns all Role <-> DTO transformations.
 * Services orchestrate; this class formats.
 */
public final class RoleMapper {

    private RoleMapper() {}

    public static RoleResponse toResponse(Role role) {
        return RoleResponse.builder()
                .idRol(role.getIdRol())
                .nombre(role.getNombre())
                .build();
    }
}
