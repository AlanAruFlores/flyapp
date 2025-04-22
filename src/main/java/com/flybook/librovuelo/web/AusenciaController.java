package com.flybook.librovuelo.web;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.flybook.librovuelo.model.*;
import com.flybook.librovuelo.service.AusenciaService;
import com.flybook.librovuelo.service.ExcelService;
import com.flybook.librovuelo.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class AusenciaController {

    private final AusenciaService ausenciaService;
    private final UserService userService;
    private final ExcelService excelService;

    @Autowired
    public AusenciaController(AusenciaService ausenciaService, UserService userService, ExcelService excelService) {
        this.ausenciaService = ausenciaService;
        this.userService = userService;
        this.excelService = excelService;
    }

    @GetMapping("/tripulante/mislicencias")
    public String mostrarPedidos(@RequestParam Map<String, Object> params, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());

        if(!params.isEmpty() && !params.get("tipoVacacion").equals("TODOS")) {
            TipoAusencia tipoElegido = TipoAusencia.valueOf((String) params.get("tipoVacacion"));
            model.addAttribute("tipoElegido", tipoElegido);
        }

        List<TipoAusencia> tipoAusencias = List.of(TipoAusencia.values());

        List<Ausencia> ausencias = this.ausenciaService.filtrarAusenciasPorTipoParaUnUsuario(user, params);
        model.addAttribute("ausencias", ausencias);
        model.addAttribute("user", user);
        model.addAttribute("tipoAusencias", tipoAusencias);

        return "ausencias-tripulantes-mostrar";
    }


    @GetMapping("/tripulante/ausencia/pedir")
    public String pedirLicencia(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());

        Ausencia ausencia = new Ausencia();
        ausencia.setUser(user);

        model.addAttribute("user", user);
        List<TipoAusencia> tipoAusencias = List.of(TipoAusencia.values());
        model.addAttribute("ausencia", ausencia);
        model.addAttribute("tipoAusencias", tipoAusencias);
        return "ausencias-tripulantes-pedir";
    }

    @PostMapping("/tripulante/ausencia/pedir")
    public String pedirLicencia(@ModelAttribute("ausencia") Ausencia ausencia, Model model) {
        List<TipoAusencia> tipoAusencias = List.of(TipoAusencia.values());
        try {
            if (ausencia.getEstadoAusencia() != null) {
                if (ausencia.getEstadoAusencia().equals(EstadoAusencia.APROBADA)) {
                    model.addAttribute("yaEstaAprobada", "Tu ausencia ya fue aprobada, no puedes editar");
                    model.addAttribute("tipoAusencias", tipoAusencias);
                    return "ausencias-tripulantes-pedir";
                }
            }
            this.ausenciaService.guardarPedidoDeAusencia(ausencia);
            model.addAttribute("success", "La ausencia fue pedida con exito");
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("tipoAusencias", tipoAusencias);
        return "ausencias-tripulantes-pedir";
    }

    @GetMapping("/tripulante/pedido-ausencia/editar/{id}")
    public String editarLicencia(@PathVariable("id") Long id, Model model) {
        Ausencia ausencia = this.ausenciaService.buscarAusenciaPorId(id);
        List<TipoAusencia> tipoAusencias = List.of(TipoAusencia.values());
        TipoAusencia tipoElegido = ausencia.getTipoAusencia() != null ? ausencia.getTipoAusencia() : TipoAusencia.VAC15;
        Boolean ausenciaAprobada = ausencia.getEstadoAusencia().equals(EstadoAusencia.APROBADA);
        model.addAttribute("tipoAusencias", tipoAusencias);
        model.addAttribute("ausencia", ausencia);
        model.addAttribute("tipoElegido", tipoElegido);
        model.addAttribute("ausenciaAprobada", ausenciaAprobada);
        return "ausencias-tripulantes-pedir";
    }

    @GetMapping("/lider/listarAusencias")
    public String listarAusenciasLider(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());

        List<Ausencia> ausencias = this.ausenciaService.buscarTodasLasAusenciasPorLider(user);
        model.addAttribute("ausencias",ausencias);
        return "ausencias-lider-mostrar";
    }

    @GetMapping("/lider/ausencias/ver/{id}")
    public String listarAusenciasLider(@PathVariable("id") Long id, Model model){
        Ausencia ausencia = this.ausenciaService.buscarAusenciaPorId(id);
        model.addAttribute("ausencia",ausencia);
        return "ausencias-lider-ver";
    }

    @PostMapping("/lider/ausencias/aprobar")
    public String aprobarDesaprobarAusencia(@ModelAttribute("ausencia") Ausencia ausencia, RedirectAttributes redirectAttributes) {
        String menssage = "La ausencia de tipo " + ausencia.getTipoAusencia()
                + " del empleado " + ausencia.getUser().getNombre() + " " + ausencia.getUser().getApellido()
                + " fue aprobada para la fecha " + ausencia.getFechaDesde() + " hasta " + ausencia.getFechaHasta() ;
        this.ausenciaService.cambiarEstadoDeAusenciaPorDecisionDelLider(EstadoAusencia.APROBADA, ausencia);
        redirectAttributes.addFlashAttribute("message", menssage);

        return "redirect:/lider/listarAusencias";
    }

    @PostMapping("/lider/ausencias/desaprobar")
    public String desaprobarAusencia(@ModelAttribute("ausencia") Ausencia ausencia, RedirectAttributes redirectAttributes) {
        String message = "La ausencia de tipo " + ausencia.getTipoAusencia()
                + " del empleado " + ausencia.getUser().getNombre() + " " + ausencia.getUser().getApellido()
                + " fue desaprobada";
        this.ausenciaService.cambiarEstadoDeAusenciaPorDecisionDelLider(EstadoAusencia.CANCELADA, ausencia);
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/lider/listarAusencias";
    }


    @GetMapping({"/tripulante/ver-ausencias", "/lider/ver-ausencias", "/operaciones/ver-ausencias"})
    public String verAusencias(@RequestParam(required = false) Map<String, Object> params, Model model){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userService.findByUsername(userName);

        String url  = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();
        List<Ausencia> ausencias = new ArrayList<>();

        String atributo = (String) params.getOrDefault("atributo", "");
        String tipoAusencia = (String) params.getOrDefault("tipoAusencia", "");
        String valor = (String) params.getOrDefault("valor", "");

        String fechaDesdeData = (String) params.getOrDefault("fechaDesde", "");
        String fechaHastaData = (String) params.getOrDefault("fechaHasta", "");

        if(url.contains("tripulante")) {
            ausencias = this.ausenciaService.buscarTodasLasAusenciasDeUnUsuario(user);
            model.addAttribute("type", "tripulante");
        }
        if(url.contains("lider")) {
            ausencias = this.ausenciaService.buscarTodasLasAusenciasACargoDelLideroParams(user,params);
            model.addAttribute("type", "lider");
        }
        if(url.contains("operaciones")) {
            ausencias = this.ausenciaService.buscarTodasLasAusenciasOperacionesParams(params);
            model.addAttribute("type", "operaciones");
        }

        model.addAttribute("atributo", atributo);
        model.addAttribute("tipoAusencia", tipoAusencia);
        model.addAttribute("valor",valor);
        model.addAttribute("ausencias", ausencias);
        model.addAttribute("fechaDesdeData", fechaDesdeData);
        model.addAttribute("fechaHastaData", fechaHastaData);

        return "mostrar-ausencias";
    }

    @GetMapping({"/tripulante/ausencia/exportar-excel", "/lider/ausencia/exportar-excel", "/operaciones/ausencia/exportar-excel"})
    public String exportarExcelDeAusencias(@RequestParam (required = false)Map<String, Object> params, Model model, HttpServletResponse response){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = this.userService.findByUsername(userName);

        String url  = ServletUriComponentsBuilder.fromCurrentRequestUri().toUriString();

        List<Ausencia> ausencias = new ArrayList<>();
        if(url.contains("tripulante"))
            ausencias = this.ausenciaService.buscarTodasLasAusenciasDeUnUsuario(user);
        if(url.contains("lider"))
            ausencias = this.ausenciaService.buscarTodasLasAusenciasACargoDelLideroParams(user,params);
        if(url.contains("operaciones"))
            ausencias = this.ausenciaService.buscarTodasLasAusenciasOperacionesParams(params);

        try{
            this.excelService.exportarExcelDeLasAusencias(ausencias, response);
        } catch (IOException e) {
            e.printStackTrace();
        }


        model.addAttribute("ausencias", ausencias);
        return "mostrar-ausencias";
    }


}
