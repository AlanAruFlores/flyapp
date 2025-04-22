package com.flybook.librovuelo.repository;

import com.flybook.librovuelo.model.Documentacion;
import com.flybook.librovuelo.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DocumentacionRepository extends JpaRepository<Documentacion, Long> {

    Documentacion findByUser(User username);
    List<Documentacion> findAllByUser(Sort sort, User user);
    //List<Documentacion> findAllByUsername(Sort sort, List<User> users);
    Optional<Documentacion> findById (Long idDocumentacion);
    Documentacion findDocumentacionById(Long id);
    List<Documentacion> findAllByUser(User user);
    List<Documentacion> findDocumentacionsById(Long id);
    List<Documentacion> findAllDocumentacionByNotificacionActivadaIsTrueAndFechaDeVencimientoGreaterThanAndFechaDeVencimientoIsLessThanEqual(LocalDate hoy, LocalDate fecha);
}
