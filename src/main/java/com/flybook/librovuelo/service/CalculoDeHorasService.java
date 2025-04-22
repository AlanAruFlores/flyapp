package com.flybook.librovuelo.service;

import com.flybook.librovuelo.model.VueloRealizado;

import java.time.LocalDateTime;

public interface CalculoDeHorasService {

    Double calcularhorasEntreDosFechas(LocalDateTime inicial, LocalDateTime fin);
    Double calcularCantidadDeHorasDiurna(LocalDateTime inicial, LocalDateTime fin);
    Double calcularCantidadDeHorasNocturna(LocalDateTime inicial, LocalDateTime fin);
    Double calcularTiempoTotalVuelo(LocalDateTime despegue, LocalDateTime aterrizaje) ;

    void calcularTodasLasHorasDeUnVuelo(VueloRealizado vueloRealizado);
}
