package com.flybook.librovuelo.repository;

import com.flybook.librovuelo.model.Documentacion;
import com.flybook.librovuelo.model.Recurrent;
import com.flybook.librovuelo.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RecurrentRepository extends JpaRepository<Recurrent, Long> {

    Optional<Recurrent> findById (Long idRecurrent);
    Recurrent findRecurrentById(Long id);
    List<Recurrent> findRecurrentsById(Long id);

}
