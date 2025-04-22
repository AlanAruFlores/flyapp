package com.flybook.librovuelo.service;

import com.flybook.librovuelo.model.Localidad;

import java.util.List;

public interface LocalidadService {

    List<Localidad> findAll();

    List<Localidad> findByProvincia(Long id);

    Localidad findLocalidadById(Long id);

    Localidad findById(Long id);

    Localidad findByLocalidad(Long id);
}
