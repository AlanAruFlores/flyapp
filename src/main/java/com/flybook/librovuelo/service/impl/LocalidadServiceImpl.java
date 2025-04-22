package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.model.Localidad;
import com.flybook.librovuelo.repository.LocalidadRepository;
import com.flybook.librovuelo.service.LocalidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalidadServiceImpl implements LocalidadService {

    @Autowired
    private LocalidadRepository localidadRepository;

    @Override
    public List<Localidad> findAll() { return this.localidadRepository.findAllByOrderByNombreAsc();}

    @Override
    public List<Localidad> findByProvincia(Long id) {
        return this.localidadRepository.findAllByProvincia_IdOrderByNombreAsc(id);
    }

    @Override
    public Localidad findLocalidadById(Long id) {
        return this.localidadRepository.findById(id).get();
    }

    @Override
    public Localidad findById(Long id) {
        return this.localidadRepository.getById(id);
    }


    @Override
    public Localidad findByLocalidad(Long id) {

        return this.localidadRepository.getById(id);
    }
}
