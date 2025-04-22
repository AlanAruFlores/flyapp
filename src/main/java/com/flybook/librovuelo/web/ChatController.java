package com.flybook.librovuelo.web;

import com.flybook.librovuelo.model.*;
import com.flybook.librovuelo.service.ChatService;
import com.flybook.librovuelo.service.MensajeService;
import com.flybook.librovuelo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Controller
public class ChatController {

    @Autowired
    private MensajeService mensajeService;
    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    @GetMapping("/chats")
    public String chats(Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User usuario = this.userService.findByUsername(authentication.getName());

        List<Chat> chatsRemitente = this.chatService.getAllByRemitente(usuario);
        List<Chat> chatsDestinatario = this.chatService.getAllByDestinatario(usuario);

        List<Chat> chats = Stream.concat(chatsRemitente.stream(), chatsDestinatario.stream())
                .collect(Collectors.toList());

        List<User> usuarios = userService.findAll();

        model.addAttribute("usuario", usuario);
        model.addAttribute("chatNuevo", new Chat());
        model.addAttribute("usuarios", usuarios);
        model.addAttribute("chats", chats);

        return "chats";
    }

    //form para generar chat nuevo aqui va el boton
    @PostMapping("/chats")
    public String chats(@ModelAttribute("chatNuevo") Chat chatNuevo, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());

        chatNuevo.setRemitente(user);

        User remitente = chatNuevo.getRemitente();
        User destinatario =chatNuevo.getDestinatario();
        Chat chatEncontradoRemitente = chatService.getByRemitenteAndDestinatario(remitente, destinatario);
        Chat chatEncontradoDestinatario = chatService.getByRemitenteAndDestinatario(destinatario, remitente);

        if(destinatario!= null & remitente != destinatario && chatEncontradoRemitente == null
                && chatEncontradoDestinatario == null){
            chatService.save(chatNuevo);
        }else {
            return "redirect:/chats";
        }

        return "redirect:/chat/"+ chatNuevo.getId();
    }


    @GetMapping("/chat/{id}")
    public String chat(@PathVariable("id") Long id, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User usuario = this.userService.findByUsername(authentication.getName());

        Chat chat = chatService.getById(id);

        User remitente = chat.getRemitente();
        User destinatario = chat.getDestinatario();

        if(usuario == remitente || usuario == destinatario){

        List<Mensaje> mensajes = mensajeService.getAllByChat(chat);

            List<Chat> chatsRemitente = this.chatService.getAllByRemitente(usuario);
            List<Chat> chatsDestinatario = this.chatService.getAllByDestinatario(usuario);

            List<Chat> chats = Stream.concat(chatsRemitente.stream(), chatsDestinatario.stream())
                    .collect(Collectors.toList());

            List<User> usuarios = userService.findAll();

            model.addAttribute("chatNuevo", new Chat());
            model.addAttribute("usuarios", usuarios);
            model.addAttribute("chats", chats);

        model.addAttribute("usuario", usuario);
        model.addAttribute("remitente", remitente);
        model.addAttribute("destinatario", destinatario);
        model.addAttribute("chatActual", chat);
        model.addAttribute("mensajes", mensajes);
        model.addAttribute("mensajeNuevo", new Mensaje());

        return "chats";
        }
        return "redirect:/";
    }

    @PostMapping("/chat")
    public String chat(@ModelAttribute("mensajeNuevo") Mensaje mensajeNuevo, Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = this.userService.findByUsername(authentication.getName());

        mensajeNuevo.setUsuario(user);
        mensajeService.save(mensajeNuevo);
        System.out.println(mensajeNuevo);
        return "redirect:/chat/"+mensajeNuevo.getChat().getId();
    }

}