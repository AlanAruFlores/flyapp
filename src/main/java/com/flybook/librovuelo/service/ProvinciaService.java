package com.flybook.librovuelo.service;

import com.flybook.librovuelo.model.Provincia;

import java.util.List;

public interface ProvinciaService {

    List<Provincia> findAll();

    Provincia findById(Long id);
}
