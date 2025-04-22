package com.flybook.librovuelo.service;

import com.flybook.librovuelo.dto.HabilitarVacaciones;
import com.flybook.librovuelo.exceptions.FechaDesdePosteriorAFechaHastaException;

public interface HabilitarVacacionesService {

    void guardarHabilitarVacaciones(HabilitarVacaciones habilitarVacaciones) throws FechaDesdePosteriorAFechaHastaException;
}
