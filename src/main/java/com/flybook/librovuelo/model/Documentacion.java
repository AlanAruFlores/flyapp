package com.flybook.librovuelo.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Documentacion {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaDeCreacion;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaDeVencimiento;

    private String comentario;

    private Boolean notificacionActivada=true;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private TipoDocumentacion tipoDocumentacion;

    public Documentacion(){}
}

