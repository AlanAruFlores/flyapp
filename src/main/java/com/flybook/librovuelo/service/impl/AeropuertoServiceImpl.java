package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.model.Aeropuerto;
import com.flybook.librovuelo.repository.AeropuertoRepository;
import com.flybook.librovuelo.service.AeropuertoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AeropuertoServiceImpl implements AeropuertoService {
    @Autowired
    private AeropuertoRepository aeropuertoRepository;

    @Override
    public void save(Aeropuerto aeropuerto){


        aeropuertoRepository.save(aeropuerto);

    }

    @Override
    public void delete(Aeropuerto aeropuerto) {
        aeropuertoRepository.delete(aeropuerto);
    }

    @Override
    public Aeropuerto findByCodigo(String codigo) {
        return aeropuertoRepository.findByCodigo(codigo);
    }

    @Override
    public List<Aeropuerto> findAll() {
        return aeropuertoRepository.findAll();
    }
    @Override
    public Aeropuerto getById(Long id) {
        return aeropuertoRepository.getById(id);
    }
}
