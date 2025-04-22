package com.flybook.librovuelo.web;

import com.flybook.librovuelo.model.Ausencia;
import com.flybook.librovuelo.model.PedidoDiasLibres;
import com.flybook.librovuelo.model.User;
import com.flybook.librovuelo.service.ExcelService;
import com.flybook.librovuelo.service.PedidoDiasLibresService;
import com.flybook.librovuelo.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class PedidoDiasLibresController {

    @Autowired
    private PedidoDiasLibresService pedidoDiasLibresService;

    @Autowired
    private UserService userService;

    @Autowired
    private ExcelService excelService;


    @GetMapping(value = {"/tripulante/pedido-dias-libres", "/lider/pedido-dias-libres", "/operaciones/pedido-dias-libres" })
     public String mostrarPedidosDiasLibres(@RequestParam Map<String, Object> params, Model modelo){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = this.userService.findByUsername(authentication.getName());
        String valor = (String) params.getOrDefault("valor", "");
        String atributo = (String) params.getOrDefault("atributo", "");
        Boolean mostrarBotonEdicion=true;
        String url  = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();

        List<PedidoDiasLibres> diasLibres=new ArrayList<>();

        String fechaDesdeData = (String) params.getOrDefault("fechaDesde", "");
        String fechaHastaData = (String) params.getOrDefault("fechaHasta", "");
        if (params.get("id") == null) {

            if (url.contains("/tripulante/pedido-dias-libres")) {
                modelo.addAttribute("type", "tripulante");
                diasLibres = this.pedidoDiasLibresService.buscarDiasLibresDeUnUsuario(user);
            }
            if (url.contains("/lider/pedido-dias-libres")) {
                modelo.addAttribute("type", "lider");
                diasLibres = this.pedidoDiasLibresService.buscarDiasLibresDeMisTripulantes(user,params);
                mostrarBotonEdicion=false;
            }

            if (url.contains("/operaciones/pedido-dias-libres") ) {
                modelo.addAttribute("type", "operaciones");
                mostrarBotonEdicion = false;
                diasLibres = this.pedidoDiasLibresService.buscarTodasLosPedidosdeDiasLibres(params);
            }
        } else {
            modelo.addAttribute("type", "tripulante");
            Long idABuscar = Long.parseLong(params.get("id").toString());
            User tripulante = this.userService.findUserById(idABuscar);
            modelo.addAttribute("tripulante", tripulante);
            modelo.addAttribute("idABuscar", idABuscar);
            diasLibres = this.pedidoDiasLibresService.buscarDiasLibresDeUnUsuario(tripulante);
        }
        modelo.addAttribute("valor",valor);
        modelo.addAttribute("atributo",atributo);
        modelo.addAttribute("fechaDesde",fechaDesdeData);
        modelo.addAttribute("fechaHasta",fechaHastaData);

        modelo.addAttribute("user", user);
        modelo.addAttribute("pedidoDiasLibres",diasLibres);
        modelo.addAttribute("mostrarbotonedicion",  mostrarBotonEdicion);
        return "pedidoDiasLibres-mostrar";
    }


    @GetMapping({"/tripulante/pedido-dias-libres/exportar-excel", "/lider/pedido-dias-libres/exportar-excel", "/operaciones/pedido-dias-libres/exportar-excel"})
    public String exportarExcelDePedidosDiasLibres(@RequestParam (required = false)Map<String, Object> params, Model modelo, HttpServletResponse response){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userService.findByUsername(userName);

        String url  = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
        Boolean mostrarBotonEdicion=true;

        List<PedidoDiasLibres> diasLibres=new ArrayList<>();

        String fechaDesdeData = (String) params.getOrDefault("fechaDesde", "");
        String fechaHastaData = (String) params.getOrDefault("fechaHasta", "");

        if (url.contains("/tripulante/pedido-dias-libres")) {
            modelo.addAttribute("type", "tripulante");
            diasLibres = this.pedidoDiasLibresService.buscarDiasLibresDeUnUsuario(user);
        }
        if (url.contains("/lider/pedido-dias-libres")) {
            modelo.addAttribute("type", "lider");
            diasLibres = this.pedidoDiasLibresService.buscarDiasLibresDeMisTripulantes(user,params);
            mostrarBotonEdicion=false;
        }

        if (url.contains("/operaciones/pedido-dias-libres") ) {
            modelo.addAttribute("type", "operaciones");
            mostrarBotonEdicion = false;
            diasLibres = this.pedidoDiasLibresService.buscarTodasLosPedidosdeDiasLibres(params);
        }
        try{
            this.excelService.exportarExcelDeLosPedidosDiasLibres(diasLibres, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        modelo.addAttribute("pedidoDiasLibres",diasLibres);
        return "pedidoDiasLibres-mostrar";
    }


    @GetMapping("/tripulante/pedido-dias-libres/registrar-pedido")
    public String registrarNuevoPedidoDiasLibres(Model model) {
        PedidoDiasLibres pedidoDiasLibres = new PedidoDiasLibres();
        pedidoDiasLibres.setFechaSolicitud(LocalDate.now());
        model.addAttribute("title", "Registrar");
        model.addAttribute("pedidoDiasLibres", pedidoDiasLibres);
        return "pedidoDiasLibres-form";
    }

    @PostMapping("/tripulante/nuevo-pedido/pedido-dias-libres")
    public String registrarNuevoPedidoVacaciones(@ModelAttribute("pedidoDiasLibres") PedidoDiasLibres pedido, BindingResult bindingResult, Model model) throws Exception {
        if (bindingResult.hasErrors()) {
            return "pedidoDiasLibres-form";
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());
        pedido.setUser(user);

        try {
            pedidoDiasLibresService.validaryGuardar(pedido);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("pedido", pedido);
            model.addAttribute("user", user);
            model.addAttribute("title", "Registrar");

            return "pedidoDiasLibres-form";
        }

        return "redirect:/tripulante/pedido-dias-libres";
    }

    @GetMapping("/tripulante/pedido-dias-libres/ver/{id}")
    public String verPedidoDiasLibres(@PathVariable("id") Long id, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());
        PedidoDiasLibres pedidoDiasLibres = pedidoDiasLibresService.getById(id);



        model.addAttribute("rol","tripulante");
        model.addAttribute("pedidoDiasLibres",pedidoDiasLibres);
        model.addAttribute("title", "Ver");
        return "pedidoDiasLibres-form";
    }

    @GetMapping("/tripulante/pedido-dias-libres/editar/{id}")
    public String editarPedidoDiasLibres(@PathVariable("id") Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());
        PedidoDiasLibres pedidoDiasLibres = pedidoDiasLibresService.getById(id);
        model.addAttribute("pedidoDiasLibres", pedidoDiasLibres);
        model.addAttribute("title", "Editar");
        model.addAttribute("user", user);
        return "pedidoDiasLibres-form";
    }
}
