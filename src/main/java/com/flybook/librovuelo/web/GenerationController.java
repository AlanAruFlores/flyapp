package com.flybook.librovuelo.web;


import com.flybook.librovuelo.model.Generacion;
import com.flybook.librovuelo.service.GeneracionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/lider")
public class  GenerationController {

    @Autowired
    private GeneracionService generacionService;

    @GetMapping("/mostrar-generaciones")
    public String mostrarGeneraciones(Model model) {
        List<Generacion> generaciones = generacionService.findAll();
        model.addAttribute("generaciones",generaciones);
        return "mostrar-generaciones";
    }

    @GetMapping("/generacion-registration")
    public String irARegistrarGeneracion(Model model) {
        Generacion generacion = new Generacion();
        model.addAttribute("generacion",generacion);
        model.addAttribute("title", "Crear generacion");
        return "generacion-registration";
    }

    @PostMapping("/nueva-generacion")
    public String guardarGeneraciones(@ModelAttribute("generacion")Generacion generacion, Model model, BindingResult bindingResult){
        try {
            this.generacionService.save(generacion);
        } catch (Exception e) {
            model.addAttribute("generacion",generacion);
            model.addAttribute("title", "Crear generacion");
            model.addAttribute("error", e.getMessage());
            return "generacion-registration";
        }
        return "redirect:/lider/mostrar-generaciones";
    }

    @GetMapping("/generacion-update/{id}")
    public String generacionUpdate(@PathVariable("id") Long id, Model model) {
        Generacion generacion = generacionService.getById(id);
        model.addAttribute("generacion",generacion);
        model.addAttribute("title", "Modificar generacion");
        return "generacion-registration";
    }

    @GetMapping("/generacion-delete/{id}")
    public String generacionDelete(@PathVariable("id") Long id, Model model) {
        Generacion generacion = generacionService.getById(id);
        generacionService.delete(generacion);
        return "redirect:/lider/mostrar-generaciones";
    }

}

