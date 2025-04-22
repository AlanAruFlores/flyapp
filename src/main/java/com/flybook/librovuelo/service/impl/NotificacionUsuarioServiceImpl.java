package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.model.*;
import com.flybook.librovuelo.repository.NotificacionUsuarioRepository;
import com.flybook.librovuelo.repository.UserRepository;
import com.flybook.librovuelo.repository.VueloRealizadoRepository;
import com.flybook.librovuelo.service.NotificacionUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

@Service
public class NotificacionUsuarioServiceImpl implements NotificacionUsuarioService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    VueloRealizadoRepository vueloRealizadoRepository;

    @Autowired
    private NotificacionUsuarioRepository notificacionUsuarioRepository;

    @Override
    public void save(List<Long> idsUsuarios, Notificacion notificacion) {
        VueloRealizado vuelo = vueloRealizadoRepository.getById(notificacion.getIdVueloRealizado());
        Long id = vuelo.getId();

        if (id != null && idsUsuarios!=null && idsUsuarios.size()!=0) {
            Iterator<Long> iter = idsUsuarios.iterator();
            while(iter.hasNext()) {

                User usuario = userRepository.findUserById(iter.next());
                NotificacionUsuario registro = new NotificacionUsuario();

                registro.setUsuario(usuario);
                registro.setNotificacion(notificacion);

                this.notificacionUsuarioRepository.save(registro);

            }
        }
    }
}
