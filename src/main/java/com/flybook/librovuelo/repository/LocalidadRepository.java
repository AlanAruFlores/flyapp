package com.flybook.librovuelo.repository;

import com.flybook.librovuelo.model.Localidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocalidadRepository extends JpaRepository<Localidad, Long> {

    List<Localidad> findAllByOrderByNombreAsc();

    List<Localidad> findAllByProvincia_IdOrderByNombreAsc(Long id);

    Localidad findByNombre(String nombre);
}
