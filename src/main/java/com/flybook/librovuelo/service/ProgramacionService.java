package com.flybook.librovuelo.service;


import com.flybook.librovuelo.model.Planificacion;
import com.flybook.librovuelo.model.Programacion;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

public interface ProgramacionService {

    public List<Programacion> findAll();

    void guardar(Programacion programacion) throws ParseException;

    void guardar(List<Programacion> list) throws Exception;

    List<Integer> devolverTodosLosNumerosDeProgramacion();

    List<Programacion> devolverTodasLasProgramacionesPorNumero(Integer numero);

    public List<List<Programacion>> devolverListaDeListasDeProgramaciones();

    Programacion buscarPorID(Long id);

    List<Programacion> obtenerProgramacionesPorPlanificacion(Planificacion planificacion);
}
