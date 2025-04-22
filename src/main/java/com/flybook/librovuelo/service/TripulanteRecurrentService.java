package com.flybook.librovuelo.service;

import com.flybook.librovuelo.exceptions.UsuariosNoAsigandosRecurrentException;
import com.flybook.librovuelo.model.Recurrent;
import com.flybook.librovuelo.model.TripulanteRecurrent;
import com.flybook.librovuelo.model.User;

import java.util.List;
import java.util.Optional;

public interface TripulanteRecurrentService {
    void borrarTripulanteDeUnRecurrent(Long idTripulante, Long idRecurrent) throws Exception;

    void save(List<Long> idsTripulantes, Long idRecurrent) throws UsuariosNoAsigandosRecurrentException;

    void delete (TripulanteRecurrent tripulanteRecurrent);
    TripulanteRecurrent getByTripulante(User tripulante);
    List<TripulanteRecurrent> findAll();
    TripulanteRecurrent findByRecurrent(Recurrent recurrent);
    List<TripulanteRecurrent> findTripulanteRecurrentsByTripulante(User tripulante);
    List<TripulanteRecurrent> findTripulanteRecurrentsByRecurrent(Recurrent recurrent);
    TripulanteRecurrent findByRecurrentAndTripulante(Recurrent recurrent, User tripulante);
    TripulanteRecurrent findByTripulanteIdAndAndRecurrentId(Long idTripulante, Long idRecurrent);

    List<TripulanteRecurrent> findByTripulante(User tripulante);
    List<TripulanteRecurrent> findAllByTripulante(User tripulante);
    List<TripulanteRecurrent> findAllByRecurrent(Recurrent recurrent);
    Optional<TripulanteRecurrent> findById(Long id);
    TripulanteRecurrent getById(Long id);
    List<TripulanteRecurrent> getByTripulanteIdAndRecurrentId(Long idTripulante, Long idRecurrent);
    TripulanteRecurrent findByTripulanteId(Long id);

    TripulanteRecurrent findByRecurrentId(Long idRecurrent);
}
