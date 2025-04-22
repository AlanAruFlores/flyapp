package com.flybook.librovuelo.service;


import com.flybook.librovuelo.model.Vuelo;

import java.util.List;

public interface VueloService {

    List<Vuelo> findAll();

    void save(Vuelo vuelo) throws Exception;

    public Long tiempoVueloMinutos(Double tv);
}
