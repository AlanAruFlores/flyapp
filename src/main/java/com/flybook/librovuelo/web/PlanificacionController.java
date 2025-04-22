package com.flybook.librovuelo.web;

import com.flybook.librovuelo.model.Planificacion;
import com.flybook.librovuelo.model.Programacion;
import com.flybook.librovuelo.service.PlanificacionService;
import com.flybook.librovuelo.service.ProgramacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller

public class PlanificacionController {
    @Autowired
    private ProgramacionService programacionService;

    @Autowired
    private PlanificacionService planificacionService;

    @GetMapping({"/operaciones/planificaciones"})
    public String mostrarPlanificaciones(Model model) {

        List<Planificacion> planificaciones = this.planificacionService.devolverListasDePlanificaciones();


        model.addAttribute("planificaciones", planificaciones);
        return "planificaciones";
    }

    @GetMapping({"/operaciones/planificaciones/ver/{id}"})
    public String verPlanificacion(Model model, @PathVariable("id") Long id) {
        Planificacion planificacion = this.planificacionService.getPlanificacionById(id);
        List<Programacion> listaDeProgramaciones = this.programacionService.obtenerProgramacionesPorPlanificacion(this.planificacionService.getPlanificacionById(id));

        model.addAttribute("listaDeProgramaciones", listaDeProgramaciones);
        model.addAttribute("planificacion", planificacion);
        return "planificaciones-ver";
    }


    @GetMapping({"/operaciones/planificaciones/ver-tripulantes/{id}"})
    public String verTripulantes(Model model, @PathVariable("id") Long id) {
        Programacion programacion = this.programacionService.buscarPorID(id);


        model.addAttribute("programacion", programacion);
        return "programaciones-ver-tripulantes";
    }
}
