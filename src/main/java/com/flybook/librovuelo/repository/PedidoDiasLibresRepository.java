package com.flybook.librovuelo.repository;

import com.flybook.librovuelo.model.PedidoDiasLibres;
import com.flybook.librovuelo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface PedidoDiasLibresRepository extends JpaRepository<PedidoDiasLibres, Long> {
    PedidoDiasLibres getById(Long id);
    List<PedidoDiasLibres> getAllByUser_IdAndComienzo3DiasLibresGreaterThanEqualAndComienzo3DiasLibresLessThanEqual(Long id, LocalDate desde,LocalDate hasta);
    List<PedidoDiasLibres> getAllByUser_IdAndDiaLibreGreaterThanEqualAndDiaLibreLessThanEqual(Long id, LocalDate desde,LocalDate hasta);

    List<PedidoDiasLibres> findAllByUserOrderByFechaSolicitud(User user);

    List<PedidoDiasLibres> findAllByOrderByFechaSolicitudDesc();

    List<PedidoDiasLibres> findAllByUser_LiderOrderByFechaSolicitudDesc(User lider);
    //List<PedidoDiasLibres> findAllByUser_Lider_IdOrderByFechaSolicitudDesc(Long id);
    List<PedidoDiasLibres> findAllByUser_Lider_IdAndFechaSolicitudGreaterThanEqualAndFechaSolicitudLessThanEqualOrderByFechaSolicitudDesc(Long id, LocalDate desde, LocalDate hasta);
    List<PedidoDiasLibres> findAllByUser_Lider_IdAndUser_LegajoAndFechaSolicitudGreaterThanEqualAndFechaSolicitudLessThanEqualOrderByFechaSolicitudDesc(Long id,Integer legajo, LocalDate desde, LocalDate hasta);
    List<PedidoDiasLibres> findAllByUser_Lider_IdAndUser_DniAndFechaSolicitudGreaterThanEqualAndFechaSolicitudLessThanEqualOrderByFechaSolicitudDesc(Long id,Long dni, LocalDate desde, LocalDate hasta);
    List<PedidoDiasLibres> findAllByUser_Lider_IdAndUser_NombreAndFechaSolicitudGreaterThanEqualAndFechaSolicitudLessThanEqualOrderByFechaSolicitudDesc(Long id,String nombre, LocalDate desde, LocalDate hasta);
    List<PedidoDiasLibres> findAllByUser_Lider_IdAndUser_ApellidoAndFechaSolicitudGreaterThanEqualAndFechaSolicitudLessThanEqualOrderByFechaSolicitudDesc(Long id,String apellido, LocalDate desde, LocalDate hasta);

    List<PedidoDiasLibres> findAllByFechaSolicitudGreaterThanEqualAndFechaSolicitudLessThanEqualOrderByFechaSolicitudDesc(LocalDate desde, LocalDate hasta);
    List<PedidoDiasLibres> findAllByUser_LegajoAndFechaSolicitudGreaterThanEqualAndFechaSolicitudLessThanEqualOrderByFechaSolicitudDesc(Integer legajo, LocalDate desde, LocalDate hasta);
    List<PedidoDiasLibres> findAllByUser_DniAndFechaSolicitudGreaterThanEqualAndFechaSolicitudLessThanEqualOrderByFechaSolicitudDesc(Long dni, LocalDate desde, LocalDate hasta);
    List<PedidoDiasLibres> findAllByUser_NombreAndFechaSolicitudGreaterThanEqualAndFechaSolicitudLessThanEqualOrderByFechaSolicitudDesc(String nombre, LocalDate desde, LocalDate hasta);
    List<PedidoDiasLibres> findAllByUser_ApellidoAndFechaSolicitudGreaterThanEqualAndFechaSolicitudLessThanEqualOrderByFechaSolicitudDesc(String apellido, LocalDate desde, LocalDate hasta);


}
