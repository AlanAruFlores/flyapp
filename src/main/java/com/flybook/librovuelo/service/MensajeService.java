package com.flybook.librovuelo.service;

import com.flybook.librovuelo.model.Chat;
import com.flybook.librovuelo.model.Mensaje;
import com.flybook.librovuelo.model.User;

import java.util.List;

public interface MensajeService {

    List <Mensaje> getAllByChatAndUsuario(Chat chat, User usuario);

    List <Mensaje> getAllByChat(Chat chat);

    void save (Mensaje mensaje);

       /* List<Mensaje> getRecibidosByUser(User usuario);

        List<Mensaje> getEnviadosByUser(User usuario);

        void save (Mensaje mensaje);*/
}
