package com.flybook.librovuelo.service;


import com.flybook.librovuelo.model.CicloVacaciones;
import com.flybook.librovuelo.model.Generacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface CicloVacacionesService {
    List<CicloVacaciones> obtenerTodosLosCiclos();
    void save(CicloVacaciones cicloVacaciones) throws Exception;
    void update(CicloVacaciones cicloVacaciones);

    void deleteId(Long id);
    CicloVacaciones getById(Long id);
    List<CicloVacaciones> findAll();

    CicloVacaciones findById(Long id);

    List<CicloVacaciones> obtenerTodosLosCiclosParaUnaGeneracion(Generacion generacion);

    List <CicloVacaciones> findAllByGeneracionOrderByNumeroDeCicloDesc(Generacion generacion);

    List <CicloVacaciones> findAllByGeneracion(Generacion generacion);

    List<CicloVacaciones> obtenerTodosLosPedidoParaUnNumeroDeCiclo(Integer ciclo);

     Page<CicloVacaciones> findAll(Pageable pageable);

    CicloVacaciones obtenerCicloDeVacacionesParaUnageneracionYUnaFechaDada(Generacion generacion, LocalDate Fecha);

}

