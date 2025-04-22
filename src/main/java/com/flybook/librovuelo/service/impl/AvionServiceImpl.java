package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.model.Avion;
import com.flybook.librovuelo.repository.AvionRepository;
import com.flybook.librovuelo.service.AvionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class
AvionServiceImpl implements AvionService {
    @Autowired
    private AvionRepository avionRepository;

    @Override
    public void save(Avion avion) {
        avionRepository.save(avion);
    }

    @Override
    public void delete(Avion avion) {
        avionRepository.delete(avion);
    }

    @Override
    public Avion getById(Long id) {
        return avionRepository.getById(id);
    }

    @Override
    public Avion findByMatricula(String matricula) {
        return avionRepository.findByMatricula(matricula);
    }

    @Override
    public List<Avion> findAll() {
        return avionRepository.findAll();
    }

}
