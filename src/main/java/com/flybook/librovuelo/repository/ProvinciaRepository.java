package com.flybook.librovuelo.repository;

import com.flybook.librovuelo.model.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProvinciaRepository extends JpaRepository<Provincia, Long> {

    List<Provincia> findAllByOrderByNombreAsc();
}
