package com.flybook.librovuelo.dto;

import com.flybook.librovuelo.model.TipoAusencia;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ResumenDeVacaciones {
    private String nombre;
    private String apellido;
    private Long userId;
    private Integer legajo;
    private Long pedidoId;
    private LocalDate fechaInicioAusencia;
    private LocalDate fechaFinalAusencia;
    private TipoAusencia tipoAusencia;
}
