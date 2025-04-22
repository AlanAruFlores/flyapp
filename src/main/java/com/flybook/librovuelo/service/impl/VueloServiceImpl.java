package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.model.Vuelo;
import com.flybook.librovuelo.repository.VueloRepository;
import com.flybook.librovuelo.service.VueloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class VueloServiceImpl implements VueloService {

    @Autowired
    private VueloRepository vueloRepository;

    @Override
    public List<Vuelo> findAll() {
        return this.vueloRepository.findAll();
    }

    @Override
    public void save(Vuelo vuelo) throws Exception {
        if (vuelo.getTv() != null) {
            this.vueloRepository.save(vuelo);
        } else {
            throw new Exception("Ingrese un tiempo de vuelo");
        }

    }

    public Long tiempoVueloMinutos(Double tv) {
        Long minutos = 0L;
        Double tiempoVuelo = tv * 60;
        minutos = tiempoVuelo.longValue();
        return minutos;
    }
}
