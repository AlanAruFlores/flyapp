package com.flybook.librovuelo.web;


import com.flybook.librovuelo.dto.DatosFlyondiersEdit;
import com.flybook.librovuelo.model.*;
import com.flybook.librovuelo.service.AdjuntoDocumentacionService;
import com.flybook.librovuelo.service.GeneracionService;
import com.flybook.librovuelo.service.RoleService;
import com.flybook.librovuelo.service.UserService;
import org.springframework.beans.BeanUtils;
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

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
public class EmpleadosController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private GeneracionService generacionService;

    @GetMapping({"/flybondiers"})
    public String mostrarEmpleados(Model model) {

        List<User> empleados = userService.findAll();

        model.addAttribute("empleados", empleados);
        return "flybondiers";
    }

    @GetMapping({"/flybondiers/ver/{id}"})
    public String verEmpleados(Model model, @PathVariable("id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = this.userService.findByUsername(authentication.getName());
        User empleado = userService.getById(id);

        Direccion origenDireccion = user.getDireccion();
        Direccion destinoDireccion = empleado.getDireccion();

        String origen = origenDireccion.getCalle() + ", " + origenDireccion.getNumeroDeCalle() + " " + origenDireccion.getLocalidad().getNombre() + ", " + origenDireccion.getLocalidad().getProvincia().getNombre();
        String destino = destinoDireccion.getCalle() + ", " + destinoDireccion.getNumeroDeCalle() + " " + destinoDireccion.getLocalidad().getNombre() + ", " + destinoDireccion.getLocalidad().getProvincia().getNombre();

        String encodedOrigen = URLEncoder.encode(origen, StandardCharsets.UTF_8);
        String encodedDestino = URLEncoder.encode(destino, StandardCharsets.UTF_8);

        String link = "";

        model.addAttribute("link", link);
        model.addAttribute("destino", destino);
        model.addAttribute("origen", origen);
        model.addAttribute("empleado", empleado);
        return "flybondiers-ver";
    }

    @GetMapping("/flybondiers/editar/{id}")
    public String editarEmpleados(@PathVariable("id") Long id, Model model) {

        DatosFlyondiersEdit flybondiersEdit = userService.obtenerlybondiersEdit(id);
        User empleado = userService.getById(id);
        List<Role> roles = roleService.obtenerTodosLosRoles();
        List<Generacion> generaciones = this.generacionService.findAll();
        List<User> lideres = this.userService.obtenerTodosLosLideres();
        List<TipoCargo> cargos = List.of(TipoCargo.values());

        model.addAttribute("empleado", empleado);
        model.addAttribute("flybondiersEdit", flybondiersEdit);
        model.addAttribute("roles", roles);
        model.addAttribute("generaciones", generaciones);
        model.addAttribute("lideres", lideres);
        model.addAttribute("cargos", cargos);

        return "flybondiers-editar";
    }


    @PostMapping("/flybondiers/editar")
    public String editarRole(@ModelAttribute("datosFlyondiersEdit") DatosFlyondiersEdit datosFlyondiersEdit, Model model, BindingResult bindingResult) {


        if (bindingResult.hasErrors())
            return "redirect:/";


        List<Role> roles = roleService.obtenerTodosLosRoles();
        List<Generacion> generaciones = this.generacionService.findAll();
        List<User> lideres = this.userService.obtenerTodosLosLideres();
        List<TipoCargo> cargos = List.of(TipoCargo.values());
        User empleado = userService.getById(datosFlyondiersEdit.getId());

        model.addAttribute("empleado", empleado);
        model.addAttribute("flybondiersEdit", datosFlyondiersEdit);
        model.addAttribute("roles", roles);
        model.addAttribute("generaciones", generaciones);
        model.addAttribute("lideres", lideres);
        model.addAttribute("cargos", cargos);



        try {
            userService.updateDatosFlybondiEdit(datosFlyondiersEdit);
            model.addAttribute("confirmado", "Los datos fueron modificados con éxito");

        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return "flybondiers-editar";

    }

}
