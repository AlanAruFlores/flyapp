package com.flybook.librovuelo.service;

import com.flybook.librovuelo.dto.SugerenciaVacacion;
import com.flybook.librovuelo.model.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface PeriodoService {

     Periodo obtenerElPeriodoParaUnaFecha(LocalDate fecha);

    List<Periodo> mostrarTodosLosPeriodos();

    Periodo buscarPorID(Long numero);

    List<Periodo> buscarPorAnio(Integer anio);

    void generarPeriodos(Integer ciclo) throws Exception;

    void save(Periodo periodo);

    Periodo actualizarCupos(Periodo periodo);

    List<Integer> devolverTodosLosAnios();

    Periodo getByAnioAndFechaInicioBeforeAndFechaFinalizacionAfter(Integer anio, LocalDate desde, LocalDate hasta);

    List<SugerenciaVacacion> obtenerSugerenciasVacacionesPorTipoCargo(PedidoVacaciones pedidoVacaciones, User usuarioVacacion, TipoAusencia tipoAusencia, TipoCargo tipoCargo) ;

   // List<SugerenciaVacacion> obtenerSugerenciasVacaciones(PedidoVacaciones pedidoVacaciones, User usuarioVacacion, TipoAusencia tipoAusencia);
}