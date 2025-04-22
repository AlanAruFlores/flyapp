package com.flybook.librovuelo.web;

import com.flybook.librovuelo.exceptions.UsuariosNoAsigandosRecurrentException;
import com.flybook.librovuelo.model.Recurrent;
import com.flybook.librovuelo.model.TripulanteRecurrent;
import com.flybook.librovuelo.model.User;
import com.flybook.librovuelo.service.RecurrentService;
import com.flybook.librovuelo.service.TripulanteRecurrentService;
import com.flybook.librovuelo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class RecurrentController {

    @Autowired
    RecurrentService recurrentService;

    @Autowired
    TripulanteRecurrentService tripulanteRecurrentService;

    @Autowired
    UserService userService;

    @GetMapping("lider/mostrar-recurrent")
    public String mostrarRecurrent(Model model) {
        List<Recurrent> recurrents = recurrentService.findAll();
        model.addAttribute("recurrents", recurrents);
        return "mostrar-recurrent";
    }


    @GetMapping("lider/nuevo-recurrent")
    public String registrarRecurrent(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());

        Recurrent recurrent = new Recurrent();
        model.addAttribute("recurrent", recurrent);

        return "registrar-recurrent";
    }

    @PostMapping("/lider/registrar-recurrent")
    public String guardarRecurrent(@ModelAttribute("recurrent") Recurrent recurrent, Model model, BindingResult bindingResult) {
        try {
            this.recurrentService.save(recurrent);
        } catch (Exception e) {
            model.addAttribute("recurrent", recurrent);
            model.addAttribute("error", e.getMessage());
            return "registrar-recurrent";
        }
        return "redirect:/lider/mostrar-recurrent";
    }

    @GetMapping("/lider/recurrent-update/{id}")
    public String actualizarRecurrent(@PathVariable("id") Long id,
                                      Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());

        Recurrent recurrent = recurrentService.findRecurrentById(id);

        model.addAttribute("recurrentActualizado", recurrent);

        return "actualizar-recurrent";
    }

    @PostMapping("/lider/recurrent-actualizado")
    public String actualizarDocumentacion(@ModelAttribute("recurrent") Recurrent recurrent,
                                          Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());

        try {
            recurrentService.save(recurrent);
        } catch (Exception e) {
            model.addAttribute(e.getMessage());
            return "actualizar-recurrent";
        }
        return "redirect:/lider/mostrar-recurrent";
    }

    @GetMapping("/lider/ver-recurrent/{id}")
    public String verRecurrent(@PathVariable("id") Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());

        Recurrent recurrentAsociado = recurrentService.getById(id);
        DatosTripulanteRecurrent datosTripulanteRecurrent = new DatosTripulanteRecurrent();
        datosTripulanteRecurrent.setIdRecurrent(recurrentAsociado.getId());
        List<User> tripulantes = userService.findAll();
        List<TripulanteRecurrent> tr = tripulanteRecurrentService.findTripulanteRecurrentsByRecurrent
                (recurrentAsociado);

        model.addAttribute("listaDeTripulantes", tripulantes);
        model.addAttribute("recurrent", recurrentAsociado);
        model.addAttribute("usuario", user);
        model.addAttribute("datosTripulanteRecurrent", datosTripulanteRecurrent);
        model.addAttribute("title", "Ver Recurrent");
        model.addAttribute("listaDeTripulanteRecurrent", tr);

        return "ver-recurrent";
    }

    @GetMapping("/lider/ver-recurrent/{idRecurrent}/tripulante-delete/{idTripulante}")
    public String borrarTripulanteDelRecurrent(@PathVariable("idTripulante") Long idTripulante, @PathVariable("idRecurrent") Long idRecurrent, Model model) throws Exception {
        this.tripulanteRecurrentService.borrarTripulanteDeUnRecurrent(idTripulante, idRecurrent);
        return "redirect:/lider/ver-recurrent/"+idRecurrent;
    }

    @GetMapping("/lider/recurrent-delete/{id}")
    public String borrarRecurrent(@PathVariable("id") Long id, Model model) {
        Recurrent recurrentAEliminar = recurrentService.getById(id);
        recurrentService.delete(recurrentAEliminar);
        return "redirect:/lider/mostrar-recurrent";
    }

    @PostMapping("/lider/validarTripulantesAsignados")
    public String asignarRecurrentATripulantes(
            @ModelAttribute("datosTripulanteRecurrent") DatosTripulanteRecurrent datosTripulanteRecurrent,
            BindingResult bindingResult, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());

        if (bindingResult.hasErrors()) {
            return "ver-recurrent";
        }
        Recurrent recurrent=this.recurrentService.findRecurrentById( datosTripulanteRecurrent.getIdRecurrent());

        try {
            tripulanteRecurrentService.save(datosTripulanteRecurrent.getIdsTripulantes(),
                    datosTripulanteRecurrent.getIdRecurrent());
        } catch (UsuariosNoAsigandosRecurrentException e) {
            model.addAttribute("mensajeAusenciasDeUsuariosQueNoPuedenAgregarseAUnRecurrente",e.getMessage());
            model.addAttribute("AusenciasDeUsuariosQueNoPuedenAgregarseAUnRecurrente",e.getAsenciasDeUsuariosQueNoPuedenAgregarseAUnRecurrente());

        }

        Recurrent recurrentAsociado = recurrentService.getById(datosTripulanteRecurrent.getIdRecurrent());
        datosTripulanteRecurrent = new DatosTripulanteRecurrent();
        datosTripulanteRecurrent.setIdRecurrent(recurrentAsociado.getId());
        List<User> tripulantes = userService.findAll();
        List<TripulanteRecurrent> tr = tripulanteRecurrentService.findTripulanteRecurrentsByRecurrent
                (recurrentAsociado);

        model.addAttribute("listaDeTripulantes", tripulantes);
        model.addAttribute("recurrent", recurrentAsociado);
        model.addAttribute("usuario", user);
        model.addAttribute("datosTripulanteRecurrent", datosTripulanteRecurrent);
        model.addAttribute("title", "Ver Recurrent");
        model.addAttribute("listaDeTripulanteRecurrent", tr);

        return "ver-recurrent";
    }
}

