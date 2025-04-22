package com.flybook.librovuelo.service;

import com.flybook.librovuelo.model.Chat;
import com.flybook.librovuelo.model.Noticia;
import com.flybook.librovuelo.model.User;

import java.util.List;

public interface ChatService {
    List<Chat> getAllByRemitente(User remitente);
    List<Chat> getAllByDestinatario(User remitente);

    Chat getByRemitenteAndDestinatario(User remitente, User destinatario);

    void save (Chat chat);

    Chat getById(Long id);
    Chat findById(Long id);

}
