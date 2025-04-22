package com.flybook.librovuelo.web;

import com.flybook.librovuelo.dto.HabilitarVacaciones;
import com.flybook.librovuelo.model.*;
import com.flybook.librovuelo.repository.HabilitarVacacionesRepository;
import com.flybook.librovuelo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/tripulante")
public class PedidoVacacionesController {
    @Autowired
    private PedidoVacacionesService pedidosVacacionesService;
    @Autowired
    private CicloVacacionesService cicloVacacionesService;
    @Autowired
    private UserService userService;
    @Autowired
    private AusenciaService ausenciaService;
    @Autowired
    private HabilitarVacacionesService habilitarVacacionesService;

    // mostrar la lista de pedidos
    @GetMapping("/pedido-vacaciones")
    public String mostrarPedidos(@RequestParam Map<String, Object> params, Model model) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());

        Boolean estaHabilitadoParaPedirVacaciones = this.pedidosVacacionesService.sePuedenPedirVacaciones();

        List<String> sortByEstado = Arrays.asList("Todos", "SOLICITADO", "RECHAZADO", "CERRADO");
        List<CicloVacaciones> sortByCiclo = this.cicloVacacionesService.obtenerTodosLosCiclosParaUnaGeneracion(user.getGeneracion());

        List<PedidoVacaciones> pedidos = this.pedidosVacacionesService.findAllByUser(user);
        List<CicloVacaciones> ciclosVacaciones = this.cicloVacacionesService.obtenerTodosLosCiclosParaUnaGeneracion(user.getGeneracion());

        if ((params.get("ciclo") != null && (params.get("estado")) != null)) {

            if (params.get("ciclo").toString().equals(0) && !(params.get("estado").toString().equals("Todos"))) {
                try {
                    if ("SOLICITADO".equals(params.get("estado").toString())) {
                        pedidos = this.pedidosVacacionesService.findAllByUserAndEstadoPedido_Solicitado(user);
                    } else if ("RECHAZADO".equals(params.get("estado").toString())) {
                        pedidos = this.pedidosVacacionesService.findAllByUserAndEstadoPedido_Rechazado(user);
                    } else if ("CERRADO".equals(params.get("estado").toString())) {
                        pedidos = this.pedidosVacacionesService.findAllByUserAndEstadoPedido_Cerrado(user);
                    }
                } catch (Exception e) {
                    model.addAttribute("error", e.getMessage());
                }
            }

            if ((params.get("estado").toString().equals("Todos")) && !(params.get("ciclo").toString().equals(0))) {
                pedidos = this.pedidosVacacionesService.obtenerTodosLosPedidosParaUnNumeroDeCiclo(user, Integer.parseInt(params.get("ciclo").toString()));
            }

            EstadoPedido estado = null;
            if ((!(params.get("estado").toString().equals("Todos"))) && !(params.get("ciclo").toString().equals(0))) {
                if ("SOLICITADO".equals(params.get("estado").toString())) {
                    estado = estado.SOLICITADO;
                }
                if ("RECHAZADO".equals(params.get("estado").toString())) {
                    estado = estado.RECHAZADO;
                }
                if ("CERRADO".equals(params.get("estado").toString())) {
                    estado = estado.CERRADO;
                }

                pedidos = this.pedidosVacacionesService.obtenerTodosLosPedidosPorEstadoYNumeroDeCiclo(user, estado, Integer.parseInt(params.get("ciclo").toString()));
            }
        }

        model.addAttribute("ciclosVacaciones", ciclosVacaciones);
        model.addAttribute("pedidos", pedidos);
        model.addAttribute("user", user);
        model.addAttribute("sortByEstado", sortByEstado);
        model.addAttribute("sortByCiclo", sortByCiclo);
        model.addAttribute("estaHabilitadoParaPedirVacaciones", estaHabilitadoParaPedirVacaciones);
        return "pedidoVacaciones-mostrar";
    }

    @GetMapping("/pedido-vacaciones/registrar-pedido")
    public String registrarNuevoPedidoVacaciones(Model model, @RequestParam Map<String, Object> params) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());
        String parametroDelCicloSeleccionado = (String) params.get("ciclo");
        Long idCiclo = null;
        Boolean habilitarFormulario = false;
        PedidoVacaciones pedidoVacaciones = new PedidoVacaciones();

        if (parametroDelCicloSeleccionado != null) {
            idCiclo = Long.parseLong(params.get("ciclo").toString());
            habilitarFormulario = true;
            pedidoVacaciones = this.pedidosVacacionesService.buscarPedidoDeVacacionesPorIdDeCicloYPorUsuario(idCiclo, user);
            model.addAttribute("cicloElegidoId", idCiclo);
        }

        Boolean estaHabilitadoParaPedirVacaciones = this.pedidosVacacionesService.sePuedenPedirVacaciones();

        if(!estaHabilitadoParaPedirVacaciones){
            return "redirect:/tripulante/pedido-vacaciones";
        }

        List<CicloVacaciones> ciclosVacaciones = this.cicloVacacionesService.obtenerTodosLosCiclosParaUnaGeneracion(user.getGeneracion());

        Map<User, Map<CicloVacaciones, List<Ausencia>>> mapaVacacionesDeTodosLosUsuarios = this.ausenciaService.obtenerVacacionesDeUnUsuarioAgrupadasCiclos(user);

        model.addAttribute("mapaVacacionesDeTodosLosUsuarios", mapaVacacionesDeTodosLosUsuarios);
        model.addAttribute("pedidoVacaciones", pedidoVacaciones);
        model.addAttribute("ciclosVacaciones", ciclosVacaciones);
        model.addAttribute("user", user);
        model.addAttribute("habilitarFormulario", habilitarFormulario);
        model.addAttribute("title", "Registrar mi pedido");
        return "pedidoVacaciones-form";
    }


    @PostMapping("/nuevo-pedido/vacaciones")
    public String registrarNuevoPedidoVacaciones(@ModelAttribute("pedidoVacaciones") PedidoVacaciones pedidoVacaciones, BindingResult bindingResult, Model model) throws Exception {

        if (bindingResult.hasErrors()) {
            return "pedidoVacacaciones-form";
        }

        Boolean estaHabilitadoParaPedirVacaciones = this.pedidosVacacionesService.sePuedenPedirVacaciones();

        if(!estaHabilitadoParaPedirVacaciones){
            return "redirect:/tripulante/pedido-vacaciones";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());
        pedidoVacaciones.setFechaDeSolicitud(LocalDateTime.now());
        pedidoVacaciones.setUser(user);
        pedidoVacaciones.setEstadoPedido(EstadoPedido.SOLICITADO);


        try {
            pedidosVacacionesService.save(pedidoVacaciones);
        } catch (Exception e) {
            List<CicloVacaciones> ciclosVacaciones = this.cicloVacacionesService.obtenerTodosLosCiclos();
            model.addAttribute("error", e.getMessage());
            model.addAttribute("estaHabilitadoParaPedirVacaciones",estaHabilitadoParaPedirVacaciones);
            model.addAttribute("pedidoVacaciones", pedidoVacaciones);
            model.addAttribute("ciclosVacaciones", ciclosVacaciones);
            model.addAttribute("user", user);
            return "pedidoVacaciones-form";
        }

        return "redirect:/tripulante/pedido-vacaciones";
    }

    @GetMapping("/pedido-vacaciones/editar/{id}")
    public String PedidoVacacionesUpdate(@PathVariable("id") Long id) {

        Boolean estaHabilitadoParaPedirVacaciones = this.pedidosVacacionesService.sePuedenPedirVacaciones();

        if(!estaHabilitadoParaPedirVacaciones){ return "redirect:/tripulante/pedido-vacaciones"; }

        PedidoVacaciones pedidoVacaciones = pedidosVacacionesService.buscarPedidoVacacionesPorId(id);
        return "redirect:/tripulante/pedido-vacaciones/registrar-pedido?ciclo=" + pedidoVacaciones.getCicloVacaciones().getId();
    }


    @GetMapping("/pedido-vacaciones/ver/{id}")
    public String verPedidoVacaciones(@PathVariable("id") Long id, Model model) {
        PedidoVacaciones pedidoVacaciones = pedidosVacacionesService.buscarPedidoVacacionesPorId(id);
        List<CicloVacaciones> ciclosVacaciones = this.cicloVacacionesService.obtenerTodosLosCiclos();
        model.addAttribute("ciclosVacaciones", ciclosVacaciones);
        model.addAttribute("pedidoVacaciones", pedidoVacaciones);
        model.addAttribute("title", "Ver mi pedido");
        return "verPedidoVacaciones";
    }

    @GetMapping("/operaciones/habilitar-vacaciones")
    public String habilitarPedidoDeVacaciones(Model model) {

        HabilitarVacaciones habilitarVacaciones = this.pedidosVacacionesService.buscarHabilitarVacaciones();
        String vacacionesDeshabilitadas = "Los pedidos de vacaciones estan deshabilitados";

        String vacacionesHabilitadas = "Los pedidos de vacaciones estan habilitados desde "
                + habilitarVacaciones.getHabilitarPedidoDeVacacionesDesde() + " hasta "
                + habilitarVacaciones.getHabilitarPedidoDeVacacionesHasta();

        model.addAttribute("habilitarVacaciones", habilitarVacaciones);
        model.addAttribute("deshabilitado", habilitarVacaciones.getHabilitar());
        model.addAttribute("vacacionesDeshabilitadas", vacacionesDeshabilitadas);
        model.addAttribute("vacacionesHabilitadas",vacacionesHabilitadas);
        return "habilitarPedidosDeVacaciones";
    }

    @PostMapping("/operaciones/habilitar-vacaciones")
    public String habilitarPedidoDeVacaciones(@ModelAttribute HabilitarVacaciones habilitarVacaciones, Model model) {

        try {
            this.habilitarVacacionesService.guardarHabilitarVacaciones(habilitarVacaciones);
            return "redirect:/tripulante/operaciones/habilitar-vacaciones";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }

        return "habilitarPedidosDeVacaciones";
    }

}

