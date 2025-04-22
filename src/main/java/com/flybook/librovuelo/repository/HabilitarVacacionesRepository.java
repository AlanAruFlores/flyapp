package com.flybook.librovuelo.repository;

import com.flybook.librovuelo.dto.EstadoRegistro;
import com.flybook.librovuelo.dto.HabilitarVacaciones;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HabilitarVacacionesRepository extends JpaRepository<HabilitarVacaciones, Long> {
    HabilitarVacaciones findByEstadoRegistro(EstadoRegistro estadoRegistro);
}
