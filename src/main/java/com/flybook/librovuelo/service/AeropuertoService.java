package com.flybook.librovuelo.service;

import com.flybook.librovuelo.model.Aeropuerto;

import java.util.List;

public interface AeropuertoService {
    void save(Aeropuerto aeropuerto);
    void delete(Aeropuerto aeropuerto);
    Aeropuerto findByCodigo(String codigo);
    List<Aeropuerto> findAll();

    Aeropuerto getById(Long id);
}
