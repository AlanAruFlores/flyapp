package com.flybook.librovuelo.web;

import com.flybook.librovuelo.model.*;
import com.flybook.librovuelo.service.UserService;
import com.flybook.librovuelo.service.VestimentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class VestimentaController {

    @Autowired
    VestimentaService vestimentaService;
    @Autowired
    UserService userService;

    @GetMapping("/tripulante/pedido-vestimenta")
    public String mostrarForm( Model model){
        Integer[] tallasRopa ={36,38,40,42,44,46,48,50,52,54};
        Integer[] tallaMedias ={1,2,3,4,5};
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());

        model.addAttribute("tallasRopa",tallasRopa);
        model.addAttribute("tallaMedias",tallaMedias);
        model.addAttribute("user",user);
        Vestimenta vestimenta= new Vestimenta();
        vestimenta.setUser(user);
        model.addAttribute("vestimenta",vestimenta);
        return "vestimenta-form";
    }

    @PostMapping("/tripulante/vestimenta-registration")
    public String registrarForm(@ModelAttribute ("vestimenta") Vestimenta vestimenta, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            return "vestimenta-form";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());
        vestimentaService.guardarVestimenta(vestimenta,user);
        return "redirect:/tripulante/vestimenta/ver";
    }


    @GetMapping(value = {"/tripulante/vestimenta/ver", "lider/vestimenta/ver"})
    public String verVestimenta(Model model, @RequestParam Map<String, Object> params) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());

        Vestimenta vestimentaBuscada = null;

        if (params.get("id") == null) {
            vestimentaBuscada = vestimentaService.getByUserId(user.getId());
        } else {
            Long idABuscar = Long.parseLong(params.get("id").toString());
            User tripulante = this.userService.findUserById(idABuscar);
            model.addAttribute("tripulante", tripulante);
            model.addAttribute("idABuscar", idABuscar);
            vestimentaBuscada = vestimentaService.getByUserId(tripulante.getId());
        }
        model.addAttribute("user", user);
        model.addAttribute("vestimentaBuscada",vestimentaBuscada);
        return "verVestimenta";
    }

    @GetMapping("/tripulante/vestimenta/editar/{idUser}")
    public String vestimentaUpdate(@PathVariable("idUser") Long id, Model model) {
        Vestimenta vestimenta = vestimentaService.getByUserId(id);
        if(vestimenta!=null){
            Integer[] tallasRopa ={36,38,40,42,44,46,48,50,52,54};
            Integer[] tallaMedias ={1,2,3,4,5};
            model.addAttribute("tallasRopa",tallasRopa);
            model.addAttribute("tallaMedias",tallaMedias);
            model.addAttribute("vestimenta", vestimenta);
            return "vestimenta-form";
        }
        else{
            return "redirect:/tripulante/vestimenta/ver";
        }

    }

}
