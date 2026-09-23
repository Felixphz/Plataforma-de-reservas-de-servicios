package com.udea.service_platform.modules.users.service;

import com.udea.service_platform.modules.users.dto.RoleResponse;
import com.udea.service_platform.modules.users.mapper.RoleMapper;
import com.udea.service_platform.modules.users.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public List<RoleResponse> findAll() {
        return roleRepository.findAll().stream()
                .filter(role -> RoleConstants.ROLES_PERMITIDOS_AUTO_REGISTRO.contains(role.getNombre()))
                .map(RoleMapper::toResponse)
                .toList();
    }
}
