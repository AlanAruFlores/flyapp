package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.model.Recurrent;
import com.flybook.librovuelo.repository.RecurrentRepository;
import com.flybook.librovuelo.service.AusenciaService;
import com.flybook.librovuelo.service.RecurrentService;
import com.flybook.librovuelo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecurrentServiceImpl implements RecurrentService {

    @Autowired
    private RecurrentRepository recurrentRepository;


    @Autowired
    private AusenciaService ausenciaService;

    @Autowired
    private UserService userServecie;

    @Override
    public void save(Recurrent recurrent) {
        recurrentRepository.save(recurrent);
    }

    @Override
    public void delete(Recurrent recurrent) {
        recurrentRepository.delete(recurrent);
    }

    @Override
    public Optional<Recurrent> findById(Long idRecurrent) {
        return recurrentRepository.findById(idRecurrent);
    }

    @Override
    public List<Recurrent> findAll() {
        return recurrentRepository.findAll();
    }

    @Override
    public List<Recurrent> findAll(Sort sort) {
        return recurrentRepository.findAll(sort);
    }

    @Override
    public Recurrent findRecurrentById(Long id) {
        return recurrentRepository.findRecurrentById(id);
    }

    @Override
    public Recurrent getById(Long id) {
        return recurrentRepository.getById(id);
    }

    @Override
    public List<Recurrent> findRecurrentsById(Long id) {
        return recurrentRepository.findRecurrentsById(id);
    }

   }
