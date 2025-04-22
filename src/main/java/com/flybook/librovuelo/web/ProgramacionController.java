package com.flybook.librovuelo.web;


import com.flybook.librovuelo.dto.ListaDeProgramaciones;
import com.flybook.librovuelo.model.*;

import com.flybook.librovuelo.service.AvionService;
import com.flybook.librovuelo.service.ProgramacionService;
import com.flybook.librovuelo.service.UserService;
import com.flybook.librovuelo.service.VueloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ProgramacionController {

    @Autowired
    private ProgramacionService programacionService;
    @Autowired
    private VueloService vueloService;


    @GetMapping({"/operaciones/programaciones"})
    public String mostrarProgramaciones(Model model) {

        List<List<Programacion>> listaDeListasProgramaciones = this.programacionService.devolverListaDeListasDeProgramaciones();


        model.addAttribute("listaDeListasProgramaciones", listaDeListasProgramaciones);
        return "programaciones";
    }

    @GetMapping({"/operaciones/programaciones/programaciones-registrar"})
    public String registrarProgramacion(Model model) {
        List<Vuelo> vuelos = this.vueloService.findAll();
        Programacion programacion = new Programacion();
        ListaDeProgramaciones listaDeProgramaciones = new ListaDeProgramaciones();


        model.addAttribute("listaDeProgramaciones", listaDeProgramaciones);
        model.addAttribute("vuelos", vuelos);
        model.addAttribute("programacion", programacion);
        return "programaciones-registrar";
    }


    @PostMapping({"/operaciones/programaciones/programaciones-registrar"})
    public String registrarProgramacion(Model model, @ModelAttribute("listaDeProgramaciones") ListaDeProgramaciones listaDeProgramaciones) throws Exception {

        try {
            this.programacionService.guardar(listaDeProgramaciones.getProgramaciones());
        } catch (Exception e) {
            List<Vuelo> vuelos = this.vueloService.findAll();
            Programacion programacion = new Programacion();


            model.addAttribute("error", e.getMessage());
            model.addAttribute("vuelos", vuelos);
            model.addAttribute("programacion", programacion);
            return "programaciones-registrar";
        }

        return "redirect:/operaciones/programaciones";
    }

    @GetMapping({"/operaciones/programaciones/editar/{id}"})
    public String editarCupos(@PathVariable("id") Long id, Model model) {
        Programacion programacion = this.programacionService.buscarPorID(id);

        model.addAttribute("programacion", programacion);
        return "programaciones-editar";
    }

    @PostMapping({"/operaciones/programaciones/editar/{id}"})
    public String editar(@PathVariable("id") Long id, @ModelAttribute("programacion") Programacion programacion, Model model) throws ParseException {
        Programacion programacionExistente = this.programacionService.buscarPorID(id);
        programacionExistente.setETD(programacion.getETD());
        programacionExistente.setETA(programacion.getETA());
        programacionService.guardar(programacion);

        return "redirect:/operaciones/programaciones";
    }

}
