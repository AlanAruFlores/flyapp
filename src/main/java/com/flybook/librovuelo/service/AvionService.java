package com.flybook.librovuelo.service;

import com.flybook.librovuelo.model.Avion;

import java.util.List;

public interface AvionService {
    void save(Avion avion);
    void delete(Avion avion);
    Avion getById(Long id);
    Avion findByMatricula(String matricula);
    List<Avion> findAll();
}
