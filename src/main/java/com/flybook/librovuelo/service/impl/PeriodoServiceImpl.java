package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.dto.OrdenPeriodoAnioDesendiente;
import com.flybook.librovuelo.dto.SugerenciaVacacion;
import com.flybook.librovuelo.model.*;
import com.flybook.librovuelo.repository.PeriodoRepository;
import com.flybook.librovuelo.service.PeriodoService;
import com.flybook.librovuelo.service.UtilsService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Service
public class PeriodoServiceImpl implements PeriodoService {

    private final PeriodoRepository periodoRepository;
    private final UtilsService utilsService;

    public PeriodoServiceImpl(PeriodoRepository periodoRepository, UtilsService utilsService) {
        this.periodoRepository = periodoRepository;
        this.utilsService = utilsService;
    }


    @Override
    public List<Periodo> mostrarTodosLosPeriodos() {
        List<Periodo> periodos = periodoRepository.findAll(Sort.by("anio"));

        return periodos;
    }

    @Override
    public Periodo buscarPorID(Long id) {
        return periodoRepository.findById(id);
    }

    @Override
    public List<Periodo> buscarPorAnio(Integer anio) {
        return periodoRepository.findAllByAnio(anio);
    }


    @Override
    public void generarPeriodos(Integer anio) throws Exception {
        if (anio == null) {
            throw new Exception("Ingresa un año para generar automaticamente el periodo");
        }
        if (periodoRepository.findAllByAnio(anio).size() > 0) {
            throw new Exception("Ya se encuentran generados los periodos del año " + anio);

        } else if (anio < 2018 || anio > 2999) { //PARA QUE NO SE PUEDA PONER CUALQUIER FECHA
            throw new Exception("Ingrese un año entre 2018 y  2999.");
        }
        ArrayList<Periodo> periodos = new ArrayList<>();
        Periodo generado = new Periodo();
        for (int i = 1; i <= 24; i++) {
            generado.setAnio(anio);
            generado.setId((long) (i + anio));
            if (generado.getCantidadCupos() == null) {
                generado.setCantidadCupos(0);
                generado.setCantidadCuposTcp(0);
                generado.setCantidadCuposJefe(0);
                generado.setCantidadCuposComandante(0);
                generado.setCantidadCuposSegundoOficial(0);

            }
            if (i == 1) {
                periodos.add(generado);
                generado.setFechaInicio(LocalDate.parse(anio + "-01-01"));
                generado.setFechaFinalizacion(generado.getFechaInicio().plusDays(14)/*LocalDate.parse("2022-01-15")*/);
                periodoRepository.save(generado);
            } else if (i % 2 != 0) {
                periodos.add(generado);
                generado.setFechaInicio(periodos.get(i - 1).getFechaInicio().withDayOfMonth(1));
                generado.setFechaInicio(periodos.get(i - 1).getFechaInicio().plusMonths(1));
                generado.setFechaFinalizacion(periodos.get(i - 1).getFechaFinalizacion().withDayOfMonth(15));
                generado.setFechaFinalizacion(generado.getFechaFinalizacion().plusMonths(1));
                periodoRepository.save(generado);
            } else if (i % 2 == 0 && i != 4) {
                periodos.add(generado);
                generado.setFechaInicio(periodos.get(i - 1).getFechaInicio().withDayOfMonth(16));
                generado.setFechaFinalizacion(periodos.get(i - 1).getFechaFinalizacion().withDayOfMonth(30));
                periodoRepository.save(generado);
            } else {
                //febrero es un caso especial
                periodos.add(generado);
                generado.setFechaInicio(periodos.get(i - 1).getFechaInicio().withDayOfMonth(16));
                generado.setFechaFinalizacion(periodos.get(i - 1).getFechaFinalizacion().withDayOfMonth(28));
                periodoRepository.save(generado);
            }
        }
    }

    @Override
    public void save(Periodo periodo) {
        periodoRepository.save(periodo);
    }

    @Override
    public Periodo actualizarCupos(Periodo periodo) {
        return periodoRepository.save(periodo);
    }

    public Periodo getByAnioAndFechaInicioBeforeAndFechaFinalizacionAfter(Integer anio, LocalDate desde, LocalDate hasta) {
        return getByAnioAndFechaInicioBeforeAndFechaFinalizacionAfter(anio, desde, hasta);
    }

    /*
        @Override
        public List<SugerenciaVacacion> obtenerSugerenciasVacaciones(PedidoVacaciones pedidoVacaciones, User usuarioVacacion, TipoAusencia tipoAusencia) {
            List<SugerenciaVacacion> sugerenciaVacacions = new ArrayList<>();
            CicloVacaciones cicloVacaciones=pedidoVacaciones.getCicloVacaciones();
            List<Periodo> periodos= this.periodoRepository.findAllByFechaInicioIsGreaterThanEqualAndFechaFinalizacionIsLessThanEqualOrderByFechaInicioAsc(cicloVacaciones.getComienzoCiclo(),cicloVacaciones.getFinalizacionCiclo());
            for (Periodo periodo: periodos) {
                Integer cantidadVacacionesAsiganadaEnUnPeriodo =this.ausenciaService.cantidadDeVacacionesAsignadasEntreDosFechas(periodo.getFechaInicio(),periodo.getFechaFinalizacion());
               Integer cuposDisponibles = periodo.getCantidadCupos() - cantidadVacacionesAsiganadaEnUnPeriodo;
                if (cuposDisponibles > 0) {
                    SugerenciaVacacion sugerenciaVacacion = new SugerenciaVacacion();
                    sugerenciaVacacion.setPeriodo(periodo);
                    sugerenciaVacacion.setUser(usuarioVacacion);
                    sugerenciaVacacion.setCicloVacaciones(cicloVacaciones);
                    sugerenciaVacacion.setCantidadCupos(cuposDisponibles);
                    sugerenciaVacacion.setTipoAusencia(tipoAusencia);
                    sugerenciaVacacions.add(sugerenciaVacacion);

                }

            }

            sugerenciaVacacions.sort((SugerenciaVacacion s1, SugerenciaVacacion s2) -> s2.getCantidadCupos().compareTo(s1.getCantidadCupos()));
            return sugerenciaVacacions;
        }

     */
    @Override
    public List<SugerenciaVacacion> obtenerSugerenciasVacacionesPorTipoCargo(PedidoVacaciones pedidoVacaciones, User usuarioVacacion, TipoAusencia tipoAusencia, TipoCargo tipoCargo) {
        List<SugerenciaVacacion> sugerenciaVacacions = new ArrayList<>();
        CicloVacaciones cicloVacaciones = pedidoVacaciones.getCicloVacaciones();
        List<Periodo> periodos = this.periodoRepository.findAllByFechaInicioIsGreaterThanEqualAndFechaFinalizacionIsLessThanEqualOrderByFechaInicioAsc(cicloVacaciones.getComienzoCiclo(), cicloVacaciones.getFinalizacionCiclo());
        for (Periodo periodo : periodos) {
            Integer cantidadVacacionesAsiganadaEnUnPeriodo = this.utilsService.cantidadDeVacacionesAsignadasEntreDosFechasPorCargo(periodo.getFechaInicio(), periodo.getFechaFinalizacion(), usuarioVacacion.getTipoCargo());
            Integer cuposDisponibles = periodo.getCantidadCuposPorCargo(tipoCargo) - cantidadVacacionesAsiganadaEnUnPeriodo;
            if (cuposDisponibles > 0) {
                SugerenciaVacacion sugerenciaVacacion = new SugerenciaVacacion();
                sugerenciaVacacion.setPeriodo(periodo);
                sugerenciaVacacion.setUser(usuarioVacacion);
                sugerenciaVacacion.setCicloVacaciones(cicloVacaciones);
                sugerenciaVacacion.setCantidadCupos(cuposDisponibles);
                sugerenciaVacacion.setTipoAusencia(tipoAusencia);
                sugerenciaVacacions.add(sugerenciaVacacion);

            }

        }

        sugerenciaVacacions.sort((SugerenciaVacacion s1, SugerenciaVacacion s2) -> s2.getCantidadCupos().compareTo(s1.getCantidadCupos()));
        return sugerenciaVacacions;
    }

    public Periodo obtenerElPeriodoParaUnaFecha(LocalDate fecha) {

        Periodo periodo = periodoRepository.findPeriodoByFechaInicioIsLessThanEqualAndFechaFinalizacionIsGreaterThanEqual(fecha, fecha);
        return periodo;
    }

    @Override
    public List<Integer> devolverTodosLosAnios() {
        List<Periodo> periodos = new ArrayList<>();
        periodos = periodoRepository.findAll();
        Set<Integer> anios = new TreeSet<>(new OrdenPeriodoAnioDesendiente());
        for (Periodo periodo : periodos) {
            anios.add(periodo.getAnio());
        }
       /* for (int i = 0; i<periodos.size(); i++) {
            if (i == 0 ){
                anios.add(periodos.get(i).getAnio());
            } else if (i > 0 && !periodos.get(i).getAnio().equals(periodos.get(i-1).getAnio())){
                anios.add(periodos.get(i).getAnio());
            }
        }*/

        return new ArrayList<>(anios);
    }
}
