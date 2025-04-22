package com.flybook.librovuelo.service.impl;
import com.flybook.librovuelo.model.Estacion;
import com.flybook.librovuelo.model.HorarioPorEstacion;
import com.flybook.librovuelo.repository.HorarioPorEstacionRepository;
import com.flybook.librovuelo.service.HorarioPorEstacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Service
public class HorarioPorEstacionServiceImpl implements HorarioPorEstacionService {


    @Autowired
    private HorarioPorEstacionRepository horarioPorEstacionRepository;





    @Override
    public Estacion obtenerEstacionDeUnaFecha(LocalDate fecha) {


        //Integer fechaInt =  fecha.getMonth()*100+fecha.getDay()/10*10+fecha.getDay()%10;
        Integer fechaInt =  fecha.getMonthValue()*100+fecha.getDayOfMonth()/10*10+fecha.getDayOfMonth()%10;

        Estacion estacion=Estacion.VERANO;


        if (fechaInt>=321 && fechaInt <=620)
            estacion=estacion.OTOÑO;


        if (fechaInt>=621 && fechaInt <=920)
            estacion=estacion.INVIERNO;

        if (fechaInt>=921 && fechaInt <=1220)
            estacion=estacion.PRIMAVERA;


        return  estacion;
    }

    @Override
    public void guardar(HorarioPorEstacion horarioPorEstacion) {

        //if (horarioPorEstacion.getEstacion())
    }

    @Override
    public HorarioPorEstacion obtenerHorarioPorEstacionById(Long id) {
        return horarioPorEstacionRepository.getById(id);
    }

    @Override
    public HorarioPorEstacion obtenerHorarioPorEstacionByEstacion(Estacion estacion) {
        return horarioPorEstacionRepository.findByEstacion(estacion);
    }
}
