package com.flybook.librovuelo.service;

import com.flybook.librovuelo.model.Generacion;

import java.util.List;

public interface GeneracionService {

    void save(Generacion generacion) throws Exception;
    void delete(Generacion generacion);

    List<Generacion> findAll();

    Generacion getById(Long id);

    Generacion findByNumero(Integer numero);

}
