package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.model.Estacion;
import com.flybook.librovuelo.model.HorarioPorEstacion;
import com.flybook.librovuelo.model.VueloRealizado;
import com.flybook.librovuelo.service.CalculoDeHorasService;
import com.flybook.librovuelo.service.HorarioPorEstacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;


@Service
public class CalculoDeHorasServiceImpl implements CalculoDeHorasService {

    @Autowired
    private HorarioPorEstacionService horarioPorEstacionService;




    @Override
    public Double calcularhorasEntreDosFechas(LocalDateTime inicial, LocalDateTime fin) {

        LocalDateTime tempDateTime = LocalDateTime.from(inicial);

        long years = tempDateTime.until(fin, ChronoUnit.YEARS);
        tempDateTime = tempDateTime.plusYears(years);

        long months = tempDateTime.until(fin, ChronoUnit.MONTHS);
        tempDateTime = tempDateTime.plusMonths(months);

        long days = tempDateTime.until(fin, ChronoUnit.DAYS);
        tempDateTime = tempDateTime.plusDays(days);

        long hours = tempDateTime.until(fin, ChronoUnit.HOURS);
        tempDateTime = tempDateTime.plusHours(hours);

        long minutes = tempDateTime.until(fin, ChronoUnit.MINUTES);
        tempDateTime = tempDateTime.plusMinutes(minutes);

        long seconds = tempDateTime.until(fin, ChronoUnit.SECONDS);

        Double totalHoras = years*24*365 + months*24*30 + days*24 + hours + minutes/60.0 + seconds/3600.0 ;

        return totalHoras;
    }

    @Override
    public Double calcularCantidadDeHorasDiurna(LocalDateTime inicial, LocalDateTime fin) {



        Estacion estacionDespegue = horarioPorEstacionService.obtenerEstacionDeUnaFecha(inicial.toLocalDate());
        HorarioPorEstacion horarioEstacionDespegue=horarioPorEstacionService.obtenerHorarioPorEstacionByEstacion(estacionDespegue);
        LocalTime horaIncioEstacionDespegue = horarioEstacionDespegue.getComienzoHorarioDiurno();
        LocalTime horaFinEstacionDespegue = horarioEstacionDespegue.getComienzoHorarioNocturno();
        LocalDateTime comienzoHoraDiurnoDespegue= LocalDateTime.of(inicial.toLocalDate(),horaIncioEstacionDespegue);
        LocalDateTime comienzoHoraNocturnoDespegue= LocalDateTime.of(inicial.toLocalDate(),horaFinEstacionDespegue);


        Estacion estacionDiaSiguiente = horarioPorEstacionService.obtenerEstacionDeUnaFecha(inicial.toLocalDate().plusDays(1L));
        HorarioPorEstacion horarioEstacionDiaSiguiente=horarioPorEstacionService.obtenerHorarioPorEstacionByEstacion(estacionDiaSiguiente);
        LocalTime horaIncioEstacionDiaSiguiente = horarioEstacionDiaSiguiente.getComienzoHorarioDiurno();
        LocalTime horaFinEstacionDiaSiguiente = horarioEstacionDiaSiguiente.getComienzoHorarioNocturno();
        LocalDateTime comienzoHoraDiurnoDiaSiguiente= LocalDateTime.of(inicial.toLocalDate().plusDays(1L),horaIncioEstacionDiaSiguiente);
     //   LocalDateTime comienzoHoraNocturnoDiaSiguiente= LocalDateTime.of(fin.toLocalDate(),horaFinEstacionDiaSiguiente);



        LocalDateTime inicio=inicial;
        LocalDateTime finalizacion=fin;

        //Valido que si la hra de inicio es antes del horario nocturo y que la hora de fin sea atesdel omienzo del horario nocturno
        if ((inicial.isBefore(comienzoHoraDiurnoDespegue) || inicial.equals(comienzoHoraDiurnoDespegue)&& fin.isBefore(comienzoHoraNocturnoDespegue))) {
            inicio = comienzoHoraDiurnoDespegue;

        }


        //si el despegue esta antes del comienzo nocturno y el ateriizaje despues del nocturno y antes del diurno del dia siguiente
        if(inicial.isBefore(comienzoHoraNocturnoDespegue) && (inicial.isAfter(comienzoHoraDiurnoDespegue) || inicial.isEqual(comienzoHoraDiurnoDespegue))
                && (fin.isAfter(comienzoHoraNocturnoDespegue) ||fin.isEqual(comienzoHoraNocturnoDespegue) ) && (fin.isBefore(comienzoHoraDiurnoDiaSiguiente) || fin.isEqual(comienzoHoraDiurnoDiaSiguiente)) ) {
            finalizacion = comienzoHoraNocturnoDespegue.minusMinutes(1L);

        }

        if( (fin.isAfter(comienzoHoraDiurnoDiaSiguiente)  || fin.equals(comienzoHoraDiurnoDiaSiguiente))){
          if (inicial.isAfter(comienzoHoraNocturnoDespegue) || inicial.isEqual(comienzoHoraNocturnoDespegue)) {
              inicio = comienzoHoraDiurnoDiaSiguiente;
              finalizacion = fin;
          }
          if (inicial.isBefore(comienzoHoraNocturnoDespegue) && (inicial.isAfter(comienzoHoraDiurnoDespegue) || inicial.isEqual(comienzoHoraDiurnoDespegue))){
              return calcularTiempoTotalVuelo(inicio,comienzoHoraNocturnoDespegue.minusMinutes(1L))
                      + calcularTiempoTotalVuelo(comienzoHoraDiurnoDiaSiguiente,fin);
          }
        }



        if ((inicial.isBefore(comienzoHoraDiurnoDespegue) && fin.isBefore(comienzoHoraDiurnoDespegue) )||
                (inicial.isAfter(comienzoHoraNocturnoDespegue) || inicial.equals(comienzoHoraNocturnoDespegue) )
                        && fin.isBefore(comienzoHoraDiurnoDiaSiguiente)
                        && (fin.isAfter(comienzoHoraNocturnoDespegue)|| fin.isEqual(comienzoHoraNocturnoDespegue)))
            return 0.0;

        return this.calcularTiempoTotalVuelo(inicio,finalizacion);
    }

    @Override
    public Double calcularCantidadDeHorasNocturna(LocalDateTime inicial, LocalDateTime fin) {

        return this.calcularTiempoTotalVuelo(inicial, fin)  - calcularCantidadDeHorasDiurna(inicial, fin);

    }
    @Override

    public Double calcularTiempoTotalVuelo(LocalDateTime despegue, LocalDateTime aterrizaje) {
        return this.calcularhorasEntreDosFechas(despegue,aterrizaje);
    }



    @Override
    public void calcularTodasLasHorasDeUnVuelo(VueloRealizado vueloRealizado) {

        vueloRealizado.setHorasDiurnas(this.calcularCantidadDeHorasDiurna(vueloRealizado.getFechahoraDespegue(),
                vueloRealizado.getFechahoraAterrizaje()));

        vueloRealizado.setHorasNocturnas(
                this.calcularCantidadDeHorasNocturna(vueloRealizado.getFechahoraDespegue(),
                vueloRealizado.getFechahoraAterrizaje())
        );

        vueloRealizado.setTotalDeHoras(this.calcularTiempoTotalVuelo(vueloRealizado.getFechahoraDespegue(),
                vueloRealizado.getFechahoraAterrizaje()));

    }
}
