package com.flybook.librovuelo.repository;

import com.flybook.librovuelo.model.Planificacion;
import com.flybook.librovuelo.model.Programacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ProgramacionRepository extends JpaRepository<Programacion, Long> {

    List<Programacion> findAllByNroDeProgramacion(Integer numero);

    Programacion findProgramacionById(Long programacionId);


    List<Programacion> findAllByPlanificacion(Planificacion planificacion);
}
