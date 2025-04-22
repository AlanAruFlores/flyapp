package com.flybook.librovuelo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DatosNotificacionUsuario {

    private Long idVueloRealizado;
    private List<Long> idsUsuarios;

}
