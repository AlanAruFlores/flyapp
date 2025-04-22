package com.flybook.librovuelo.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
//@EqualsAndHashCode
//@Component
public class Ausencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User user;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaDesde;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaHasta;
    @Enumerated(EnumType.STRING)
    private TipoAusencia tipoAusencia;

    @Enumerated(EnumType.STRING)
    private EstadoAusencia estadoAusencia;

}
