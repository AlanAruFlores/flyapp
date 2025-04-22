package com.flybook.librovuelo.service;

import com.flybook.librovuelo.model.Estacion;
import com.flybook.librovuelo.model.HorarioPorEstacion;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;


public interface HorarioPorEstacionService {
      Estacion obtenerEstacionDeUnaFecha(LocalDate fecha);
      void guardar(HorarioPorEstacion horarioPorEstacion);
      HorarioPorEstacion obtenerHorarioPorEstacionById(Long id);
      HorarioPorEstacion obtenerHorarioPorEstacionByEstacion (Estacion estacion);

}
