package com.flybook.librovuelo.service;

import com.flybook.librovuelo.model.Notificacion;
import com.flybook.librovuelo.model.User;

import java.time.LocalDate;
import java.util.List;

public interface NotificacionUsuarioService {
    void save(List<Long> idsUsuarios, Notificacion notificacion);
}
