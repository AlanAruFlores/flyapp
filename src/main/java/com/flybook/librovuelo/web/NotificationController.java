package com.flybook.librovuelo.web;

import com.flybook.librovuelo.dto.DatosNotificacionUsuario;
import com.flybook.librovuelo.model.*;
import com.flybook.librovuelo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/tripulante")
public class NotificationController {

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private UserService userService;

    @Autowired
    private CalculoDeHorasService calculoDeHorasService;


    @Autowired
    private AvionService avionService;

    @Autowired
    private AeropuertoService aeropuertoService;




    @Autowired
    private VueloRealizadoService vueloRealizadoService;

    @GetMapping("/notificaciones")
    public String notificaciones(@RequestParam Map<String, Object> params, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());

        List<Notificacion> notificacionesNoLeidas = this.notificacionService.obtenerNotificacionesNoLeidas(user);
        List<Notificacion> notificacionesLeidas = this.notificacionService.obtenerNotificacionesLeidas(user);
        if(params.get("idVueloAgreagdo") != null){
            long idVueloAgregado=Long.parseLong(params.get("idVueloAgreagdo").toString());
            VueloRealizado vueloCompartido=this.vueloRealizadoService.getById(idVueloAgregado);
            String mensaje=  "Se ha agrgado  el vuelo desde " + vueloCompartido.getAeropuertoOrigen().getNombre() + " hasta " + vueloCompartido.getAeropuertoDestino().getNombre() + " el dia " + vueloCompartido.getFechahoraDespegue().toLocalDate().toString() + " Con un total de TV " + vueloCompartido.getTotalDeHoras();

            model.addAttribute("vueloAgregado", mensaje);

        }
        Integer cantidadNotidicacionesNoLeidas=this.notificacionService.obtenerCatidadDeNotificacionesNoLeidas(user);
        model.addAttribute("cantidadNotidicacionesNoLeidas",cantidadNotidicacionesNoLeidas);
        model.addAttribute("notificacionesNoLeidas", notificacionesNoLeidas);
        model.addAttribute("notificacionesLeidas", notificacionesLeidas);
        model.addAttribute("user", user);
        return "notificaciones";
    }
    @GetMapping("/nuevaNotificacion/{id}")
    public String notificacion(@PathVariable("id") Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());

        VueloRealizado vueloRealizadoAsociado = this.vueloRealizadoService.getById(id);
        DatosNotificacionUsuario datosNotificacionUsuario = new DatosNotificacionUsuario();
        datosNotificacionUsuario.setIdVueloRealizado(vueloRealizadoAsociado.getId());
        List<User> tripulantes = userService.findAll();
      /*  List<TripulanteRecurrent> tr = tripulanteRecurrentService.findTripulanteRecurrentsByRecurrent
                (recurrentAsociado);
*/
        model.addAttribute("listaDeTripulantes", tripulantes);
     //   model.addAttribute("recurrent", recurrentAsociado);
        model.addAttribute("usuario", user);
        model.addAttribute("datosNotificacionUsuario", datosNotificacionUsuario);
        model.addAttribute("title", "Ver notificaciones");
      //  model.addAttribute("listaDeTripulanteRecurrent", tr);

        return "redirect:/tripulante/librovuelo";
    }



    @PostMapping("/nuevaNotificacion/{id}")
    public String notificaciones(@PathVariable("id") Long id, @ModelAttribute("datosNotificacionUsuario") DatosNotificacionUsuario datosNotificacionUsuario, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "notificaciones";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User remitente = this.userService.findByUsername(authentication.getName());
        LocalDate fecha = LocalDate.now();


        this.notificacionService.crearNotificacionesAUsuariosDeVueloCompartido(id,remitente, datosNotificacionUsuario.getIdsUsuarios());


        return "redirect:/tripulante/librovuelo";
    }

    @GetMapping("/rechazarnotifiacion/{id}")
    public String rechazarNotifiacion(@PathVariable("id") Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());

        this.notificacionService.marcarNotificacionComoLeida(id);
      /*  List<TripulanteRecurrent> tr = tripulanteRecurrentService.findTripulanteRecurrentsByRecurrent
                (recurrentAsociado);
*/


        return "redirect:/tripulante/notificaciones";
    }

    @GetMapping("/eliminar/notifiacion/{id}")
    public String eliminarNotifiacion(@PathVariable("id") Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());

        this.notificacionService.eliminarNotificacion(id);
      /*  List<TripulanteRecurrent> tr = tripulanteRecurrentService.findTripulanteRecurrentsByRecurrent
                (recurrentAsociado);
*/


        return "redirect:/tripulante/notificaciones";
    }

    @GetMapping("/marcarnotifiacionnoleida/{id}")
    public String marcarNotifiacionComoNoLeida(@PathVariable("id") Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());

        this.notificacionService.marcarNotifiacionComoNoLeida(id);
      /*  List<TripulanteRecurrent> tr = tripulanteRecurrentService.findTripulanteRecurrentsByRecurrent
                (recurrentAsociado);
*/


        return "redirect:/tripulante/notificaciones";
    }


    @GetMapping("/aceptarnotifiacionlibrovuelo/{id}")
    public String aceptarNotifiacionLibrovuelo(@PathVariable("id") Long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());



        Notificacion notificacion=this.notificacionService.buscarNotificacion(id.longValue());

        VueloRealizado vueloRealizado= this.vueloRealizadoService.obtenerVueloRealizadoDesdeNotificacion(user, notificacion);

        try {
            this.vueloRealizadoService.validarHorasDeVuelosRelizados(user,vueloRealizado);
        } catch(Exception e){
            this.notificacionService.marcarNotificacionComoLeida(id);
            List<Aeropuerto> aeropuertos = this.aeropuertoService.findAll();
            List<Avion> aviones = this.avionService.findAll();
            model.addAttribute("vueloRealizado", vueloRealizado);
            model.addAttribute("aeropuertos", aeropuertos);
            model.addAttribute("aviones", aviones);
          //  model.addAttribute("action", "/fbtripulantes/tripulante/librovuelo/registrar");
            model.addAttribute("title", "Registrar vuelo");
            model.addAttribute("error",e.getMessage());
            return "librovuelo-form";
        }
        this.calculoDeHorasService.calcularTodasLasHorasDeUnVuelo(vueloRealizado);
        this.vueloRealizadoService.save(vueloRealizado);
        this.notificacionService.eliminarNotificacion(notificacion.getId());


       // return "redirect:/tripulante/librovuelo";
       return "redirect:/tripulante/notificaciones?idVueloAgreagdo="+vueloRealizado.getId();
    }


}

