package com.flybook.librovuelo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Foliado {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaFoliado;

    @ManyToOne
    private User user;
    private Double horasDiurnas;
    private Double horasNocturnas;
    private Double totalDeHoras;
    private Integer totalAterrizaje;

    private Double horasDiurnasEnFoliadoPrevio;
    private Double horasNocturnasEnFoliadoPrevio;
    private Double totalDeHorasEnFoliadoPrevio;
    private Integer totalAterrizajeEnFoliadoPrevio;

    public Double obtenerTotalDeHoraDiurnasFoliadas(){
        return this.horasDiurnas+this.horasDiurnasEnFoliadoPrevio;
    }

    public Double obtenerTotalDeHoraNocturnasFoliadas(){
        return this.horasNocturnas+this.horasNocturnasEnFoliadoPrevio;
    }

    public Double obtenerTotalDeHorasFoliadas(){
        return obtenerTotalDeHoraNocturnasFoliadas()+this.obtenerTotalDeHoraDiurnasFoliadas();
    }


    public Integer obtenerTotalAterrizajesFoliadas(){
        return totalAterrizaje+this.totalAterrizajeEnFoliadoPrevio;
    }

}


