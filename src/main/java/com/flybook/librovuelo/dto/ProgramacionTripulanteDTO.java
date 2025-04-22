package com.flybook.librovuelo.dto;

import com.flybook.librovuelo.model.Aeropuerto;
import com.flybook.librovuelo.model.EventoCalendario;
import com.flybook.librovuelo.model.TipoActividad;
import com.flybook.librovuelo.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ProgramacionTripulanteDTO {
    public Long id;

    public User tripulante;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate diaActividad;
    public TipoActividad tipoActividad;

    public Aeropuerto aeropuertoOrigen;
    public Aeropuerto aeropuertoDestino;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate fechaPresentacion;
    public LocalTime horaPresentacion;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate fechaDespegue;
    public LocalTime horaDespegue;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate fechaDestino;
    public LocalTime horaDestino;

    public EventoCalendario eventoCalendario;
  //  public Boolean agendarCalendario;
}
