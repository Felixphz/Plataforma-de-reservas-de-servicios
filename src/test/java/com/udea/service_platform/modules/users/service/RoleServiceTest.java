package com.udea.service_platform.modules.users.service;

import com.udea.service_platform.modules.users.dto.RoleResponse;
import com.udea.service_platform.modules.users.model.Role;
import com.udea.service_platform.modules.users.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role clienteRole;
    private Role proveedorRole;
    private Role adminRole;

    @BeforeEach
    void setUp() {
        clienteRole = Role.builder().idRol(1L).nombre("Cliente").build();
        proveedorRole = Role.builder().idRol(2L).nombre("Proveedor de Servicios").build();
        adminRole = Role.builder().idRol(3L).nombre("Administrador").build();
    }

    @Test
    void findAll_returnsOnlyPublicRoles() {
        when(roleRepository.findAll()).thenReturn(List.of(clienteRole, proveedorRole, adminRole));

        List<RoleResponse> result = roleService.findAll();

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(r ->
                RoleConstants.ROLES_PERMITIDOS_AUTO_REGISTRO.contains(r.getNombre())));
        assertTrue(result.stream().anyMatch(r -> r.getNombre().equals("Cliente")));
        assertTrue(result.stream().anyMatch(r -> r.getNombre().equals("Proveedor de Servicios")));
        assertFalse(result.stream().anyMatch(r -> r.getNombre().equals("Administrador")));
    }

    @Test
    void findAll_whenOnlyPublicRolesExist_returnsAll() {
        when(roleRepository.findAll()).thenReturn(List.of(clienteRole, proveedorRole));

        List<RoleResponse> result = roleService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void findAll_whenNoRolesExist_returnsEmptyList() {
        when(roleRepository.findAll()).thenReturn(List.of());

        List<RoleResponse> result = roleService.findAll();

        assertTrue(result.isEmpty());
    }
}
