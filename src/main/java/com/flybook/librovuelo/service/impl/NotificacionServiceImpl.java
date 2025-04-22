package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.model.Notificacion;
import com.flybook.librovuelo.model.TipoNotificacion;
import com.flybook.librovuelo.model.User;
import com.flybook.librovuelo.model.VueloRealizado;
import com.flybook.librovuelo.repository.NotificacionRepository;
import com.flybook.librovuelo.repository.UserRepository;
import com.flybook.librovuelo.service.NotificacionService;
import com.flybook.librovuelo.service.VueloRealizadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class NotificacionServiceImpl implements NotificacionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Autowired
    private VueloRealizadoService vueloRealizadoService;


    @Override
    public void save(Notificacion nuevaNotificacion) {
        notificacionRepository.save(nuevaNotificacion);
    }

    @Override
    public void crearNotificacionesAUsuariosDeVueloCompartido(Long id, User remitente, List<Long> idsUsuarios) {

        VueloRealizado vueloCompartido=this.vueloRealizadoService.getById(id);

        for (Long idUsuario : idsUsuarios){
            User userANotificar = this.userRepository.findUserById(idUsuario);
            Notificacion notificacion  = new Notificacion();
            notificacion.setUser(userANotificar);
            notificacion.setFechaEnvio(LocalDate.now());
            notificacion.setTipo(TipoNotificacion.LIBROVUELO);
            String mensaje= crearMensajeNotificacionDeVueloCompartio(remitente, vueloCompartido);
            notificacion.setMensaje(mensaje);
            notificacion.setRemitente(remitente);
            notificacion.setLeida(false);
            notificacion.setIdVueloRealizado(id);
            notificacion.setAction("tripulante/cargarlibrovuelodesdenotificacion");
            this.notificacionRepository.save(notificacion);


        }

    }

    @Override
    public List<Notificacion> obtenerNotificacionesNoLeidas(User user) {
        return this.notificacionRepository.findAllByUserAndLeidaIsFalse(user);
    }

    @Override
    public List<Notificacion> obtenerNotificacionesLeidas(User user) {
        return this.notificacionRepository.findAllByUserAndLeidaIsTrue(user);
    }

    @Override
    public void marcarNotificacionComoLeida(Long id) {
        Notificacion notificacion=notificacionRepository.getById(id);
        notificacion.setLeida(true);
        this.notificacionRepository.save(notificacion);
    }

    @Override
    public void eliminarNotificacion(Long id) {
        this.notificacionRepository.deleteById(id);
    }

    @Override
    public void marcarNotifiacionComoNoLeida(Long id) {
      Notificacion notificacion =this.notificacionRepository.getById(id);
      notificacion.setLeida(false);
      this.notificacionRepository.save(notificacion);
    }

    @Override
    public Notificacion buscarNotificacion(Long id) {

         Optional<Notificacion> notificacion =this.notificacionRepository.findById(id);
         return notificacion.get();
    }

    private String crearMensajeNotificacionDeVueloCompartio(User remitente, VueloRealizado vueloCompartido) {
        return remitente.obtenerNombreApellidoLegajo() + "compartio  el vuelo desde " + vueloCompartido.getAeropuertoOrigen().getNombre() + " hasta " + vueloCompartido.getAeropuertoDestino().getNombre() + " el dia " + vueloCompartido.getFechahoraDespegue().toLocalDate().toString() + " Con un total de TV " + vueloCompartido.getTotalDeHoras();
    }
     public Integer obtenerCatidadDeNotificacionesNoLeidas(User user){
        return obtenerNotificacionesNoLeidas(user).size();
     }
}
