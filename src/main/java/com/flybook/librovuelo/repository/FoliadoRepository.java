package com.flybook.librovuelo.repository;


import com.flybook.librovuelo.model.Foliado;
import com.flybook.librovuelo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FoliadoRepository  extends JpaRepository<Foliado, Long> {

    public Foliado findByFechaFoliado(LocalDate fechaFoliado);

    public List<Foliado> findAllByUserOrderByFechaFoliadoDesc(User user);


}
