package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.dto.HabilitarVacaciones;
import com.flybook.librovuelo.exceptions.FechaDesdePosteriorAFechaHastaException;
import com.flybook.librovuelo.repository.HabilitarVacacionesRepository;
import com.flybook.librovuelo.service.HabilitarVacacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HabilitarVacacionesServiceImpl implements HabilitarVacacionesService {

    private final HabilitarVacacionesRepository habilitarVacacionesRepository;

    @Autowired
    public HabilitarVacacionesServiceImpl(HabilitarVacacionesRepository habilitarVacacionesRepository) {
        this.habilitarVacacionesRepository = habilitarVacacionesRepository;
    }

    @Override
    public void guardarHabilitarVacaciones(HabilitarVacaciones habilitarVacaciones) throws FechaDesdePosteriorAFechaHastaException {

        if (habilitarVacaciones.getHabilitarPedidoDeVacacionesDesde().isAfter(habilitarVacaciones.getHabilitarPedidoDeVacacionesHasta())) {
            throw new FechaDesdePosteriorAFechaHastaException("La fecha desde no puede ser posterior a la fecha hasta.");
        }

        this.habilitarVacacionesRepository.save(habilitarVacaciones);
    }

}
