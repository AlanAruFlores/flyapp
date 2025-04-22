package com.flybook.librovuelo.service;

import com.flybook.librovuelo.model.Foliado;
import com.flybook.librovuelo.model.User;
import com.flybook.librovuelo.model.VueloRealizado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FoliadoService {

    Optional<Foliado> findById(Long id);
    void delete(Foliado foliado);
    Foliado buscarFoliadoPorFecha(LocalDate fechaFoliado);

    List<Foliado> obtenerTodosLosFoliados(User usero);

    Double obtenerTotalDeHorasFoliada(User user);
    Double obtenerTotalDeHorasFoliadaNocturna(User user);
    Double obtenerTotalDeHorasFoliadaDiurnas(User user);
    Integer obtenerTotalDeAterrizajeFoliada(User user);
    Page<VueloRealizado> buscarHorasNofoliadas(User user, PageRequest pageRequest);
     Page<VueloRealizado> buscarHorasfoliadas(User user, Foliado foliado, Pageable pageable);
    Foliado buscarFoliadoPorId (Long id);

    Foliado generarNuevoFoliado(User user,PageRequest pageRequest) throws Exception;
}
