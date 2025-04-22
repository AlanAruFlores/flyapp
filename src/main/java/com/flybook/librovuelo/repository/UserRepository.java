package com.flybook.librovuelo.repository;

import com.flybook.librovuelo.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    void deleteById(Long id);
    User findByUsername(String username);
    List<User> findAllByRolesContaining (Role role);

    // Lista de tripulantes asociados a un lider
    List<User> findUsersByLider_Id(Long idLider);

    List<User> findUsersByLiderId(Long idLider);
    List<User> findUsersByLiderIdAndNombre(Long id, String nombre);
    User getUserByLiderIdAndNombre(Long id, String nombre);
    User getUserByIdAndLiderId(Long id, Long idLider);
    User getByIdAndLiderId(Long id, Long idLider);
    User findByLider_Id(Long id);
   // List<Ausencia> findAllByUserIdAndTipoAusenciaAndCicloVacaciones(TipoAusencia ausencia, CicloVacaciones cicloVacaciones, Long userId);

    List<User> findAllByLider(User user);

    User getUserByLiderId(Long id);

    User findUserByLiderId(Long idLider);

    User findUserById(Long id);

    User findByNombre(String nombre);

    List<User> findByLiderIdAndGeneracionNumero(Long liderId, Integer generacionNumero);

    List<User> findByLiderIdAndLegajo(Long liderId, Integer legajo);

    List<User> findByLiderIdAndApellido(Long liderId, String apellido);

    User findByLegajo(Integer legajo);

    User findByMail(String email);

    User findByResetPasswordToken(String token);

    User findUserByUsernameAndPassword(String username, String password);
}
