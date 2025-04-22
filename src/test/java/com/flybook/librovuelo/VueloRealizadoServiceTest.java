package com.flybook.librovuelo;


import com.flybook.librovuelo.model.Estacion;
import com.flybook.librovuelo.model.HorarioPorEstacion;
import com.flybook.librovuelo.model.VueloRealizado;
import com.flybook.librovuelo.service.HorarioPorEstacionService;
import com.flybook.librovuelo.service.VueloRealizadoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;

@SpringBootTest
@EnableTransactionManagement
public class VueloRealizadoServiceTest {
    @Autowired
    private VueloRealizadoService vueloRealizadoService;

    @Autowired
    private HorarioPorEstacionService horarioPorEstacionService;

    @Test
    public void alIngresarDosFechaySeObtengaLaCantidadDeHorasEnDecimal(){

        LocalDateTime inicio=LocalDateTime.of(21,01,01,10,00);
        LocalDateTime fin=LocalDateTime.of(21,01,01,11,30);

        Double valorObtenido = vueloRealizadoService.calcularhorasEntreDosFechas(inicio,fin);
        assertThat(valorObtenido).isEqualTo(1.5);


    }

    @Test
    public void dadoUnLibroDeVueloCalcularLasHorasDiurnas(){
        HorarioPorEstacion verano = new HorarioPorEstacion();
        verano.setEstacion(Estacion.VERANO);
        verano.setComienzoHorarioDiurno(LocalTime.of(9,0));
        verano.setComienzoHorarioNocturno(LocalTime.of(23,01));

        //when(horarioPorEstacionService.obtenerHorarioPorEstacionByEstacion(any(Estacion.class))).thenReturn(verano);

        VueloRealizado vuelo = new VueloRealizado();


        LocalDateTime despegue1=LocalDateTime.of(21,01,01,5,00);
        LocalDateTime aterrizaje1=LocalDateTime.of(21,01,01,11,30);
        Double valorObtenido2 = vueloRealizadoService.calcularCantidadDeHorasDiurna(despegue1,aterrizaje1);
        assertThat(valorObtenido2).isEqualTo(2.5);


        LocalDateTime despegue3=LocalDateTime.of(21,01,01,9,00);
        LocalDateTime aterrizaje3=LocalDateTime.of(21,01,01,12,30);
        Double valorObtenido3 = vueloRealizadoService.calcularCantidadDeHorasDiurna(despegue3,aterrizaje3);
        assertThat(valorObtenido3).isEqualTo(3.5);



        LocalDateTime despegue4=LocalDateTime.of(21,01,01,22,00);
        LocalDateTime aterrizaje4=LocalDateTime.of(21,01,02,4,00);
        Double valorObtenido4 = vueloRealizadoService.calcularCantidadDeHorasDiurna(despegue4,aterrizaje4);
        assertThat(valorObtenido4).isEqualTo(1.0);


        LocalDateTime despegue5=LocalDateTime.of(21,01,01,22,00);
        LocalDateTime aterrizaje5=LocalDateTime.of(21,01,02,11,00);
        Double valorObtenido5 = vueloRealizadoService.calcularCantidadDeHorasDiurna(despegue5,aterrizaje5);
        assertThat(valorObtenido5).isEqualTo(3.0);


    }
}
