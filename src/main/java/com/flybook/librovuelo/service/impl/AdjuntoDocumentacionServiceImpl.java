package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.model.AdjuntoDocumentacion;
import com.flybook.librovuelo.model.Documentacion;
import com.flybook.librovuelo.repository.AdjuntoDocumentacionRepository;
import com.flybook.librovuelo.service.AdjuntoDocumentacionService;
import com.flybook.librovuelo.service.FileStorageService;
import org.hibernate.annotations.ColumnTransformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AdjuntoDocumentacionServiceImpl implements AdjuntoDocumentacionService {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private AdjuntoDocumentacionRepository adjuntoDocumentacionRepository;

    @Override
    public AdjuntoDocumentacion getByNombre(String nombre) {
        return this.adjuntoDocumentacionRepository.getByNombre(nombre);
    }

    @Override
    public Page<AdjuntoDocumentacion> findAll(Pageable pageable) {
        return this.adjuntoDocumentacionRepository.findAll(pageable);
    }

    @Override
    public AdjuntoDocumentacion getByIdAndNombre(Long id, String nombre) {
        return this.adjuntoDocumentacionRepository.getByIdAndNombre(id, nombre);
    }

    @Override
    public AdjuntoDocumentacion save(AdjuntoDocumentacion adjuntar) {
        return this.adjuntoDocumentacionRepository.save(adjuntar);
    }

    @Override
    public List<AdjuntoDocumentacion> findAllByDocumentacion(Documentacion documentacion) {
        return this.adjuntoDocumentacionRepository.findAllByDocumentacion(documentacion);
    }

    @Override
    public AdjuntoDocumentacion getById(Long id) {
        return this.adjuntoDocumentacionRepository.getById(id);
    }

    @Transactional
    @Override
    public void delete(AdjuntoDocumentacion adjuntoDocumentacion, Documentacion documentacion) {
        List<AdjuntoDocumentacion> adjuntos  = this.findAllByDocumentacion(documentacion);
        this.fileStorageService.deleteFile(adjuntoDocumentacion.getPath());

        if(adjuntos.size() <=1)
            this.fileStorageService.deleteFolder(adjuntoDocumentacion.getPath(), documentacion);

        this.adjuntoDocumentacionRepository.delete(adjuntoDocumentacion);
    }

    @Override
    public Optional<AdjuntoDocumentacion> findById(Long id) {
        return this.adjuntoDocumentacionRepository.findById(id);
    }

    @Override
    public AdjuntoDocumentacion findByDocumentacion(Documentacion documentacion) {
        return this.adjuntoDocumentacionRepository.findByDocumentacion(documentacion);
    }

    @Override
    public AdjuntoDocumentacion findAdjuntoDocumentacionByDocumentacionId(Long id) {
        return this.adjuntoDocumentacionRepository.findAdjuntoDocumentacionByDocumentacionId(id);
    }

    @Override
    public List<AdjuntoDocumentacion> findAll() {
        return this.adjuntoDocumentacionRepository.findAll();
    }

    @Override
    public void deleteAdjuntosByDocumentacion(Documentacion documentacion){
        this.adjuntoDocumentacionRepository.deleteAllByDocumentacion(documentacion);
    }
}
