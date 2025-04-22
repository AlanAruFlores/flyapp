package com.flybook.librovuelo.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Vuelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Aeropuerto aeropuertoOrigen;

    @OneToOne
    private Aeropuerto aeropuertoDestino;

    private Double tv;

    
}
