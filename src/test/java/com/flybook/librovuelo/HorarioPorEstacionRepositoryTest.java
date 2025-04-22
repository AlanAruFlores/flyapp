package com.flybook.librovuelo;


import com.flybook.librovuelo.model.Estacion;
import com.flybook.librovuelo.model.HorarioPorEstacion;
import com.flybook.librovuelo.repository.HorarioPorEstacionRepository;


import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnableJpaRepositories("com.flybook.librovuelo.repository")
@EnableTransactionManagement
public class HorarioPorEstacionRepositoryTest {


    @Autowired
    private HorarioPorEstacionRepository horarioPorEstacionRepository;




    @Test
    public void queAgregaUnHorarioAUnaEstacionDelAnio() throws ParseException {
        HorarioPorEstacion nuevoHorario = new HorarioPorEstacion();
        nuevoHorario.setEstacion(Estacion.VERANO);

        nuevoHorario.setComienzoHorarioDiurno(LocalTime.of(9,00));
        nuevoHorario.setComienzoHorarioNocturno(LocalTime.of(23,00));
        //nuevoHorario.setFechainicial(new SimpleDateFormat("MM-dd").parse("12-21"));
        //nuevoHorario.setFechaFinal(new SimpleDateFormat("MM-dd").parse("03-20"));

        horarioPorEstacionRepository.save(nuevoHorario);
        HorarioPorEstacion horarioVerano= horarioPorEstacionRepository.findByEstacion(Estacion.VERANO);
        //System.out.println("fecha " + horarioVerano.getFechaFinal().toString() + horarioVerano.getFechainicial().toString());
        assertThat(horarioVerano.getEstacion()).isEqualTo(Estacion.VERANO);
    }


}
