package com.flybook.librovuelo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ProgramacionTripulante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User tripulante;

    @Enumerated(EnumType.STRING)
    private TipoActividad tipoActividad;

    //@ManyToOne
    //private Vuelo vuelo;

    @ManyToOne
    private Aeropuerto aeropuertoOrigen;

    @ManyToOne
    private Aeropuerto aeropuertoDestino;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(nullable = true)
    private EventoCalendario eventoCalendario;

    private LocalDateTime fechaHoraPresentacion;
    private LocalDateTime fechaHoraDespegue;
    private LocalDateTime fechaHoraDestino;

    private LocalDate diaActividad;
}
