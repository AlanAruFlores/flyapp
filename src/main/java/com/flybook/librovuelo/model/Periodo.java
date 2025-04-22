package com.flybook.librovuelo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Periodo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private Integer anio;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaInicio;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate fechaFinalizacion;

    private Integer cantidadCupos;
    private Integer cantidadCuposTcp;
    private Integer cantidadCuposJefe;
    private Integer cantidadCuposSegundoOficial;
    private Integer cantidadCuposComandante;


    public Integer getCantidadCuposPorCargo(TipoCargo tipoCargo) {
        Integer cantidadCupos = 0;
        switch (tipoCargo) {
            case TCP:
                cantidadCupos = cantidadCuposTcp;
                break;
            case JEFECABINA:
                cantidadCupos = cantidadCuposJefe;
                break;
            case PRIMEROFICIAL:
                cantidadCupos = cantidadCuposComandante;
                break;
            case SEGUNDOOFICIAL:
                cantidadCupos = cantidadCuposSegundoOficial;
                break;
        }
        return cantidadCupos;
    }
}
