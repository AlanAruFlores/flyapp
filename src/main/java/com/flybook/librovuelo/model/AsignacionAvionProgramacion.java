package com.flybook.librovuelo.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
public class AsignacionAvionProgramacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Avion avion;

    @ManyToOne
    private Programacion programacion;

    private TipoEstado estado;

    private String comentario;
}
