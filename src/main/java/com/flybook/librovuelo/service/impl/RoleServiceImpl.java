package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.model.Role;

import com.flybook.librovuelo.repository.GeneracionRepository;

import com.flybook.librovuelo.repository.RoleRepository;
import com.flybook.librovuelo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;



@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Override
    public List<Role> obtenerTodosLosRoles() {
        return roleRepository.findAll();
    }
    @Override
    public Role obtenerRolPorNombre(String name) {
        return roleRepository.findRoleByName(name);
    }

}
