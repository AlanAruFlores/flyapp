package com.flybook.librovuelo.conf.security;

import java.util.Set;
import com.google.common.collect.Sets;

import static com.flybook.librovuelo.conf.security.WebUserPermission.*;

public enum WebUserRole {
    ADMINISTRADOR(Sets.newHashSet(AEROPUERTO_READ, AEROPUERTO_WRITE, AVION_READ, AVION_WRITE, ESTACION_READ, ESTACION_WRITE, HORARIO_READ, HORARIO_WRITE, ROLE_READ, ROLE_WRITE, USER_READ, USER_WRITE, VUELO_READ, VUELO_WRITE)),
    TRIPULANTE(Sets.newHashSet()),
    LIDER(Sets.newHashSet()),
    OPERACIONES(Sets.newHashSet()),
    INSTRUCTOR(Sets.newHashSet());

    private final Set<WebUserPermission> permissions;

    WebUserRole(Set<WebUserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<WebUserPermission> getPermissions() {
        return permissions;
    }
}
