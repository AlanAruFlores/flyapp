package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.model.Planificacion;
import com.flybook.librovuelo.model.Programacion;
import com.flybook.librovuelo.repository.ProgramacionRepository;
import com.flybook.librovuelo.service.PlanificacionService;
import com.flybook.librovuelo.service.ProgramacionService;
import com.flybook.librovuelo.service.VueloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProgramacionServiceImpl implements ProgramacionService {

    @Autowired
    private ProgramacionRepository programacionRepository;

    @Autowired
    private VueloService vueloService;

    @Autowired
    private PlanificacionService planificacionService;

    @Override
    public List<Programacion> findAll() {
        return this.programacionRepository.findAll();
    }

    @Override
    public void guardar(Programacion programacion) {
        this.programacionRepository.save(programacion);
    }

    public void guardar(List<Programacion> lista) throws Exception {

        Planificacion planificacion = new Planificacion();
        this.planificacionService.guardar(planificacion);
        for (Programacion programacion : lista) {
            if (lista.get(0).getSTD() != null) {
                if (validate(programacion)) {
                    planificacion.setFecha(LocalDate.from(programacion.getSTD()));
                    programacion.setPlanificacion(planificacion);
                    asignarHorarios(lista);
                    this.programacionRepository.save(programacion);
                }
            } else {
                throw new Exception("Ingrese el primer STD");
            }
        }
    }


    public void asignarHorarios(List<Programacion> lista) {
        for (int i = 0; i < lista.size(); i++) {
            if (validate(lista.get(i))) {
                if (i == 0) {
                    lista.get(i).setSTA(lista.get(i).getSTD().plusMinutes(this.vueloService.tiempoVueloMinutos(lista.get(i).getVuelo().getTv())));
                } else if (i != 0) {
                    lista.get(i).setSTD(lista.get(i - 1).getSTA().plusMinutes(30));
                    lista.get(i).setSTA(lista.get(i).getSTD().plusMinutes(this.vueloService.tiempoVueloMinutos(lista.get(i).getVuelo().getTv())));

                }
            }
        }
    }

    public List<Programacion> obtenerProgramacionesPorPlanificacion(Planificacion planificacion) {
        return this.programacionRepository.findAllByPlanificacion(planificacion);
    }


    public Boolean validate(Programacion programacion) {
        Boolean seGuardo = false;
        if (programacion.getVuelo() != null) {
            seGuardo = true;
        }
        return seGuardo;
    }

    @Override
    public List<Integer> devolverTodosLosNumerosDeProgramacion() {
        List<Programacion> programaciones = this.programacionRepository.findAll();
        List<Integer> numerosDeProgramacion = new ArrayList<>();
        for (Programacion programacion : programaciones) {
            numerosDeProgramacion.add(programacion.getNroDeProgramacion());
        }
        return numerosDeProgramacion = numerosDeProgramacion.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<Programacion> devolverTodasLasProgramacionesPorNumero(Integer numero) {
        return this.programacionRepository.findAllByNroDeProgramacion(numero);
    }

    public List<List<Programacion>> devolverListaDeListasDeProgramaciones() {
        List<List<Programacion>> listaDeListasProgramaciones = new ArrayList<>();
        List<Integer> numeros = devolverTodosLosNumerosDeProgramacion();
        for (Integer numero : numeros) {
            listaDeListasProgramaciones.add(devolverTodasLasProgramacionesPorNumero(numero));
        }
        return listaDeListasProgramaciones;
    }


    @Override
    public Programacion buscarPorID(Long id) {
        return this.programacionRepository.findProgramacionById(id);
    }


}
