package com.flybook.librovuelo.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Getter
@Setter

public class CicloVacaciones {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private Integer numeroDeCiclo;

    @ManyToOne
    private Generacion generacion;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate comienzoCiclo;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate finalizacionCiclo;
}
