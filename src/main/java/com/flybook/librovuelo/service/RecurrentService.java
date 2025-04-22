package com.flybook.librovuelo.service;

import com.flybook.librovuelo.model.Recurrent;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface RecurrentService {

    void save (Recurrent recurrent);

    void delete (Recurrent recurrent);

    Optional<Recurrent> findById (Long idRecurrent);

    List<Recurrent> findAll();
    List<Recurrent> findAll(Sort sort);
    Recurrent findRecurrentById(Long id);
    Recurrent getById(Long id);
    List<Recurrent> findRecurrentsById(Long id);

    //List<Ausencia> obtenerUsuariosQueNoSePuedenAsignarAUnRecurrent(DatosTripulanteRecurrent datosTripulanteRecurrent);
}
