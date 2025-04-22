package com.flybook.librovuelo.web;

import com.flybook.librovuelo.dto.ListaDeProgramaciones;
import com.flybook.librovuelo.model.Aeropuerto;
import com.flybook.librovuelo.model.Vuelo;
import com.flybook.librovuelo.repository.CicloVacacionesRepository;
import com.flybook.librovuelo.service.AeropuertoService;
import com.flybook.librovuelo.service.UserService;
import com.flybook.librovuelo.service.VueloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class VueloController {

    @Autowired
    private VueloService vueloService;
    @Autowired
    private AeropuertoService aeropuertoService;


    @GetMapping({"/vuelos"})
    public String mostrarEmpleados(Model model) {


        List<Vuelo> listaVuelos = this.vueloService.findAll();

        model.addAttribute("listaVuelos", listaVuelos);
        return "vuelos";
    }

    @GetMapping({"/vuelos/vuelos-registrar"})
    public String registrarVuelo(Model model) {
        List<Aeropuerto> aeropuertos = this.aeropuertoService.findAll();
        Vuelo vuelo = new Vuelo();


        model.addAttribute("aeropuertos", aeropuertos);
        model.addAttribute("vuelo", vuelo);
        return "vuelos-registrar";
    }


    @PostMapping({"/vuelos/vuelos-registrar"})
    public String registrarVuelo(Model model, @ModelAttribute("vuelo") Vuelo vuelo) throws Exception {


        try {
            this.vueloService.save(vuelo);
        } catch (Exception e) {
            List<Aeropuerto> aeropuertos = this.aeropuertoService.findAll();
           

            model.addAttribute("error", e.getMessage());
            model.addAttribute("aeropuertos", aeropuertos);

            return "vuelos-registrar";
        }

        return "redirect:/vuelos";
    }
}
