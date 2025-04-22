package com.flybook.librovuelo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@Column(nullable = false)
    private String nombre;
    //@Column(nullable = false)
    private String apellido;
    //@Column(nullable = false)
    private String mail;
    //@Column(nullable = false)
    private Integer legajo;

    private Long dni;

    private TipoCargo tipoCargo;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Documentacion> documentacion;

    @OneToOne(cascade = CascadeType.ALL)
    private DatosContacto datosContacto;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaIngreso;

    @ManyToOne
    @JsonIgnore
    private User lider;

    @ManyToOne
    private Generacion generacion;

    private String username;

    private String password;

    private Integer telefono;

    @Transient
    private String passwordConfirm;

    @ManyToOne(cascade = CascadeType.ALL)
    private Direccion direccion;


    private Double tvNocturnoInicial;
    private Double tvDiurnoInicial;
    private Double tvTotalInicial;
    private Integer cantidadAterrizajeInical;



    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Role> roles;

  //  @OneToOne(cascade={CascadeType.PERSIST})
   // private Vestimenta vestimenta;
    private String sexo;

    @OneToOne
    /*@JoinColumn(name = "image_profile_id")*/
    private ImageProfile imageProfile;

    private boolean enabled;

    private String resetPasswordToken;

    public User(){
        this.enabled=false;
    }

    public String obtenerNombreApellidoLegajo(){
        return  nombre  + " " + apellido + " legajo: " +legajo ;
    }

}
