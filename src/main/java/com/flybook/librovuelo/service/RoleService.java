package com.flybook.librovuelo.service;

import com.flybook.librovuelo.model.Role;

import java.util.List;

public interface RoleService {



    List<Role> obtenerTodosLosRoles();




    Role obtenerRolPorNombre(String name);
}

