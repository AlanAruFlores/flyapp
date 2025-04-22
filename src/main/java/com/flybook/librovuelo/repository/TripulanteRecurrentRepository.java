package com.flybook.librovuelo.repository;

import com.flybook.librovuelo.model.Recurrent;
import com.flybook.librovuelo.model.TripulanteRecurrent;
import com.flybook.librovuelo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TripulanteRecurrentRepository extends JpaRepository<TripulanteRecurrent, Long> {
    TripulanteRecurrent findByRecurrentId(Long idRecurrent);
    TripulanteRecurrent findByTripulanteId(Long id);
    TripulanteRecurrent findTripulanteRecurrentByRecurrentAndTripulante(Recurrent recurrent, User tripulante);
    List<TripulanteRecurrent> findTripulanteRecurrentsByAsignado(Boolean asignado);
    TripulanteRecurrent getByTripulante(User tripulante);
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

}
