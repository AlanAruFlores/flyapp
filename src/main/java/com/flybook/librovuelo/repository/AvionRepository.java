package com.flybook.librovuelo.repository;


import com.flybook.librovuelo.model.Avion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvionRepository  extends JpaRepository<Avion, Long> {

    public Avion findByMatricula(String matricula);

}
