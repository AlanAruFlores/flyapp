package com.flybook.librovuelo.web;

import com.flybook.librovuelo.model.*;
import com.flybook.librovuelo.service.MensajeService;
import com.flybook.librovuelo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;


@Controller
public class MensajeController {

   /* @Autowired
    private MensajeService mensajeService;
    @Autowired
    private UserService userService;


    @GetMapping("tripulante/mensajes-recibidos")
    public String mensajesRecibidos(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User usuario = this.userService.findByUsername(authentication.getName());

        List<Mensaje> mensajesRecibidos = this.mensajeService.getRecibidosByUser(usuario);

        model.addAttribute("mensajesRecibidos", mensajesRecibidos);

        return "chat-nuevo";
    }

    @GetMapping("tripulante/mensajes-enviados")
    public String mensajesEnviados(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User usuario = this.userService.findByUsername(authentication.getName());

        List<Mensaje> mensajesEnviados = this.mensajeService.getEnviadosByUser(usuario);

        model.addAttribute("mensajesEnviados", mensajesEnviados);

        return "mensajes-enviados";
    }

    @GetMapping("tripulante/mensaje-nuevo")
    public String mensajeNuevo(Model model) {

        Mensaje nuevoMensaje = new Mensaje();
        nuevoMensaje.setFechaEnvio(LocalDateTime.now());

        List<User> usuarios = userService.findAll();

        model.addAttribute("mensaje", nuevoMensaje);
        model.addAttribute("usuarios", usuarios);

        return "mensaje-nuevo";
    }

    @PostMapping("tripulante/mensaje-enviar")
    public String enviarMensaje(@ModelAttribute("mensaje") Mensaje mensaje, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());

        mensaje.setUsuarioEnvia(user);
        mensaje.setFechaEnvio(LocalDateTime.now());

        mensajeService.save(mensaje);

        return "redirect:/tripulante/mensajes-recibidos";
    }

*/
}
