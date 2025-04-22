package com.flybook.librovuelo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
public class VueloRealizado {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fechahoraDespegue;

    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime fechahoraAterrizaje;

    @ManyToOne
    private Aeropuerto aeropuertoOrigen;

    @ManyToOne
    private Aeropuerto aeropuertoDestino;
    private String finalidadDelVuelo;

    @ManyToOne
    private Avion avion;

    private String  folioRVA;
    private String  instructorTcp;
    private String  tipoAeronave;




    private Double horasDiurnas;
    private Double horasNocturnas;
    private Double totalDeHoras;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaFoliado;

    @ManyToOne
    private Foliado foliado;
//
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VueloRealizado that = (VueloRealizado) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
