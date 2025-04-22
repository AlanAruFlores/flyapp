package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.model.Ausencia;
import com.flybook.librovuelo.model.TipoAusencia;
import com.flybook.librovuelo.model.TipoCargo;
import com.flybook.librovuelo.model.User;
import com.flybook.librovuelo.repository.AusenciaRepository;
import com.flybook.librovuelo.service.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class UtilsServiceImpl implements UtilsService {

    private final AusenciaRepository ausenciaRepository;

    @Autowired
    public UtilsServiceImpl(AusenciaRepository ausenciaRepository) {
        this.ausenciaRepository = ausenciaRepository;
    }

    @Override
    public Integer cantidadDeVacacionesAsignadasEntreDosFechasPorCargo(LocalDate fechaDesde, LocalDate fechaHasta, TipoCargo tipoCargo) {
        List<TipoAusencia> ausenciasDeVacaciones = new ArrayList<>();
        ausenciasDeVacaciones.add(TipoAusencia.VAC15);
        ausenciasDeVacaciones.add(TipoAusencia.OPUESTO10);
        List<Ausencia> result = this.ausenciaRepository.findAllByFechaDesdeIsGreaterThanEqualAndFechaDesdeIsLessThanEqualAndTipoAusenciaInAndUser_TipoCargoOrderByFechaDesde(fechaDesde, fechaHasta, ausenciasDeVacaciones, tipoCargo);
        return result.size();
    }

    @Override
    public List<Ausencia> obtenerTodasLasAusenciasDeUnUsuarioEntreDosFechas(User user, LocalDate inicio, LocalDate finalizacion) {
        HashSet<Ausencia> ausencias = new HashSet<>();

        ausencias.addAll(this.ausenciaRepository.findAllByUserAndFechaDesdeIsLessThanAndFechaDesdeIsLessThanEqualAndFechaHastaIsGreaterThanEqualOrderByFechaDesdeAsc(user, inicio, finalizacion, finalizacion));
        ausencias.addAll(this.ausenciaRepository.findAllByUserAndFechaDesdeIsGreaterThanEqualAndFechaDesdeIsLessThanEqualAndFechaHastaIsGreaterThanEqualOrderByFechaDesdeAsc(user, inicio, finalizacion, finalizacion));
        ausencias.addAll(this.ausenciaRepository.findAllByUserAndFechaDesdeIsGreaterThanEqualAndFechaDesdeIsLessThanEqualAndFechaHastaIsLessThanOrderByFechaDesdeAsc(user, inicio, finalizacion, finalizacion));

        return new ArrayList<>(ausencias);

    }
}
