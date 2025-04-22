package com.flybook.librovuelo.repository;

import com.flybook.librovuelo.model.AdjuntoDocumentacion;
import com.flybook.librovuelo.model.Documentacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.print.Doc;
import java.util.List;

public interface AdjuntoDocumentacionRepository extends JpaRepository<AdjuntoDocumentacion, Long> {
    
    Page<AdjuntoDocumentacion> findAll(Pageable pageable);

    List<AdjuntoDocumentacion> findAllByDocumentacion(Documentacion documentacion);

    void delete(AdjuntoDocumentacion adjuntoDocumentacion);

    AdjuntoDocumentacion findByDocumentacion(Documentacion documentacion);

    AdjuntoDocumentacion findAdjuntoDocumentacionByDocumentacionId(Long id);
    AdjuntoDocumentacion getById(Long id);
    AdjuntoDocumentacion getByIdAndNombre(Long id, String nombre);
    AdjuntoDocumentacion getByNombre(String nombre);

    AdjuntoDocumentacion getAdjuntoDocumentacionByDocumentacionId(Documentacion documentacion);

    void deleteAllByDocumentacion(Documentacion documentacion);
}
