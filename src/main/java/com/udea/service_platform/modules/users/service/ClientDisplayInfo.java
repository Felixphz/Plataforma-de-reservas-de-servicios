package com.udea.service_platform.modules.users.service;

/**
 * Published contract of the users module for other modules.
 * Exposes minimal client display data without leaking the
 * User entity or UserRepository across module boundaries.
 */
public record ClientDisplayInfo(
        Long idUsuario,
        String nombreCompleto,
        String correo
) {}
