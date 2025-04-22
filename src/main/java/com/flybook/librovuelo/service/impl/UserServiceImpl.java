package com.flybook.librovuelo.service.impl;


import com.flybook.librovuelo.dto.DatosFlyondiersEdit;
import com.flybook.librovuelo.exceptions.InvalidCredentialsUserException;
import com.flybook.librovuelo.exceptions.UserNotFoundException;
import com.flybook.librovuelo.model.*;
import com.flybook.librovuelo.repository.GeneracionRepository;
import com.flybook.librovuelo.repository.LocalidadRepository;
import com.flybook.librovuelo.repository.UserRepository;
import com.flybook.librovuelo.service.RoleService;
import com.flybook.librovuelo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final RoleService roleService;

    private final Path root = Paths.get("./uploads/img/profile");

    @Autowired
    private LocalidadRepository localidadRepository;

    @Autowired
    private GeneracionRepository generacionRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleService = roleService;
    }

    @Override
    public void encriptarContraseña(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    }

    @Override
    public void setearRolTripulantePorDefault(User user) {
        Set<Role> roles = new HashSet<>();
        roles.add(this.roleService.obtenerRolPorNombre("ROLE_TRIPULANTE"));
        user.setRoles(new HashSet<>(roles));
    }

    @Override
    public void setearDatosDeVueloPorDefault(User user) {
        user.setTvDiurnoInicial((double) 0);
        user.setTvNocturnoInicial((double) 0);
        user.setTvTotalInicial((double) 0);
        user.setCantidadAterrizajeInical(0);
    }

    @Override
    public void calcularHorasIniciales(User user) {
        user.setTvTotalInicial(user.getTvDiurnoInicial() + user.getTvNocturnoInicial());
    }

    @Override
    public void setearDatosContactoPorDefault(User user) {
        DatosContacto datosContacto = new DatosContacto();

        datosContacto.setNombre("");
        datosContacto.setApellido("");
        datosContacto.setParentezco("");
        datosContacto.setTelefono(0);
        user.setDatosContacto(datosContacto);
    }

    @Override
    public void setarLiderPorDefault(User user) {
        User lider = this.userRepository.findByUsername("admin01");
        user.setLider(lider);
    }

    @Override
    public void setearGeneracionPorDefault(User user) {
        Generacion generacion = this.generacionRepository.findByNumero(0);
        user.setGeneracion(generacion);
    }

    @Override
    public void autoCompletarDatosPorDefault(User user) {
        setearRolTripulantePorDefault(user);
        setearDatosDeVueloPorDefault(user);
        setearDireccionPorDefault(user);
        setearDatosContactoPorDefault(user);
        setarLiderPorDefault(user);
        setearGeneracionPorDefault(user);
    }

    @Override
    public void setearDireccionPorDefault(User user) {
        Direccion direccion = new Direccion();

        direccion.setLocalidad(this.localidadRepository.findByNombre("C.A.B.A."));
        direccion.setCalle("Av. del Libertador");
        direccion.setNumeroDeCalle(6343);

        user.setDireccion(direccion);
    }

    @Override
    public void save(User user) {
        encriptarContraseña(user);
        autoCompletarDatosPorDefault(user);

        userRepository.save(user);
    }

    @Override
    public void update(User user) throws Exception {

        this.validarDatosUpdate(user);

        this.userRepository.save(user);
    }

    private void validarDatosUpdate(User user) throws Exception {

        if (user.getMail() == null || !user.getMail().endsWith("@flybondi.com")  ) {
            throw new Exception( "mail Invalido ");
        }


        if ( user  != null &&   user.getLegajo() == null    ) {
            throw new Exception( "El legajo no puede ser vacio ");
        }
        User userConLegajo = findByLegajo(user.getLegajo()) ;
        if ( user  != null && userConLegajo != null && userConLegajo.getId() != user.getId()   ) {
            throw new Exception( "legajo Duplicado");
        }
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByLider_Id(Long id) {
        return userRepository.findByLider_Id(id);
    }


    @Override
    public List<User> obtenerTodosLosLideres() {

        return userRepository.findAllByRolesContaining(this.roleService.obtenerRolPorNombre("ROLE_LIDER"));

    }

    @Override
    public List<User> findUsersByLider_Id(Long idLider) {
        return userRepository.findUsersByLider_Id(idLider);
    }

    @Override
    public List<User> findUsersByLiderId(Long idLider) {
        return userRepository.findUsersByLiderId(idLider);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findUsersByLiderIdAndNombre(Long id, String nombre) {
        return userRepository.findUsersByLiderIdAndNombre(id, nombre);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User getUserByLiderIdAndNombre(Long id, String nombre) {
        return userRepository.getUserByLiderIdAndNombre(id, nombre);
    }

    @Override
    public User getUserByIdAndLiderId(Long id, Long idLider) {
        return userRepository.getUserByIdAndLiderId(id, idLider);
    }

    @Override
    public User getByIdAndLiderId(Long id, Long idLider) {
        return userRepository.getByIdAndLiderId(id, idLider);
    }

    @Override
    public User getById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public User getUserByLiderId(Long id) {
        return userRepository.getUserByLiderId(id);
    }

    @Override
    public User newPassword(User user, DatosContrasenia datosContrasenia) throws Exception {
        if (bCryptPasswordEncoder.matches(datosContrasenia.getCurrentPassword(), user.getPassword())) {
            if (datosContrasenia.getPassword().length() >= 8 && datosContrasenia.getPassword().length() <= 32) {
                if (datosContrasenia.getPassword().equals(datosContrasenia.getPasswordConfirm())) {

                    user.setPassword(bCryptPasswordEncoder.encode(datosContrasenia.getPassword()));
                    user.setPasswordConfirm(bCryptPasswordEncoder.encode(datosContrasenia.getPasswordConfirm()));
                    return user;

                } else {
                    throw new Exception("Las contraseñas no coinciden");
                }
            } else {
                throw new Exception("La contraseña es inválida, probá con una de al menos 8 caracteres");
            }
        }
        throw new Exception("La contraseña actual no coincide con la ingresada");
    }

    @Override
    public User findUserByLiderId(Long idLider) {
        return userRepository.findUserByLiderId(idLider);
    }

    @Override
    public List<User> findAllByLider(User user) {
        return this.userRepository.findAllByLider(user);
    }

    @Override
    public User findUserById(Long id) {
        return this.userRepository.findUserById(id);
    }

    @Override
    public List<User> buscarTripulantesPorLiderYParams(User user, Map<String, Object> params) {

        Long liderId = user.getId();
        String atributo = (String) params.getOrDefault("atributo", "");
        List<User> tripulantes;

        if (atributo.isEmpty() || params.get("valor").toString().isEmpty()) {
            tripulantes = this.userRepository.findUsersByLiderId(liderId);
        } else {
            String valor = (String) params.get("valor");

            tripulantes = switch (atributo) {
                case "legajo" -> buscarPorLegajo(liderId, valor);
                case "generacion" -> buscarPorGeneracion(liderId, valor);
                case "apellido" -> buscarPorApellido(liderId, valor);
                default -> findUsersByLiderId(liderId);
            };
        }

        return tripulantes;
    }

    @Override
    public List<User> buscarPorLegajo(Long liderId, String valor) {
        try {
            Integer legajo = Integer.parseInt(valor);
            return this.userRepository.findByLiderIdAndLegajo(liderId, legajo);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("El legajo debe ser un número", e);
        }

    }

    @Override
    public List<User> buscarPorGeneracion(Long liderId, String valor) {
        try {
            Integer generacionNumero = Integer.parseInt(valor);
            return this.userRepository.findByLiderIdAndGeneracionNumero(liderId, generacionNumero);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("El número de generacion debe ser un número", e);
        }

    }

    @Override
    public List<User> buscarPorApellido(Long liderId, String valor) {
        if (valor.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Ingresá un apellido válido (no numérico)");
        }
        return this.userRepository.findByLiderIdAndApellido(liderId, valor);
    }

    @Override
    public User findByLegajo(Integer legajo) {
        return this.userRepository.findByLegajo(legajo);
    }

    @Override
    public User findByMail(String email) {
        return this.userRepository.findByMail(email);
    }

    @Override
    public void updateResetPasswordToken(String token, String email) throws UserNotFoundException {
        User user = this.userRepository.findByMail(email);
        if (user != null) {
            user.setResetPasswordToken(token);
            this.userRepository.save(user);
        } else {
            throw new UserNotFoundException("No pudimos encontrar ningun usuario con el mail: " + email);
        }
    }

    @Override
    public User getByResetPasswordToken(String token) {
        return userRepository.findByResetPasswordToken(token);
    }

    @Override
    public void updatePassword(User user, String newPassword) throws Exception {

        if (newPassword.length() >= 8 && newPassword.length() <= 32) {
            String encodedPassword = bCryptPasswordEncoder.encode(newPassword);
            user.setPassword(encodedPassword);

            user.setResetPasswordToken(null);
            this.userRepository.save(user);
        }  else {
            throw new Exception("La contraseña es inválida, probá con una de al menos 8 caracteres");
        }
    }

    @Override
    public DatosFlyondiersEdit obtenerlybondiersEdit(Long id) {
        DatosFlyondiersEdit datosFlyondiersEdit =new DatosFlyondiersEdit();
        User empleado = this.getById(id);
        datosFlyondiersEdit.setId(empleado.getId());
        datosFlyondiersEdit.setGeneracion(empleado.getGeneracion());
        datosFlyondiersEdit.setTipoCargo(empleado.getTipoCargo());
        datosFlyondiersEdit.setLider(empleado.getLider());
        datosFlyondiersEdit.setRoles(empleado.getRoles());


        return datosFlyondiersEdit;
    }

    @Override
    public void updateDatosFlybondiEdit(DatosFlyondiersEdit datosFlyondiersEdit) throws Exception {
        User empleado = this.getById(datosFlyondiersEdit.getId());

        empleado.setLider(datosFlyondiersEdit.getLider());
        empleado.setTipoCargo(datosFlyondiersEdit.getTipoCargo());
        empleado.setRoles(datosFlyondiersEdit.getRoles());
        empleado.setGeneracion(datosFlyondiersEdit.getGeneracion());
        this.update(empleado);
    }

    @Override
    public void verifyIfUserHasCorrectCredentials(User user, User userToAuthenticate) throws InvalidCredentialsUserException{
        if(!user.getUsername().equalsIgnoreCase(userToAuthenticate.getUsername()) || !this.bCryptPasswordEncoder.matches(userToAuthenticate.getPassword(), user.getPassword()))
            throw new InvalidCredentialsUserException();
    }
}



