package com.flybook.librovuelo.repository;

import com.flybook.librovuelo.model.Ausencia;
import com.flybook.librovuelo.model.TipoAusencia;
import com.flybook.librovuelo.model.TipoCargo;
import com.flybook.librovuelo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AusenciaRepository extends JpaRepository<Ausencia, Long> {

    // List <Ausencia> findAllByFechaDesdeIsGreaterThanEqualAndFechaHastaIsLessThanEqualAndTipoAusenciaEqualsOrderByFechaDesde(LocalDate fechaDesde, LocalDate fechaHasta, TipoAusencia tipoAusencia);

    List<Ausencia> findAllByFechaDesdeIsGreaterThanEqualAndFechaDesdeIsLessThanEqualAndTipoAusenciaInOrderByFechaDesde(LocalDate fechaDesde, LocalDate fechaHasta, List<TipoAusencia> tipoAusencias);

    List<Ausencia> findAllByFechaDesdeIsGreaterThanEqualAndFechaDesdeIsLessThanEqualAndTipoAusenciaInAndUser_TipoCargoOrderByFechaDesde(LocalDate fechaDesde, LocalDate fechaHasta, List<TipoAusencia> tipoAusencias, TipoCargo tipoCargo);

    List<Ausencia> findAllByUserAndFechaDesdeIsGreaterThanEqualAndFechaDesdeIsLessThanEqualAndTipoAusenciaInOrderByFechaDesde(User user, LocalDate fechaDesde, LocalDate fechaHasta, List<TipoAusencia> tipoAusencias);

    List<Ausencia> findAllByUserAndFechaDesdeIsGreaterThanEqualAndFechaHastaIsLessThanEqualAndTipoAusenciaOrderByFechaDesde(User user, LocalDate fechaDesde, LocalDate fechaHasta, TipoAusencia tipoAusencia);

    //List<Ausencia> findAllByUserAndFechaDesdeIsBetweenAndFechaHastaIsBetweenOrderByFechaDesdeDesc(User user, LocalDate inicioDesde,  LocalDate inicioHasta,  LocalDate hastaInicio, LocalDate hastaHasta);

    List<Ausencia> findAllByUserAndFechaDesdeIsLessThanAndFechaDesdeIsLessThanEqualAndFechaHastaIsGreaterThanEqualOrderByFechaDesdeAsc(User user, LocalDate desde, LocalDate hasta, LocalDate hasta2);

    List<Ausencia> findAllByUserAndFechaDesdeIsGreaterThanEqualAndFechaDesdeIsLessThanEqualAndFechaHastaIsGreaterThanEqualOrderByFechaDesdeAsc(User user, LocalDate desde, LocalDate hasta, LocalDate hasta2);

    List<Ausencia> findAllByUserAndFechaDesdeIsGreaterThanEqualAndFechaDesdeIsLessThanEqualAndFechaHastaIsLessThanOrderByFechaDesdeAsc(User user, LocalDate desde, LocalDate hasta, LocalDate hasta2);

    //  List<Ausencia> findAllByUserAndFechaHastaIsBetweenOrderByFechaDesdeDesc(User user, LocalDate inicioDesde,  LocalDate inicioHasta);
    Ausencia findByUserAndFechaDesdeAndFechaHastaAndTipoAusencia(User user, LocalDate inicioDesde, LocalDate inicioHasta, TipoAusencia tipoAusencia);

    List<Ausencia> findAllByUser(User user);

    List<Ausencia> findAllByUserAndTipoAusencia(User user, TipoAusencia tipoAusencia);

    List<Ausencia> findByUserLider(User lider);

    Ausencia findAusenciaById(Long id);

    List<Ausencia> findAllByFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(LocalDate desde, LocalDate hasta);

    List<Ausencia> findAllByTipoAusenciaAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(
            TipoAusencia tipoAusencia, LocalDate desde, LocalDate hasta);

    List<Ausencia> findAllByUser_LegajoAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(
            Integer legajo, LocalDate desde, LocalDate hasta);

    List<Ausencia> findAllByUser_DniAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(
            Long dni, LocalDate desde, LocalDate hasta);

    List<Ausencia> findAllByUser_NombreAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(
            String nombre, LocalDate desde, LocalDate hasta);

    List<Ausencia> findAllByUser_Generacion_NumeroAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(
            Integer numeroGeneracion, LocalDate desde, LocalDate hasta);

    List<Ausencia> findAllByUser_LegajoAndTipoAusenciaAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(
            Integer legajo, TipoAusencia tipoAusencia, LocalDate desde, LocalDate hasta);

    List<Ausencia> findAllByUser_DniAndTipoAusenciaAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(
            Long dni, TipoAusencia tipoAusencia, LocalDate desde, LocalDate hasta);

    List<Ausencia> findAllByUser_NombreAndTipoAusenciaAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(
            String nombre, TipoAusencia tipoAusencia, LocalDate desde, LocalDate hasta);

    List<Ausencia> findAllByUser_Generacion_NumeroAndTipoAusenciaAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(
            Integer numeroGeneracion, TipoAusencia tipoAusencia, LocalDate desde, LocalDate hasta);
    List<Ausencia> findAllByUser_Lider_IdAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(
            Long id, LocalDate desde, LocalDate hasta);

    List<Ausencia> findAllByUser_Lider_IdAndUser_LegajoAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(
            Long id, Integer legajo, LocalDate desde, LocalDate hasta);

    List<Ausencia> findAllByUser_Lider_IdAndUser_DniAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(
            Long id, Long dni, LocalDate desde, LocalDate hasta);

    List<Ausencia> findAllByUser_Lider_IdAndUser_NombreAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(
            Long id, String nombre, LocalDate desde, LocalDate hasta);

    List<Ausencia> findAllByUser_Lider_IdAndUser_Generacion_NumeroAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(
            Long id, Integer numeroGeneracion, LocalDate desde, LocalDate hasta);

    List<Ausencia> findAllByUser_Lider_IdAndTipoAusenciaAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(
            Long id, TipoAusencia tipoAusencia, LocalDate desde, LocalDate hasta);

    List<Ausencia> findAllByUser_Lider_IdAndUser_LegajoAndTipoAusenciaAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(
            Long id, Integer legajo, TipoAusencia tipoAusencia, LocalDate desde, LocalDate hasta);

    List<Ausencia> findAllByUser_Lider_IdAndUser_DniAndTipoAusenciaAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(
            Long id, Long dni, TipoAusencia tipoAusencia, LocalDate desde, LocalDate hasta);

    List<Ausencia> findAllByUser_Lider_IdAndUser_NombreAndTipoAusenciaAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(
            Long id, String nombre, TipoAusencia tipoAusencia, LocalDate desde, LocalDate hasta);

    List<Ausencia> findAllByUser_Lider_IdAndUser_Generacion_NumeroAndTipoAusenciaAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(
            Long id, Integer numeroGeneracion, TipoAusencia tipoAusencia, LocalDate desde, LocalDate hasta);

}