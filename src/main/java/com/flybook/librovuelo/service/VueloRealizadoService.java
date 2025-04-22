package com.flybook.librovuelo.service;

import com.flybook.librovuelo.model.Foliado;
import com.flybook.librovuelo.model.Notificacion;
import com.flybook.librovuelo.model.User;
import com.flybook.librovuelo.model.VueloRealizado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface VueloRealizadoService {

     void save(VueloRealizado vueloRealizado);
     void delete(VueloRealizado vueloRealizado);
     List<VueloRealizado> obtenerVuelosRealizadosEntreDosFecha (LocalDateTime desde, LocalDateTime hasta);
     List<VueloRealizado> findAllByUser(User user);
     List<VueloRealizado> findAllByFoliado(Foliado foliado);
     VueloRealizado getById(Long id);

     Double calcularhorasEntreDosFechas(LocalDateTime inicial, LocalDateTime fin);
     Double calcularCantidadDeHorasDiurna(LocalDateTime inicial, LocalDateTime fin);
     Double calcularCantidadDeHorasNocturna(LocalDateTime inicial, LocalDateTime fin);
     Double calcularTiempoTotalVuelo(LocalDateTime despegue, LocalDateTime aterrizaje) ;

    Boolean validarHorasDeVuelosRelizados(User user,VueloRealizado vueloRealizado) throws  Exception;

     Double obtenerTotalDeHorasDiurnas(List<VueloRealizado> vuelosRealizados);

     Double obtenerTotalDeHorasNocturnas(List<VueloRealizado> vuelosRealizados);

     Double obtenerTotalDeHoras(List<VueloRealizado> vuelosRealizados);

     Integer obtenerTotalAterrizajes(List<VueloRealizado> vuelosRealizados);

    Page<VueloRealizado> findAllByUser(User user, Pageable pageable);


    Page<VueloRealizado> buscarHorasfoliadas(User user, Foliado foliado, Pageable pageable);

    Double obtenerTotalDeHorasFoliada(User user);
    Double obtenerTotalDeHorasFoliadaNocturna(User user);
    Double obtenerTotalDeHorasFoliadaDiurnas(User user);
    Integer obtenerTotalDeAterrizajeFoliada(User user);
    Page<VueloRealizado> buscarHorasNofoliadas(User user, PageRequest pageRequest);


    void asignarFoliadoAAHorasNoFoliadas(List<VueloRealizado> vuelosNofoliados, Foliado nuevoFoliado);

    Page<VueloRealizado> findAllByUserAndFoliado(User user, Foliado foliado, PageRequest pageRequest);

    List<VueloRealizado> findAllByUserAndFoliado(User user, Foliado foliado);

    VueloRealizado obtenerVueloRealizadoDesdeNotificacion(User user, Notificacion notificacion);

    Double obtenerCantidadTotalDeHorasDiurnas(User user);

    Double obtenerCantidadTotalDeHorasNocturnas(User user);

    Double obtenerCantidadTotalDeHoras(User user);

    Integer obtenerCantidadTotalDeAterrizaje(User user);
}
