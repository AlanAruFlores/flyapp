package com.flybook.librovuelo.repository;

import com.flybook.librovuelo.model.Noticia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NoticiaRepository extends JpaRepository<Noticia, Long> {

    List<Noticia> findAllByOrderByFechaAsc();
    Page<Noticia> findAllByAutor_Id(Pageable pageable, Long id);
}
