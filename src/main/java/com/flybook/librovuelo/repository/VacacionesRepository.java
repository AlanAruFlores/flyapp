package com.flybook.librovuelo.repository;

import com.flybook.librovuelo.model.CicloVacaciones;
import com.flybook.librovuelo.model.User;
import com.flybook.librovuelo.model.VueloRealizado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VacacionesRepository extends JpaRepository<CicloVacaciones,Long> {

}