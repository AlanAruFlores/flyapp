package com.flybook.librovuelo.repository;

import com.flybook.librovuelo.model.Estacion;
import com.flybook.librovuelo.model.HorarioPorEstacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface HorarioPorEstacionRepository extends JpaRepository<HorarioPorEstacion, Long> {


    HorarioPorEstacion findByEstacion(Estacion estacion);


}
