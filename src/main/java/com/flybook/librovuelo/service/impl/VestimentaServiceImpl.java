package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.model.User;
import com.flybook.librovuelo.model.Vestimenta;
import com.flybook.librovuelo.repository.VestimentaRepository;
import com.flybook.librovuelo.service.VestimentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VestimentaServiceImpl implements VestimentaService {

    @Autowired
    VestimentaRepository vestimentaRepository;

    @Override
    public void guardarVestimenta(Vestimenta vestimenta, User user) {
        vestimenta.setUser(user);
       // user.setVestimenta(vestimenta);
        vestimentaRepository.save(vestimenta);
    }

    @Override
    public Vestimenta getByUserId(Long userId) {
        return vestimentaRepository.getByUser_Id(userId);

    }

    @Override
    public Vestimenta getById(Long id) {
        return vestimentaRepository.getById(id);
    }

    @Override
    public Vestimenta findVestimentaByUserId(Long id) {
        return vestimentaRepository.findVestimentaByUserId(id);
    }

}
