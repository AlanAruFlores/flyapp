package com.flybook.librovuelo.conf.security;

public enum WebUserPermission {
    AEROPUERTO_READ("aeropuerto:read"),
    AEROPUERTO_WRITE("aeropuerto:write"),
    AVION_READ("avion:read"),
    AVION_WRITE("avion:write"),
    ESTACION_READ("estacion:read"),
    ESTACION_WRITE("estacion:write"),
    HORARIO_READ("horario:read"),
    HORARIO_WRITE("horario:write"),
    ROLE_READ("role:read"),
    ROLE_WRITE("role:write"),
    USER_READ("user:read"),
    USER_WRITE("user:write"),
    VUELO_READ("vuelo:read"),
    VUELO_WRITE("vuelo:write");

    private final String permission;

    WebUserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
