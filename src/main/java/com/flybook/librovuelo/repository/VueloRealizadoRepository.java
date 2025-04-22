package com.flybook.librovuelo.repository;


import com.flybook.librovuelo.model.Foliado;
import com.flybook.librovuelo.model.User;
import com.flybook.librovuelo.model.VueloRealizado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VueloRealizadoRepository extends JpaRepository<VueloRealizado, Long> {


    //--@Query("select a from VueloRealizado  where a.fechahoraDespegue >= :desde and a.fechahoraDespegue <= :hasta")
    List <VueloRealizado> findAllByFechahoraDespegueBetween(LocalDateTime desde, LocalDateTime hasta);

    List <VueloRealizado> findAllByFechahoraAterrizajeBetween(LocalDateTime fechahoraDespegue, LocalDateTime fechahoraAterrizaje);
    List <VueloRealizado> findAllByUser (User user);
    List <VueloRealizado> findAllByFoliado (Foliado foliado);
    List <VueloRealizado> findAllByUserAndFoliado (User user, Foliado foliado);


    Page <VueloRealizado> findAllByUser (User user, Pageable pageable);

    Page <VueloRealizado> findAllByUserAndFoliado (User user, Foliado foliado, Pageable pageable);
    List <VueloRealizado> findAllByUserAndFoliadoIsNotNullOrderByFechahoraDespegueAsc(User user);
    Page <VueloRealizado> findAllByUserAndFoliadoIsNull(User user, Pageable pageable);
    Optional<VueloRealizado> findById(Long id);
    List <VueloRealizado> findAllByUserAndFechahoraDespegueBetween(User user,LocalDateTime desde, LocalDateTime hasta);

    List <VueloRealizado> findAllByUserAndFechahoraAterrizajeBetween(User user,LocalDateTime fechahoraDespegue, LocalDateTime fechahoraAterrizaje);



}
