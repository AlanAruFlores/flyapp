package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.model.Chat;
import com.flybook.librovuelo.model.Mensaje;
import com.flybook.librovuelo.model.Noticia;
import com.flybook.librovuelo.model.User;
import com.flybook.librovuelo.repository.ChatRepository;
import com.flybook.librovuelo.repository.MensajeRepository;
import com.flybook.librovuelo.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Override
    public List<Chat> getAllByRemitente(User remitente) {
        return chatRepository.findAllByRemitente(remitente);
    }

    @Override
    public List<Chat> getAllByDestinatario(User destinatario) {
        return chatRepository.findAllByDestinatario(destinatario);
    }

    @Override
    public Chat getByRemitenteAndDestinatario(User remitente, User destinatario) {
        return chatRepository.findByRemitenteAndDestinatario(remitente, destinatario);
    }

    @Override
    public void save(Chat chat) {
        chatRepository.save(chat);
    }

    @Override
    public Chat getById(Long id) {return chatRepository.getById(id);}

    @Override
    public Chat findById(Long id) {return chatRepository.findChatById(id);}
}
