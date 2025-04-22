package com.flybook.librovuelo.repository;

import com.flybook.librovuelo.model.CicloVacaciones;
import com.flybook.librovuelo.model.Generacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface CicloVacacionesRepository extends JpaRepository<CicloVacaciones, Long> {
    CicloVacaciones findCicloVacacionesByNumeroDeCicloAndGeneracion(Integer numeroCiclo, Generacion generacion);

    List <CicloVacaciones> findAllByGeneracion(Generacion generacion);

    List <CicloVacaciones> findAllByNumeroDeCiclo(Integer numeroDeCiclo);

    Page<CicloVacaciones> findAll(Pageable pageable);

    //CicloVacaciones findByGeneracionAndComienzoCicloIsGreaterThanEqualAndFinalizacionCicloIsLessThanEqual(Generacion generacion, LocalDate fechaDesde, LocalDate fechaHasta);

    CicloVacaciones findByGeneracionAndComienzoCicloIsLessThanEqualAndFinalizacionCicloIsGreaterThanEqual(Generacion generacion, LocalDate fechaDesde, LocalDate fechaHasta);

//    CicloVacaciones findAllByGeneracionAndComienzoCicloLessThanEqualAndComienzoCicloIsGreaterThanEqual(Generacion generacion, LocalDate fechaDEsde, LocalDate fechaHasta);



}
