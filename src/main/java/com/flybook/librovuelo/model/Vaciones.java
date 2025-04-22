package com.flybook.librovuelo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.time.LocalDate;



@Entity
@Getter
@Setter
public class Vaciones {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;


    @ManyToOne
    private CicloVacaciones cicloVacaciones;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate pedidoComienzo;


    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate pedidoFinaliacion;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate asignacionComienzo;


    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate asignacionFinaliacion;

}
