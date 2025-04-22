package com.flybook.librovuelo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class PedidoVacaciones {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private CicloVacaciones cicloVacaciones;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estadoPedido;

    private String comentarioDelUsuario;

    private String comentarioDelLider;

//    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDateTime fechaDeSolicitud;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaDesdePrimeraQuincenaPlanA;
    private Boolean seAprobofechaDesdePrimeraQuincenaPlanA;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaHastaPrimeraQuincenaPlanA;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaDesdeSegundaQuincenaPlanA;
    private Boolean seAprobofechaDesdeSegundaQuincenaPlanA;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaHastaSegundaQuincenaPlanA;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaDesdeDiasOpuestosPlanA;
    private Boolean seAprobofechaDesdeDiasOpuestosPlanA;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaHastaDiasOpuestosPlanA;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaDesdePrimeraQuincenaPlanB;
    private Boolean seAprobofechaDesdePrimeraQuincenaPlanB;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaHastaPrimeraQuincenaPlanB;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaDesdeSegundaQuincenaPlanB;
    private Boolean seAprobofechaDesdeSegundaQuincenaPlanB;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaHastaSegundaQuincenaPlanB;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaDesdeDiasOpuestosPlanB;
    private Boolean seAprobofechaDesdeDiasOpuestosPlanB;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaHastaDiasOpuestosPlanB;

}