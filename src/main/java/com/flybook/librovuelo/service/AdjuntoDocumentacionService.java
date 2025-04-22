package com.flybook.librovuelo.service;

import com.flybook.librovuelo.model.AdjuntoDocumentacion;
import com.flybook.librovuelo.model.CicloVacaciones;
import com.flybook.librovuelo.model.Documentacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AdjuntoDocumentacionService {

    AdjuntoDocumentacion getByNombre(String nombre);
    Page<AdjuntoDocumentacion> findAll(Pageable pageable);
    AdjuntoDocumentacion getByIdAndNombre(Long id, String nombre);
    AdjuntoDocumentacion save(AdjuntoDocumentacion adjuntar);
    List<AdjuntoDocumentacion> findAllByDocumentacion(Documentacion documentacion);
    AdjuntoDocumentacion getById(Long id);
    void delete(AdjuntoDocumentacion adjuntoDocumentacion, Documentacion documentacion);

    Optional<AdjuntoDocumentacion> findById(Long id);
    AdjuntoDocumentacion findByDocumentacion(Documentacion documentacion);
    AdjuntoDocumentacion findAdjuntoDocumentacionByDocumentacionId(Long id);
    List<AdjuntoDocumentacion> findAll();

    void deleteAdjuntosByDocumentacion(Documentacion documentacion);
}
