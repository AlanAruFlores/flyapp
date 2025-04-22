package com.flybook.librovuelo.service;

import com.flybook.librovuelo.model.Noticia;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.List;

public interface NoticiaService {

    void save (Noticia noticia);
    void deleteById(Long id);
    Noticia getById(Long id);
    List<Noticia> findAll();
    Page<Noticia> findAll(Pageable pageable);
    Page<Noticia> findAllByAutor_Id(Pageable pageable, Long id);
}
