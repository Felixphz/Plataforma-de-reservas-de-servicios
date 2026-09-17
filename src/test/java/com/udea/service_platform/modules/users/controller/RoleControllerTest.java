package com.udea.service_platform.modules.users.controller;

import com.udea.service_platform.modules.users.dto.RoleResponse;
import com.udea.service_platform.modules.users.service.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoleController.class)
class RoleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RoleService roleService;

    @Test
    void findAll_returnsPublicRoles() throws Exception {
        List<RoleResponse> roles = List.of(
                RoleResponse.builder().idRol(1L).nombre("Cliente").build(),
                RoleResponse.builder().idRol(2L).nombre("Proveedor de Servicios").build()
        );

        when(roleService.findAll()).thenReturn(roles);

        mockMvc.perform(get("/api/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nombre").value("Cliente"))
                .andExpect(jsonPath("$[1].nombre").value("Proveedor de Servicios"));
    }

    @Test
    void findAll_excludesAdminRole() throws Exception {
        List<RoleResponse> roles = List.of(
                RoleResponse.builder().idRol(1L).nombre("Cliente").build(),
                RoleResponse.builder().idRol(2L).nombre("Proveedor de Servicios").build()
        );

        when(roleService.findAll()).thenReturn(roles);

        mockMvc.perform(get("/api/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.nombre == 'Administrador')]").doesNotExist());
    }

    @Test
    void findAll_whenNoRoles_returnsEmptyArray() throws Exception {
        when(roleService.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
