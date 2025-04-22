package com.flybook.librovuelo.service;

import com.flybook.librovuelo.model.Ausencia;
import com.flybook.librovuelo.model.TipoCargo;
import com.flybook.librovuelo.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface UtilsService {
    Integer cantidadDeVacacionesAsignadasEntreDosFechasPorCargo(LocalDate fechaDesde, LocalDate fechaHasta, TipoCargo tipoCargo) ;

    List<Ausencia> obtenerTodasLasAusenciasDeUnUsuarioEntreDosFechas(User user, LocalDate fechaDesdePrimeraQuincenaPlanA, LocalDate fechaHastaPrimeraQuincenaPlanA);
}
