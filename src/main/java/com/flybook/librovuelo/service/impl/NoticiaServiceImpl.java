package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.model.Noticia;
import com.flybook.librovuelo.repository.NoticiaRepository;
import com.flybook.librovuelo.service.NoticiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;

import java.util.List;

@Service
public class NoticiaServiceImpl implements NoticiaService {

    @Autowired
    private NoticiaRepository noticiaRepository;

    @Override
    public void save(Noticia noticia) {noticiaRepository.save(noticia);}
    @Override
    public void deleteById(Long id) {noticiaRepository.deleteById(id);}

    @Override
    public Noticia getById(Long id) {return noticiaRepository.getById(id);}

    @Override
    public List<Noticia> findAll() {return noticiaRepository.findAll();}

    @Override
    public Page<Noticia> findAll(Pageable pageable) {return noticiaRepository.findAll(pageable);}

    @Override
    public Page<Noticia> findAllByAutor_Id(Pageable pageable, Long id) {
        return noticiaRepository.findAllByAutor_Id(pageable, id);
    }
}
