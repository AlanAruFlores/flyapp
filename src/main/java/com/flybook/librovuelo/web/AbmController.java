package com.flybook.librovuelo.web;

import com.flybook.librovuelo.model.Aeropuerto;
import com.flybook.librovuelo.model.Avion;
import com.flybook.librovuelo.service.AeropuertoService;
import com.flybook.librovuelo.service.AvionService;
import com.flybook.librovuelo.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AbmController {
    @Autowired
    private AeropuertoService aeropuertoService;
    @Autowired
    private AvionService avionService;
    @Autowired
    private SecurityService securityService;

    @GetMapping("/tripulante/perfil/admin/aviones")
        public String mostrarAviones(Model model) {
            List<Avion> aviones = avionService.findAll();
            model.addAttribute("aviones", aviones);

        return "aviones";
    }

    @GetMapping("/tripulante/perfil/admin/avion-registration")
    public String registrarAvion(Model model) {
        Avion avion = new Avion();
        model.addAttribute("newAvion", avion);

        return "avion-registration";
    }

    @PostMapping("/tripulante/perfil/admin/avion-registration")
    public String registrarAvion(@ModelAttribute("newAvion")Avion avion, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "avion-registration";
        }
        avionService.save(avion);
        return "redirect:/tripulante/perfil/admin/aviones";
    }

        @GetMapping("/tripulante/perfil/admin/avion-update/{id}")
        public String avionUpdate(@PathVariable("id") Long id, Model model) {
        Avion avion = avionService.getById(id);
        model.addAttribute("avionUpdate", avion);
        return "avion-update";
    }

        @PostMapping("/tripulante/perfil/admin/avion-update")
        public String avionUpdate(@ModelAttribute("avionUpdate") Avion avionUpdate, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "avion-update";
        }
        avionService.save(avionUpdate);
        return "redirect:/tripulante/perfil/admin/aviones";
    }

    @GetMapping("/tripulante/perfil/admin/avion-delete/{id}")
        public String avionDelete(@PathVariable("id") Long id, Model model) {
            Avion avion = avionService.getById(id);
            avionService.delete(avion);
            return "redirect:/tripulante/perfil/admin/aviones";
    }

    @GetMapping("/tripulante/perfil/admin/aeropuertos")
        public String mostrarAeropuertos(Model model) {
            List<Aeropuerto> aeropuertos = aeropuertoService.findAll();
            model.addAttribute("aeropuertos", aeropuertos);

            return "aeropuertos";
    }

    @PostMapping("/tripulante/perfil/admin/aeropuertos")
        public String mostrarAeropuertos(@ModelAttribute("aeropuertos")List<Aeropuerto> aeropuertos, BindingResult bindingResult){
            if (bindingResult.hasErrors()){
                return "aeropuertos";
            }
            return "aeropuertos";
    }

    @GetMapping("/tripulante/perfil/admin/aeropuerto-registration")
    public String registrarAeropuerto(Model model) {
        Aeropuerto aeropuerto = new Aeropuerto();
        model.addAttribute("newAeropuerto", aeropuerto);

        return "aeropuerto-registration";
    }

    @PostMapping("/tripulante/perfil/admin/aeropuerto-registration")
    public String registrarAeropuerto(@ModelAttribute("newAeropuerto")Aeropuerto aeropuerto, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "aeropuerto-registration";
        }
        aeropuertoService.save(aeropuerto);
        return "redirect:/tripulante/perfil/admin/aeropuertos";
    }

    @GetMapping("/tripulante/perfil/admin/aeropuerto-update/{id}")
    public String aeropuertoUpdate(@PathVariable("id") Long id, Model model) {
        Aeropuerto aeropuerto = aeropuertoService.getById(id);
        model.addAttribute("aeropuertoUpdate", aeropuerto);
        return "aeropuerto-update";
    }

    @PostMapping("/tripulante/perfil/admin/aeropuerto-update")
    public String aeropuertoUpdate(@ModelAttribute("aeropuertoUpdate") Aeropuerto aeropuertoUpdate, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "aeropuerto-update";
        }
        aeropuertoService.save(aeropuertoUpdate);
        return "redirect:/tripulante/perfil/admin/aeropuertos";
    }

    @GetMapping("/tripulante/perfil/admin/aeropuerto-delete/{id}")
    public String aeropuertoDelete(@PathVariable("id") Long id, Model model) {
        Aeropuerto aeropuerto = aeropuertoService.getById(id);
        aeropuertoService.delete(aeropuerto);
        return "redirect:/tripulante/perfil/admin/aeropuertos";
    }
}
