package com.flybook.librovuelo.repository;

import com.flybook.librovuelo.model.Chat;
import com.flybook.librovuelo.model.Mensaje;
import com.flybook.librovuelo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensajeRepository extends JpaRepository<Mensaje, Long> {

    List<Mensaje> findAllByChatAndUsuario(Chat chat, User usuario);

    List<Mensaje> findAllByChatOrderByFechaEnvioDesc(Chat chat);

    List<Mensaje> findAllByChatOrderByFechaEnvioAsc(Chat chat);


    /*
    List<Mensaje> findAllByUsuarioEnvia(User usuarioEnvia);

    List<Mensaje> findAllByUsuarioRecibe(User usuarioRecibe);*/
}
