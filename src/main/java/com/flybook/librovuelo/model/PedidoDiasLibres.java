package com.flybook.librovuelo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class PedidoDiasLibres {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne
    private User user;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate comienzo3DiasLibres;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaSolicitud;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate diaLibre;

    private String pedidoDeVuelo;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate periodoDelPedido;
}
