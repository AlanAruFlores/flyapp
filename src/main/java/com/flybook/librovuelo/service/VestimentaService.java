package com.flybook.librovuelo.service;

import com.flybook.librovuelo.model.User;
import com.flybook.librovuelo.model.Vestimenta;
import org.springframework.stereotype.Service;

import java.util.List;


public interface VestimentaService {

    void guardarVestimenta(Vestimenta vestimenta, User user);

    Vestimenta getByUserId(Long userId);
    Vestimenta getById(Long id);

    Vestimenta findVestimentaByUserId(Long id);
}
