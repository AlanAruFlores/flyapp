package com.flybook.librovuelo.repository;

import com.flybook.librovuelo.model.User;
import com.flybook.librovuelo.model.Vestimenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VestimentaRepository extends JpaRepository<Vestimenta,Long> {

    Vestimenta getByUser_Id(Long id);

    Vestimenta findVestimentaByUserId(Long id);
}
