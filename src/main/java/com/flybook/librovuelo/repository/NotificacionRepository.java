package com.flybook.librovuelo.repository;

import com.flybook.librovuelo.model.Notificacion;
import com.flybook.librovuelo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {

    List <Notificacion> findAllByUserAndLeidaIsFalse(User user);
    List <Notificacion> findAllByUserAndLeidaIsTrue(User user);
    Optional findById(Long id);

}
