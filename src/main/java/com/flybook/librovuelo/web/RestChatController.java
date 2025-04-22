package com.flybook.librovuelo.web;

import com.flybook.librovuelo.model.Chat;
import com.flybook.librovuelo.model.Mensaje;
import com.flybook.librovuelo.model.User;
import com.flybook.librovuelo.service.ChatService;
import com.flybook.librovuelo.service.MensajeService;
import com.flybook.librovuelo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class RestChatController {
    @Autowired
    private MensajeService mensajeService;
    @Autowired
    private ChatService chatService;

    @GetMapping("/getMensajesByChat")
    public List<Mensaje> getAllMensajes(@RequestParam("id") Long id) {
        Chat chat = chatService.findById(id);
        return this.mensajeService.getAllByChat(chat);
    }
}
