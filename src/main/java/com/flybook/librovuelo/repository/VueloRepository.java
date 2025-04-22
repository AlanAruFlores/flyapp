package com.flybook.librovuelo.repository;


import com.flybook.librovuelo.model.Vuelo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VueloRepository extends JpaRepository<Vuelo, Long> {
    
}
