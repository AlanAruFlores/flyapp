package com.flybook.librovuelo.repository;


import com.flybook.librovuelo.model.Periodo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface PeriodoRepository extends JpaRepository<Periodo,Integer> {

    List<Periodo> findAllByAnio(Integer anio);

    Periodo findById(Long id);

    Periodo findPeriodoByFechaInicioIsLessThanEqualAndFechaFinalizacionIsGreaterThanEqual(LocalDate fechaInicio, LocalDate fechaFinalizacion);

    List<Periodo> findAllByFechaInicioIsGreaterThanEqualAndFechaFinalizacionIsLessThanEqualOrderByFechaInicioAsc(LocalDate fechaInicio, LocalDate fechaFinalizacion);
    //  Periodo findByFechaDesdeIsGreaterThanEqualAndFechaHastaIsLessThanEqual(LocalDate fechaDesde, LocalDate fechaHasta);

//    Periodo findByFechaInicioGreaterThanEqualAndFechaFinalizacionLessThanEqual(LocalDate fechaInicio, LocalDate fechaFinalizacion);
}
