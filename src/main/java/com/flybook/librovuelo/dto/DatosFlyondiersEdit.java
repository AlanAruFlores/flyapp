package com.flybook.librovuelo.dto;

import com.flybook.librovuelo.model.Generacion;
import com.flybook.librovuelo.model.Role;
import com.flybook.librovuelo.model.TipoCargo;
import com.flybook.librovuelo.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class DatosFlyondiersEdit {

    private Long id;
    private TipoCargo tipoCargo;
    private User lider;
    private Generacion generacion;
    private Set<Role> roles;

}
