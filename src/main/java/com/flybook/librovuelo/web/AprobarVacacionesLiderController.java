package com.flybook.librovuelo.web;

import com.flybook.librovuelo.dto.ResumenDeVacaciones;
import com.flybook.librovuelo.dto.SugerenciaVacacion;
import com.flybook.librovuelo.model.*;
import com.flybook.librovuelo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class AprobarVacacionesLiderController {

    @Autowired
    PeriodoService periodoService;
    @Autowired
    PedidoVacacionesService pedidoVacacionesService;
    @Autowired
    CicloVacacionesService cicloVacacionesService;

    @Autowired
    AusenciaService ausenciaService;
    @Autowired
    UserService userService;


    @GetMapping({"/lider/listarPedidos", "/lider/listarpedidos"})
    public String mostrarPedidosPorCiclo(@RequestParam Map<String, Object> params, Model modelo) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());

        List<PedidoVacaciones> pedidos = null;

        if (params.get("id") == null) {
               if (params.get("ciclo") == null  || params.get("ciclo").equals("Todos") )
                   pedidos = this.pedidoVacacionesService.obtenerPedidosDeVacacionQueTieneUnLiderParaAnalizar(user);
               else {
                   Integer cicloVacaciones = Integer.parseInt(params.get("ciclo").toString());
                   pedidos = this.pedidoVacacionesService.obtenerTodosLosPedidosParaUnNumeroDeCiclo(user,cicloVacaciones);
               }
        } else {
            Long idABuscar = Long.parseLong(params.get("id").toString());
            User tripulante = this.userService.findUserById(idABuscar);
            modelo.addAttribute("idABuscar", idABuscar);
            pedidos = this.pedidoVacacionesService.obtenerPedidosDeVacacionQueTieneUnLiderParaAnalizar(tripulante);
        }

            List<CicloVacaciones> sortByCiclo = cicloVacacionesService.obtenerTodosLosCiclos();

        modelo.addAttribute("user", user);
        modelo.addAttribute("pedidos", pedidos);
        modelo.addAttribute("sortByCiclo", sortByCiclo);

        return "pedidoVacacionesLider";

    }

    @GetMapping(value = {"/lider/detallePedido/{id}"})
    public String detalle(@PathVariable("id") Long id, Model modelo) {
        PedidoVacaciones pedido = pedidoVacacionesService.buscarPedidoVacacionesPorId(id);

        Integer cantidadDeCupoTotalPrimerQuincenaPlanA =this.periodoService.obtenerElPeriodoParaUnaFecha(pedido.getFechaDesdePrimeraQuincenaPlanA()).getCantidadCuposPorCargo(pedido.getUser().getTipoCargo());
        Integer cantidadDeCupoTotalSegundaQuincenaPlanA =this.periodoService.obtenerElPeriodoParaUnaFecha(pedido.getFechaDesdeSegundaQuincenaPlanA()).getCantidadCuposPorCargo(pedido.getUser().getTipoCargo());
        Integer cantidadDeCupoTotalDiasOpuestosPlanA =this.periodoService.obtenerElPeriodoParaUnaFecha(pedido.getFechaDesdeDiasOpuestosPlanA()).getCantidadCuposPorCargo(pedido.getUser().getTipoCargo());
        Integer cantidadDeCupoTotalPrimerQuincenaPlanB =this.periodoService.obtenerElPeriodoParaUnaFecha(pedido.getFechaDesdePrimeraQuincenaPlanB()).getCantidadCuposPorCargo(pedido.getUser().getTipoCargo());
        Integer cantidadDeCupoTotalSegundaQuincenaPlanB =this.periodoService.obtenerElPeriodoParaUnaFecha(pedido.getFechaDesdeSegundaQuincenaPlanB()).getCantidadCuposPorCargo(pedido.getUser().getTipoCargo());
        Integer cantidadDeCupoTotalDiasOpuestosPlanB =this.periodoService.obtenerElPeriodoParaUnaFecha(pedido.getFechaDesdeDiasOpuestosPlanB()).getCantidadCuposPorCargo(pedido.getUser().getTipoCargo());

        modelo.addAttribute("cantidadDeCupoTotalPrimerQuincenaPlanA", cantidadDeCupoTotalPrimerQuincenaPlanA);
        modelo.addAttribute("cantidadDeCupoTotalSegundaQuincenaPlanA", cantidadDeCupoTotalSegundaQuincenaPlanA);
        modelo.addAttribute("cantidadDeCupoTotalDiasOpuestosPlanA", cantidadDeCupoTotalDiasOpuestosPlanA);
        modelo.addAttribute("cantidadDeCupoTotalPrimerQuincenaPlanB", cantidadDeCupoTotalPrimerQuincenaPlanB);
        modelo.addAttribute("cantidadDeCupoTotalSegundaQuincenaPlanB", cantidadDeCupoTotalSegundaQuincenaPlanB);
        modelo.addAttribute("cantidadDeCupoTotalDiasOpuestosPlanB", cantidadDeCupoTotalDiasOpuestosPlanB);


        Integer cantidadDeCupoUsadosPrimerQuincenaPlanA=this.ausenciaService.obtenerCantidadDeVacionesAsiganadasENUnpeRiodoParaUnaFechayUnCargo(pedido.getFechaDesdePrimeraQuincenaPlanA(),pedido.getUser().getTipoCargo());
        modelo.addAttribute("cantidadDeCupoUsadosPrimerQuincenaPlanA", cantidadDeCupoUsadosPrimerQuincenaPlanA);
        Integer cantidadDeCupoUsadosSegundaQuincenaPlanA=this.ausenciaService.obtenerCantidadDeVacionesAsiganadasENUnpeRiodoParaUnaFechayUnCargo(pedido.getFechaDesdeSegundaQuincenaPlanA(),pedido.getUser().getTipoCargo());
        modelo.addAttribute("cantidadDeCupoUsadosSegundaQuincenaPlanA", cantidadDeCupoUsadosSegundaQuincenaPlanA);
        Integer cantidadDeCupoUsadosDiasOpuestosPlanA=this.ausenciaService.obtenerCantidadDeVacionesAsiganadasENUnpeRiodoParaUnaFechayUnCargo(pedido.getFechaDesdeDiasOpuestosPlanA(),pedido.getUser().getTipoCargo());
        modelo.addAttribute("cantidadDeCupoUsadosDiasOpuestosPlanA", cantidadDeCupoUsadosDiasOpuestosPlanA);
        Integer cantidadDeCupoUsadosPrimerQuincenaPlanB=this.ausenciaService.obtenerCantidadDeVacionesAsiganadasENUnpeRiodoParaUnaFechayUnCargo(pedido.getFechaDesdePrimeraQuincenaPlanB(),pedido.getUser().getTipoCargo());
        modelo.addAttribute("cantidadDeCupoUsadosPrimerQuincenaPlanB", cantidadDeCupoUsadosPrimerQuincenaPlanB);
        Integer cantidadDeCupoUsadosSegundaQuincenaPlanB=this.ausenciaService.obtenerCantidadDeVacionesAsiganadasENUnpeRiodoParaUnaFechayUnCargo(pedido.getFechaDesdeSegundaQuincenaPlanB(),pedido.getUser().getTipoCargo());
        modelo.addAttribute("cantidadDeCupoUsadosSegundaQuincenaPlanB", cantidadDeCupoUsadosSegundaQuincenaPlanB);
        Integer cantidadDeCupoUsadosDiasOpuestosPlanB=this.ausenciaService.obtenerCantidadDeVacionesAsiganadasENUnpeRiodoParaUnaFechayUnCargo(pedido.getFechaDesdeDiasOpuestosPlanB(),pedido.getUser().getTipoCargo());
        modelo.addAttribute("cantidadDeCupoUsadosDiasOpuestosPlanB", cantidadDeCupoUsadosDiasOpuestosPlanB);

        Map<User, Map<CicloVacaciones, List<Ausencia>>> mapaVacacionesDeTodosLosUsuarios = this.ausenciaService.obtenerVacacionesDeUnUsuarioAgrupadasCiclos(pedido.getUser());
        modelo.addAttribute("mapaVacacionesDeTodosLosUsuarios", mapaVacacionesDeTodosLosUsuarios);
        Ausencia ausencia = new Ausencia();
        PedidoVacaciones newPedido = new PedidoVacaciones();
        newPedido.setId(pedido.getId());

        modelo.addAttribute("newPedido", newPedido);
        modelo.addAttribute("pedido", pedido);
        modelo.addAttribute("ausencia", ausencia);


        return "detallePedidoALider";
    }

    @GetMapping("/lider/generar-ausencia")
    public String guardarAusencia (@RequestParam Map<String, Object>  params, Model modelo, RedirectAttributes redirectAttributes) {
        PedidoVacaciones pedido = null;
        Long pedidoId =null;
        if(params.get("pedidoId")!= null) {
            pedidoId = Long.parseLong(params.get("pedidoId").toString());
            pedido = pedidoVacacionesService.buscarPedidoVacacionesPorId(pedidoId);
        }
        LocalDate fecha_inicio =null;
        if(params.get("fecha_inicio")!= null)
            fecha_inicio =LocalDate.parse(params.get("fecha_inicio").toString());

        LocalDate fecha_final =null;
        if(params.get("fecha_final")!= null)
            fecha_final =LocalDate.parse(params.get("fecha_final").toString());


        String tipoAusencia =null;
        if(params.get("tipoAusencia")!= null)
            tipoAusencia =params.get("tipoAusencia").toString();


        Long  idPeriodo =null;
        if(params.get("idPeriodo")!= null) {
            idPeriodo = Long.parseLong(params.get("idPeriodo").toString());

        }


        ResumenDeVacaciones resumenDeVacaciones = null;

        Ausencia ausencia = new Ausencia();


        try {


            if (idPeriodo != null) {
                Periodo periodo = this.periodoService.buscarPorID(idPeriodo);
                fecha_inicio = periodo.getFechaInicio();
                if (tipoAusencia.equals("VAC15"))
                    fecha_final = periodo.getFechaInicio().plusDays(15);
                if (tipoAusencia.equals("OPUESTO10"))
                    fecha_final = periodo.getFechaInicio().plusDays(10);

                resumenDeVacaciones = this.ausenciaService.generarResumenAusenciaDesdesugerencia(periodo,pedido,fecha_inicio,fecha_final,tipoAusencia);
            } else {

                resumenDeVacaciones = this.ausenciaService.generarResumenAusencia(pedido,fecha_inicio,fecha_final, tipoAusencia);

            }

            ausencia.setTipoAusencia(resumenDeVacaciones.getTipoAusencia());
            ausencia.setUser(this.userService.getById(resumenDeVacaciones.getUserId()));
            ausencia.setFechaDesde(resumenDeVacaciones.getFechaInicioAusencia());
            ausencia.setFechaHasta(resumenDeVacaciones.getFechaFinalAusencia());
            ausenciaService.validarVacacion(ausencia);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/lider/detallePedido/"+resumenDeVacaciones.getPedidoId();
        }

        Map<User, Map<CicloVacaciones, List<Ausencia>>> mapaVacacionesDeTodosLosUsuarios = this.ausenciaService.obtenerVacacionesDeUnUsuarioAgrupadasCiclos(pedido.getUser());
        modelo.addAttribute("mapaVacacionesDeTodosLosUsuarios", mapaVacacionesDeTodosLosUsuarios);

        modelo.addAttribute("pedido", pedido);
        modelo.addAttribute("fecha_inicio", fecha_inicio);
        modelo.addAttribute("fecha_final", fecha_final);
        modelo.addAttribute("tipoAusencia", tipoAusencia);
        modelo.addAttribute("resumenDeVacaciones", resumenDeVacaciones);
        return "mostrarAusenciaAAprobar";

    }
    @PostMapping("/lider/guardarausencia")
    public String guardarAusencia(@ModelAttribute("resumenDeVacaciones") ResumenDeVacaciones resumenDeVacaciones, RedirectAttributes redirectAttributes) {

        try {
            this.ausenciaService.guardarVacaciones(resumenDeVacaciones);
            redirectAttributes.addFlashAttribute("confirmado", "El pedido de la vacacion fue registrado correctamente");
            return "redirect:/lider/detallePedido/"+resumenDeVacaciones.getPedidoId();//+"/ok";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/lider/detallePedido/"+resumenDeVacaciones.getPedidoId();
        }

    }

    @PostMapping("/lider/guarda-comentario-cierra-pedido")
    public String guardacomentario(@ModelAttribute("newPedido") PedidoVacaciones newPedido) {
        PedidoVacaciones pedidoAActualizar = pedidoVacacionesService.buscarPedidoVacacionesPorId(newPedido.getId());
        pedidoAActualizar.setComentarioDelLider(newPedido.getComentarioDelLider());
        pedidoAActualizar.setEstadoPedido(EstadoPedido.CERRADO);
        pedidoVacacionesService.guardar(pedidoAActualizar);

        return "redirect:/lider/listarPedidos";
    }

    @GetMapping("/lider/errorPage")
    public String apaginadeerror(Model modelo) {
        return "/errorPage";
    }

    @GetMapping("/lider/cerrarPedido/{id}")
    public String cerrarPedido(@PathVariable("id") Long id) {
        PedidoVacaciones estadoCerrado = pedidoVacacionesService.buscarPedidoVacacionesPorId(id);
        estadoCerrado.setEstadoPedido(EstadoPedido.CERRADO);
        pedidoVacacionesService.guardar(estadoCerrado);
        return "redirect:/lider/listarPedidos";
    }

    @GetMapping("/lider/verausenciasvacaciones")
    public String verVacaciones(Model modelo ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());
        List<User> todosLosUsuarios =this.userService.findAll();
        Map<User, Map<CicloVacaciones, List<Ausencia>>> mapaVacacionesDeTodosLosUsuarios=this.ausenciaService.obtenerVacionesAgrupadasPorCiclosParaVariosUsuarios(todosLosUsuarios);
        modelo.addAttribute("mapaVacacionesDeTodosLosUsuarios", mapaVacacionesDeTodosLosUsuarios);
        return "resumendeVacaciones";
    }


    @GetMapping("/lider/generar-vacaciones-desdecuposlibres")
    public String mostrarLibroVuelo(@RequestParam Map<String, Object>  params, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());

        PedidoVacaciones pedidoVacaciones=null;
        if (params.get("idPedido") != null ) {
            pedidoVacaciones=this.pedidoVacacionesService.buscarPedidoVacacionesPorId(Long.parseLong(params.get("idPedido").toString()));
        }
        User usuarioVacacion=null;
        if (params.get("userid") != null ) {
            usuarioVacacion=this.userService.getById(Long.parseLong(params.get("userid").toString()));
        }

        TipoAusencia tipoLicencia=null;
        if (params.get("tipovacacion") != null ) {
            tipoLicencia=TipoAusencia.valueOf(params.get("tipovacacion").toString());
        }




        List<SugerenciaVacacion>  sugerenciaVacaciones =this.periodoService.obtenerSugerenciasVacacionesPorTipoCargo(pedidoVacaciones,usuarioVacacion,tipoLicencia,pedidoVacaciones.getUser().getTipoCargo());

        CicloVacaciones cicloVacaciones=pedidoVacaciones.getCicloVacaciones();

        model.addAttribute("cicloVacaciones", cicloVacaciones);
        model.addAttribute("pedidoVacaciones", pedidoVacaciones);
        model.addAttribute("sugerenciaVacaciones", sugerenciaVacaciones);
        model.addAttribute("user", usuarioVacacion);
       // model.addAttribute("user", user);

        return "mostrarsugerenciaVacaciones";

    }

}
