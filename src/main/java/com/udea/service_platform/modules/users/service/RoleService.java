package com.udea.service_platform.modules.users.service;

import com.udea.service_platform.modules.users.dto.RoleResponse;

import java.util.List;

/**
 * Dependency Inversion: controllers depend on this abstraction,
 * not on the concrete implementation.
 */
public interface RoleService {

    List<RoleResponse> findAll();
}
