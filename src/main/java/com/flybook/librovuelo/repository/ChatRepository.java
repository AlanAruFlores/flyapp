package com.flybook.librovuelo.repository;

import com.flybook.librovuelo.model.Chat;
import com.flybook.librovuelo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    List<Chat> findAllByRemitente(User remitente);

    Chat findByRemitenteAndDestinatario(User remitente, User destinatario);

    List<Chat> findAllByDestinatario(User destinatario);

    Chat findChatById(Long id);
}
