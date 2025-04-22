package com.flybook.librovuelo.repository;

import com.flybook.librovuelo.model.Generacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GeneracionRepository  extends JpaRepository<Generacion, Long> {
   Generacion findByNumero(Integer numero);

   List<Generacion> findAllByOrderByNumeroAsc();

}
