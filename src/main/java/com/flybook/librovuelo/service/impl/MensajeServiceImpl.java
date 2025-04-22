package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.model.Chat;
import com.flybook.librovuelo.model.Mensaje;
import com.flybook.librovuelo.model.User;
import com.flybook.librovuelo.repository.MensajeRepository;
import com.flybook.librovuelo.service.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MensajeServiceImpl implements MensajeService {

    @Autowired
    private MensajeRepository mensajeRepository;

    @Override
    public List<Mensaje> getAllByChatAndUsuario(Chat chat, User usuario) {
        return mensajeRepository.findAllByChatAndUsuario(chat, usuario);
    }

    @Override
    public List<Mensaje> getAllByChat(Chat chat) {
        return mensajeRepository.findAllByChatOrderByFechaEnvioAsc(chat);
    }

    @Override
    public void save(Mensaje mensaje) {
        mensajeRepository.save(mensaje);
    }

/*

    @Override
    public List<Mensaje> getRecibidosByUser(User user) {
        return mensajeRepository.findAllByUsuarioRecibe(user);
    }
    @Override
    public List<Mensaje> getEnviadosByUser(User user) {
        return mensajeRepository.findAllByUsuarioEnvia(user);
    }

    @Override
    public void save(Mensaje mensaje) {
        mensajeRepository.save(mensaje);
    }*/
}
