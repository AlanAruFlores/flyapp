package com.flybook.librovuelo.service;

import com.flybook.librovuelo.model.Notificacion;
import com.flybook.librovuelo.model.User;

import java.util.List;

public interface NotificacionService {

    void save(Notificacion nuevaNotificacion);

    void crearNotificacionesAUsuariosDeVueloCompartido(Long id, User remitente, List<Long> idsUsuarios);

    List<Notificacion> obtenerNotificacionesNoLeidas(User user);

    List<Notificacion> obtenerNotificacionesLeidas(User user);

    void marcarNotificacionComoLeida(Long id);

    void eliminarNotificacion(Long id);

    void marcarNotifiacionComoNoLeida(Long id);

    Notificacion buscarNotificacion(Long id);

    Integer obtenerCatidadDeNotificacionesNoLeidas(User user);
}
