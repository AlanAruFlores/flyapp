package com.flybook.librovuelo.dto;

import com.flybook.librovuelo.model.CicloVacaciones;
import com.flybook.librovuelo.model.Periodo;
import com.flybook.librovuelo.model.TipoAusencia;
import com.flybook.librovuelo.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SugerenciaVacacion {

    private CicloVacaciones cicloVacaciones;
    private User user;
    private Periodo periodo;
    private TipoAusencia tipoAusencia;
    private Integer cantidadCupos;

}
