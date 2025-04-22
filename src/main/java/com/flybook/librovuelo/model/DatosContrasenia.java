package com.flybook.librovuelo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;

@Getter
@Setter
public class DatosContrasenia {

    private String username;
    private String currentPassword;
    private String password;
    private String passwordConfirm;
}
