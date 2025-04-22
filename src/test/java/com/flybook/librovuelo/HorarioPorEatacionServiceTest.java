package com.flybook.librovuelo;



import com.flybook.librovuelo.model.Estacion;
import com.flybook.librovuelo.service.HorarioPorEstacionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
//@EnableJpaRepositories("com.flybook.librovuelo.service")
@EnableTransactionManagement
public class HorarioPorEatacionServiceTest {


    @Autowired
    private HorarioPorEstacionService horarioPorEstacionService;


    @Test
    public void queDadaUnaFechaSeObtengaLaEstacionDelAño() {

        LocalDate fecha = LocalDate.of(21,01, 01);
        //System.out.println("estacion año" + horarioPorEstacionService.obtenerEstacion(fecha));
        assertThat(horarioPorEstacionService.obtenerEstacionDeUnaFecha(fecha)).isEqualTo(Estacion.VERANO);
        LocalDate fecha1 = LocalDate.of(21,3, 21);
        //System.out.println("estacion año" + horarioPorEstacionService.obtenerEstacion(fecha));
        assertThat(horarioPorEstacionService.obtenerEstacionDeUnaFecha(fecha1)).isEqualTo(Estacion.OTOÑO);
        LocalDate fecha2 = LocalDate.of(21,6, 21);
        //System.out.println("estacion año" + horarioPorEstacionService.obtenerEstacion(fecha));
        assertThat(horarioPorEstacionService.obtenerEstacionDeUnaFecha(fecha2)).isEqualTo(Estacion.INVIERNO);
        LocalDate fecha3 = LocalDate.of(21,9, 21);
        //System.out.println("estacion año" + horarioPorEstacionService.obtenerEstacion(fecha));
        assertThat(horarioPorEstacionService.obtenerEstacionDeUnaFecha(fecha3)).isEqualTo(Estacion.PRIMAVERA);
    }
}