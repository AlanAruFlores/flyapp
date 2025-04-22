package com.flybook.librovuelo.web;


import com.flybook.librovuelo.model.Periodo;
import com.flybook.librovuelo.service.PeriodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PeriodoController {

    private final PeriodoService periodoService;

   @Autowired
    public PeriodoController(PeriodoService periodoService) {
        this.periodoService = periodoService;
    }


    @GetMapping({"/operaciones/periodos"})
    public String mostrarPeriodos(@Param("anioSelect") Integer anioSelect, Model model) {
        List<Periodo> periodos = new ArrayList<>();
        if (anioSelect != null) {
            periodos = periodoService.buscarPorAnio(anioSelect);
        } else {
            periodos = periodoService.mostrarTodosLosPeriodos();
        }

//este era devolverTodosLosAnios()
        List<Integer> anios = this.periodoService.devolverTodosLosAnios();
        model.addAttribute("periodos", periodos);
        model.addAttribute("anios", anios);
        return "periodos";
    }

    @PostMapping({"/operaciones/periodos"})
    public String generarPeriodos(@Param("anio") Integer anio, Model model) throws Exception {

        try {
            periodoService.generarPeriodos(anio);
        } catch (Exception e) {
            List<Periodo> periodos = periodoService.buscarPorAnio(anio);
            List<Integer> anios = periodoService.devolverTodosLosAnios();

            model.addAttribute("error", e.getMessage());
            model.addAttribute("anio", anio);
            model.addAttribute("periodos", periodos);
            model.addAttribute("anios", anios);
            return "periodos";
        }

        List<Integer> anios = periodoService.devolverTodosLosAnios();
        List<Periodo> periodos = periodoService.buscarPorAnio(anio);
        model.addAttribute("anio", anio);
        model.addAttribute("periodos", periodos);
        model.addAttribute("anios", anios);
        return "periodos";
    }

    @GetMapping({"/operaciones/periodos/editar/{id}"})
    public String editarCupos(@PathVariable("id") Long id, Model model) {
        Periodo periodo = this.periodoService.buscarPorID(id);

        model.addAttribute("periodo", periodo);
        return "periodos-editar";
    }

    @PostMapping({"/operaciones/periodos/editar/{id}"})
    public String editar(@PathVariable("id") Long id, @ModelAttribute("periodo") Periodo periodo, Model model) {
        Periodo periodoExistente = periodoService.buscarPorID(id);
        // periodoExistente.setCantidadCupos(periodo.getCantidadCupos());
        periodoService.actualizarCupos(periodo);

        return "redirect:/operaciones/periodos?anioSelect=" + periodoExistente.getAnio();
    }
}
