package com.flybook.librovuelo.service;

import com.flybook.librovuelo.model.Planificacion;

import java.util.List;

public interface PlanificacionService {
    List<Planificacion> devolverListasDePlanificaciones();

    void guardar(Planificacion planificacion);

    Planificacion getPlanificacionById(Long id);


}
