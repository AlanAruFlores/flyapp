package com.flybook.librovuelo.repository;

import com.flybook.librovuelo.model.UserCredentials;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserCredentialsRepository extends JpaRepository<UserCredentials, Long> {
    UserCredentials findByUser_Id(Long id);
    void deleteByUser_Id(Long id);
}
