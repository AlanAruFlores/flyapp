package com.flybook.librovuelo.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flybook.librovuelo.dto.MensajeDTO;
import com.flybook.librovuelo.model.Chat;
import com.flybook.librovuelo.model.Mensaje;
import com.flybook.librovuelo.model.User;
import com.flybook.librovuelo.service.ChatService;
import com.flybook.librovuelo.service.MensajeService;
import com.flybook.librovuelo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class ChatWebSocketController {

    @Autowired
    UserService userService;

    @Autowired
    ChatService chatService;

    @Autowired
    MensajeService mensajeService;

    @MessageMapping("/enviar")
    @SendTo("/topic/recibir")
    public String enviarRecibirMensajes(MensajeDTO mensajeDTO) throws JsonProcessingException {
        User user = this.userService.findUserById(mensajeDTO.getIdUsuario());
        Chat chat = this.chatService.getById(mensajeDTO.getIdChat());
        String mensaje = mensajeDTO.getMensaje();

        //Nuevo Mensaje
        Mensaje mensajeNuevo = new Mensaje();
        mensajeNuevo.setUsuario(user);
        mensajeNuevo.setMensaje(mensaje);
        mensajeNuevo.setChat(chat);
        mensajeService.save(mensajeNuevo);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString("MensajeEnviado");
        return json;
    }
}
