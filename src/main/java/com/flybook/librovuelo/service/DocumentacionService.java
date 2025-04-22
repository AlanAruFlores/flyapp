package com.flybook.librovuelo.service;

import com.flybook.librovuelo.model.Documentacion;
import com.flybook.librovuelo.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface DocumentacionService {

    void save (Documentacion documentacion);

    void guardarDocumentacionConArchivo(Documentacion documentacion, MultipartFile file, User user);

    void delete (Documentacion documentacion);

    Documentacion findByUsername(User username);

    Optional<Documentacion> findById (Long idDocumentacion);

    List<Documentacion> findAll();
    List<Documentacion> findAll(Sort sort);
    Documentacion findDocumentacionById(Long id);
    List<Documentacion> buscarDocumentacionDeUnUsuario(User user);

    List<Documentacion> findAllByUsername(Sort sort, User user);

    Documentacion getById(Long id);
    List<Documentacion> findDocumentacionsById(Long id);

    void updateDocumentacion(Documentacion documentacion, MultipartFile file);

    void updateDocumentacion(Documentacion documentacion);

    void sendEmailAllDocumentacionIfAlmostIsExpired();
}
