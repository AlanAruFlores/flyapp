package com.flybook.librovuelo.exceptions;


import com.flybook.librovuelo.model.Ausencia;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UsuariosNoAsigandosRecurrentException extends Exception {

    private List<Ausencia> asenciasDeUsuariosQueNoPuedenAgregarseAUnRecurrente;

    public UsuariosNoAsigandosRecurrentException(List<Ausencia> asenciasDeUsuariosQueNoPuedenAgregarseAUnRecurrente ){
        super ("Estos Usuarios no pudieron asignarse al recurrent ya que poseen una ausencia para el periodo del recurrent");
        this.asenciasDeUsuariosQueNoPuedenAgregarseAUnRecurrente=asenciasDeUsuariosQueNoPuedenAgregarseAUnRecurrente;
    }

}
