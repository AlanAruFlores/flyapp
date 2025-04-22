package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.model.Provincia;
import com.flybook.librovuelo.repository.ProvinciaRepository;
import com.flybook.librovuelo.service.ProvinciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinciaServiceImpl implements ProvinciaService {

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Override
    public List<Provincia> findAll() { return this.provinciaRepository.findAllByOrderByNombreAsc(); }

    @Override
    public Provincia findById(Long id) {
        return this.provinciaRepository.findById(id).get();
    }
}
