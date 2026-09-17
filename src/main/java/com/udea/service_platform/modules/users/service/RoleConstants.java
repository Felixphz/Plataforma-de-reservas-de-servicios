package com.udea.service_platform.modules.users.service;

import java.util.Set;

public final class RoleConstants {

    private RoleConstants() {}

    public static final Set<String> ROLES_PERMITIDOS_AUTO_REGISTRO = Set.of(
        "Cliente",
        "Proveedor de Servicios"
    );
}
