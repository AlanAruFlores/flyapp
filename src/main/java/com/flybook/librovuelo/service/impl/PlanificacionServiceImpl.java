package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.model.Planificacion;
import com.flybook.librovuelo.model.Programacion;
import com.flybook.librovuelo.repository.PlanificacionRepository;
import com.flybook.librovuelo.service.PlanificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanificacionServiceImpl implements PlanificacionService {

    @Autowired
    public PlanificacionRepository planificacionRepository;


    @Override
    public List<Planificacion> devolverListasDePlanificaciones() {
        return this.planificacionRepository.findAll();
    }

    @Override
    public void guardar(Planificacion planificacion) {
        this.planificacionRepository.save(planificacion);
    }

    @Override
    public Planificacion getPlanificacionById(Long id) {
        return this.planificacionRepository.getById(id);
    }
}
