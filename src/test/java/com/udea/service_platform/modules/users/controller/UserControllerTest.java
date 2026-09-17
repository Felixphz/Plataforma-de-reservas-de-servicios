package com.udea.service_platform.modules.users.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.udea.service_platform.modules.users.dto.RoleResponse;
import com.udea.service_platform.modules.users.dto.UserRequest;
import com.udea.service_platform.modules.users.dto.UserResponse;
import com.udea.service_platform.modules.users.service.RoleService;
import com.udea.service_platform.modules.users.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void register_withValidRequest_returns201() throws Exception {
        UserRequest request = UserRequest.builder()
                .nombre("Juan")
                .correo("juan@test.com")
                .password("password123")
                .idRol(1L)
                .build();

        RoleResponse roleResponse = RoleResponse.builder().idRol(1L).nombre("Cliente").build();
        UserResponse userResponse = UserResponse.builder()
                .idUsuario(1L)
                .nombre("Juan")
                .correo("juan@test.com")
                .role(roleResponse)
                .build();

        when(userService.register(any(UserRequest.class))).thenReturn(userResponse);

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.correo").value("juan@test.com"))
                .andExpect(jsonPath("$.role.nombre").value("Cliente"));
    }

    @Test
    void register_withDuplicateEmail_returns400() throws Exception {
        UserRequest request = UserRequest.builder()
                .nombre("Juan")
                .correo("existing@test.com")
                .password("password123")
                .idRol(1L)
                .build();

        when(userService.register(any(UserRequest.class)))
                .thenThrow(new IllegalArgumentException("El correo ya está en uso"));

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("El correo ya está en uso"));
    }

    @Test
    void register_withForbiddenRole_returns400() throws Exception {
        UserRequest request = UserRequest.builder()
                .nombre("Admin")
                .correo("admin@test.com")
                .password("password123")
                .idRol(3L)
                .build();

        when(userService.register(any(UserRequest.class)))
                .thenThrow(new IllegalArgumentException("El rol seleccionado no está permitido para auto-registro"));

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("El rol seleccionado no está permitido para auto-registro"));
    }

    @Test
    void register_withIdRolNull_returns400() throws Exception {
        String requestJson = """
                {
                    "nombre": "Juan",
                    "correo": "juan@test.com",
                    "password": "password123",
                    "idRol": null
                }
                """;

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.fields.idRol").value("Debe seleccionar un rol"));
    }

    @Test
    void register_withMissingFields_returns400() throws Exception {
        String requestJson = """
                {
                    "nombre": "",
                    "correo": "",
                    "password": "",
                    "idRol": null
                }
                """;

        mockMvc.perform(post("/api/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest());
    }
}
