package com.flybook.librovuelo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Recurrent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numero;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaDesde;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaHasta;

    private String comentario;
}
