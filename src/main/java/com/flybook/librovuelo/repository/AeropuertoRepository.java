package com.flybook.librovuelo.repository;


import com.flybook.librovuelo.model.Aeropuerto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AeropuertoRepository extends JpaRepository<Aeropuerto, Long> {

    Aeropuerto findByCodigo(String codigo);
}
