package com.flybook.librovuelo.service;


import com.flybook.librovuelo.dto.DatosFlyondiersEdit;
import com.flybook.librovuelo.exceptions.InvalidCredentialsUserException;
import com.flybook.librovuelo.exceptions.UserNotFoundException;
import com.flybook.librovuelo.model.DatosContrasenia;
import com.flybook.librovuelo.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    void encriptarContraseña(User user);

    void setearRolTripulantePorDefault(User user);

    void setearDatosDeVueloPorDefault(User user);

    void calcularHorasIniciales(User user);

    void setearDatosContactoPorDefault(User user);

    void setarLiderPorDefault(User user);

    void setearGeneracionPorDefault(User user);

    void autoCompletarDatosPorDefault(User user);

    void setearDireccionPorDefault(User user);

    void save(User user);

    void update(User user) throws Exception;

    void deleteById(Long id);
    User findByLider_Id(Long id);
   List<User> obtenerTodosLosLideres();
   List<User> findUsersByLider_Id(Long idLider);

   List<User> findUsersByLiderId(Long idLider);
    User findByUsername(String username);
    List<User> findUsersByLiderIdAndNombre(Long id, String nombre);

    List<User> findAll();
    User getUserByLiderIdAndNombre(Long id, String nombre);

    User getUserByIdAndLiderId(Long id, Long idLider);
    User getByIdAndLiderId(Long id, Long idLider);
    User getById(Long id);
    User getUserByLiderId(Long id);
    User newPassword(User userForm, DatosContrasenia datosContraseña) throws Exception;
    User findUserByLiderId(Long idLider);
    List<User> findAllByLider(User user);

    User findUserById(Long id);

    List<User> buscarTripulantesPorLiderYParams(User user, Map<String, Object> params);

    List<User> buscarPorLegajo(Long liderId, String valor);

    List<User> buscarPorGeneracion(Long liderId, String valor);

    List<User> buscarPorApellido(Long liderId, String valor);

    User findByLegajo(Integer legajo);

    User findByMail(String email);

    void updateResetPasswordToken(String token, String email) throws UserNotFoundException;


    User getByResetPasswordToken(String token);

    void updatePassword(User User, String newPassword) throws Exception;

    DatosFlyondiersEdit obtenerlybondiersEdit(Long id);

    void updateDatosFlybondiEdit(DatosFlyondiersEdit datosFlyondiersEdit) throws Exception;

    void verifyIfUserHasCorrectCredentials(User user, User userToAuthenticate) throws InvalidCredentialsUserException;
    /*    void saveImage(MultipartFile imageFile, ImageProfile photo) throws IOException;*/
}
