package com.flybook.librovuelo.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.common.aliasing.qual.Unique;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Setter
@Getter
@Entity
public class HabilitarVacaciones {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate habilitarPedidoDeVacacionesDesde;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate habilitarPedidoDeVacacionesHasta;
    private Boolean habilitar;
    @Unique
    private EstadoRegistro estadoRegistro;

    public HabilitarVacaciones() {
        this.habilitar = false;
        this.habilitarPedidoDeVacacionesDesde = LocalDate.now();
        this.habilitarPedidoDeVacacionesHasta = LocalDate.now();
        this.estadoRegistro = EstadoRegistro.ACTIVO;

    }

}
