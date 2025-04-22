package com.flybook.librovuelo.repository;


import com.flybook.librovuelo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long>{

   // List<Role> findAllRole ();

    Role findRoleByName(String name);
}
