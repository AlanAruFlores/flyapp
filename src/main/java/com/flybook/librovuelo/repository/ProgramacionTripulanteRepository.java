package com.flybook.librovuelo.repository;

import com.flybook.librovuelo.model.ProgramacionTripulante;
import com.flybook.librovuelo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface ProgramacionTripulanteRepository extends JpaRepository<ProgramacionTripulante, Long> {
    List<ProgramacionTripulante> findByTripulante(User tripulante);
    List<ProgramacionTripulante> findAllByDiaActividadBetweenAndTripulante(LocalDate fechaDesde, LocalDate fechaHasta, User tripulante);

    //findAllByFechaSolicitudGreaterThanEqualAndFechaSolicitudLessThanEqualOrderByFechaSolicitudDesc

}
