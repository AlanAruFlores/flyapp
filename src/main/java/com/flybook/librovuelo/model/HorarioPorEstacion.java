package com.flybook.librovuelo.model;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.time.LocalTime;


@Entity
@Getter
@Setter
public class HorarioPorEstacion {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Estacion estacion;

    //@Temporal(TemporalType.TIME)
    private LocalTime comienzoHorarioDiurno;


    //@Temporal(TemporalType.TIME)
    private LocalTime comienzoHorarioNocturno;



}
