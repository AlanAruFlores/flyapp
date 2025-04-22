package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.exceptions.HoraDespegueException;
import com.flybook.librovuelo.exceptions.TiempoVueloMayorA20HoraasException;
import com.flybook.librovuelo.model.*;
import com.flybook.librovuelo.repository.VueloRealizadoRepository;
import com.flybook.librovuelo.service.HorarioPorEstacionService;
import com.flybook.librovuelo.service.VueloRealizadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class VueloRealizadoServiceImpl implements VueloRealizadoService {

    @Autowired
    private HorarioPorEstacionService horarioPorEstacionService;

    @Autowired
    private VueloRealizadoRepository vueloRealizadoRepository;

    @Override
    public void save(VueloRealizado vueloRealizado) {
        vueloRealizadoRepository.save(vueloRealizado);
    }

    @Override
    public void delete(VueloRealizado vueloRealizado) {
        vueloRealizadoRepository.delete(vueloRealizado);
    }

    @Override
    public List<VueloRealizado> findAllByFoliado(Foliado foliado) {
        return this.vueloRealizadoRepository.findAllByFoliado(foliado);
    }

    ;

    @Override
    public VueloRealizado getById(Long id) {
        return this.vueloRealizadoRepository.getById(id);
    }

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

        Double totalHoras = years * 24 * 365 + months * 24 * 30 + days * 24 + hours + minutes / 60.0;

        return totalHoras;
    }

    @Override
    public Double calcularCantidadDeHorasDiurna(LocalDateTime inicial, LocalDateTime fin) {

        Double cantidadHorasDiurnas = 0.0;


        cantidadHorasDiurnas = this.calcularTiempoTotalVuelo(inicial, fin) - calcularCantidadDeHorasNocturna(inicial, fin);
        return cantidadHorasDiurnas;


    }

    @Override
    public Double calcularCantidadDeHorasNocturna(LocalDateTime inicial, LocalDateTime fin) {

        Estacion estacionDespegue = horarioPorEstacionService.obtenerEstacionDeUnaFecha(inicial.toLocalDate());
        HorarioPorEstacion horarioEstacionDespegue = horarioPorEstacionService.obtenerHorarioPorEstacionByEstacion(estacionDespegue);
        LocalTime horaIncioEstacionDespegue = horarioEstacionDespegue.getComienzoHorarioDiurno();
        LocalTime horaFinEstacionDespegue = horarioEstacionDespegue.getComienzoHorarioNocturno();
        LocalDateTime comienzoHoraDiurnoDespegue = LocalDateTime.of(inicial.toLocalDate(), horaIncioEstacionDespegue);
        LocalDateTime comienzoHoraNocturnoDespegue = LocalDateTime.of(inicial.toLocalDate(), horaFinEstacionDespegue);


        Estacion estacionDiaSiguiente = horarioPorEstacionService.obtenerEstacionDeUnaFecha(inicial.toLocalDate().plusDays(1L));
        HorarioPorEstacion horarioEstacionDiaSiguiente = horarioPorEstacionService.obtenerHorarioPorEstacionByEstacion(estacionDiaSiguiente);
        LocalTime horaIncioEstacionDiaSiguiente = horarioEstacionDiaSiguiente.getComienzoHorarioDiurno();
        LocalTime horaFinEstacionDiaSiguiente = horarioEstacionDiaSiguiente.getComienzoHorarioNocturno();
        LocalDateTime comienzoHoraDiurnoDiaSiguiente = LocalDateTime.of(fin.toLocalDate(), horaIncioEstacionDiaSiguiente);
        LocalDateTime comienzoHoraNocturnoDiaSiguiente = LocalDateTime.of(fin.toLocalDate(), horaFinEstacionDiaSiguiente);

        Double cantidadHorasNocturnas = 0.0;


        LocalDateTime principio = LocalDateTime.from(inicial);
        LocalDateTime finalizacion = LocalDateTime.from(fin);

        //ejemplo vuelo entre las 22 y 6
        if ((inicial.isAfter(comienzoHoraNocturnoDespegue) || inicial.isEqual(comienzoHoraDiurnoDespegue)) && fin.isBefore(comienzoHoraNocturnoDiaSiguiente)) {
            principio = LocalDateTime.from(inicial);
            finalizacion = LocalDateTime.from(fin);
        }

        //ejemplo vuelo despues las de 22 y a despues 6
        if ((inicial.isAfter(comienzoHoraNocturnoDespegue) || inicial.isEqual(comienzoHoraDiurnoDespegue)) && fin.isAfter(comienzoHoraNocturnoDiaSiguiente)) {
            principio = LocalDateTime.from(comienzoHoraNocturnoDespegue);
            finalizacion = LocalDateTime.from(comienzoHoraDiurnoDiaSiguiente);
        }

        //  vuelo desde  desde las 18 a las 3
        if ((inicial.isBefore(comienzoHoraNocturnoDespegue) && inicial.isAfter(comienzoHoraDiurnoDespegue)) && fin.isBefore(comienzoHoraNocturnoDiaSiguiente)) {
            principio = LocalDateTime.from(comienzoHoraNocturnoDespegue);
            finalizacion = LocalDateTime.from(fin);
        }

        // vuelo desde las 18 a 10 del  otro dia
        if (inicial.isBefore(comienzoHoraNocturnoDespegue) && fin.isAfter(comienzoHoraNocturnoDiaSiguiente)) {
            principio = LocalDateTime.from(comienzoHoraNocturnoDespegue);
            finalizacion = LocalDateTime.from(comienzoHoraDiurnoDiaSiguiente);
        }


        //por la mañana desde 2 a 10
        if (inicial.isBefore(comienzoHoraDiurnoDespegue) && fin.isAfter(comienzoHoraDiurnoDespegue)) {
            principio = LocalDateTime.from(inicial);
            finalizacion = LocalDateTime.from(comienzoHoraDiurnoDespegue);
        }

        // vuelo de 2 a 4
        if (inicial.isBefore(comienzoHoraDiurnoDespegue) && fin.isBefore(comienzoHoraDiurnoDespegue)) {
            principio = LocalDateTime.from(inicial);
            finalizacion = LocalDateTime.from(fin);
        }


        cantidadHorasNocturnas = this.calcularhorasEntreDosFechas(principio, finalizacion);

        return cantidadHorasNocturnas;
    }

    @Override

    public Double calcularTiempoTotalVuelo(LocalDateTime despegue, LocalDateTime aterrizaje) {
        return this.calcularhorasEntreDosFechas(despegue, aterrizaje);
    }

    @Override
    public Boolean validarHorasDeVuelosRelizados(User user, VueloRealizado vueloRealizado) throws Exception {

//        VueloRealizado vueloRealizadoAlMacenado = this.vueloRealizadoRepository.findById(vueloRealizado.getId()).get();
//        if (vueloRealizado.getFechahoraDespegue().equals(vueloRealizadoAlMacenado.getFechahoraDespegue())
//                && vueloRealizado.getFechahoraAterrizaje().equals(vueloRealizadoAlMacenado.getFechahoraAterrizaje()))
//            return true;
//
//        if ()
        return validarHoraDeAterrizajeSeaPosteriorAlDespegue(vueloRealizado) && validarQueElTvNoSeaMayorA20hORAS(vueloRealizado) && validarQueNoSeSuperpongaConOtroVuelo(user, vueloRealizado);

    }

    private boolean validarQueNoSeSuperpongaConOtroVuelo(User user, VueloRealizado vueloRealizado) throws Exception {
        List<VueloRealizado> cantidadDeVelosParaLAHoraDeDespegue = this.vueloRealizadoRepository.findAllByUserAndFechahoraDespegueBetween(user, vueloRealizado.getFechahoraDespegue(), vueloRealizado.getFechahoraAterrizaje());

        ///si es edicion se debe eliminar el vuelo a a editar
        if (cantidadDeVelosParaLAHoraDeDespegue.contains(vueloRealizado))
            cantidadDeVelosParaLAHoraDeDespegue.remove(vueloRealizado);

        if (cantidadDeVelosParaLAHoraDeDespegue.size() > 0)
            throw new Exception("Ya tiene un vuelo asignado para ese rango Horario");


        List<VueloRealizado> cantidadDeVelosParaLAHorAterrizaje = this.vueloRealizadoRepository.findAllByUserAndFechahoraAterrizajeBetween(user, vueloRealizado.getFechahoraDespegue(), vueloRealizado.getFechahoraAterrizaje());

        ///si es edicion se debe eliminar el vuelo a a editar
        if (cantidadDeVelosParaLAHorAterrizaje.contains(vueloRealizado))
            cantidadDeVelosParaLAHorAterrizaje.remove(vueloRealizado);

        if (cantidadDeVelosParaLAHorAterrizaje.size() > 0)
            throw new Exception("Ya tiene un vuelo asignado para ese rango Horario");

        return true;
    }

    @Override
    public Double obtenerTotalDeHorasDiurnas(List<VueloRealizado> vuelosRealizados) {
        Double totalHorasDiurnas = 0.0;
        for (VueloRealizado vueloRealizado : vuelosRealizados) {
            totalHorasDiurnas += vueloRealizado.getHorasDiurnas();

        }
        return totalHorasDiurnas;
    }

    @Override
    public Double obtenerTotalDeHorasNocturnas(List<VueloRealizado> vuelosRealizados) {
        Double totalHorasNocturnas = 0.0;
        for (VueloRealizado vueloRealizado : vuelosRealizados) {
            totalHorasNocturnas += vueloRealizado.getHorasNocturnas();

        }
        return totalHorasNocturnas;
    }

    @Override
    public Double obtenerTotalDeHoras(List<VueloRealizado> vuelosRealizados) {
        Double totalHoras = 0.0;
        for (VueloRealizado vueloRealizado : vuelosRealizados) {
            totalHoras += vueloRealizado.getHorasNocturnas() + vueloRealizado.getHorasDiurnas();

        }
        return totalHoras;
    }

    @Override
    public Integer obtenerTotalAterrizajes(List<VueloRealizado> vuelosRealizados) {
        return vuelosRealizados.size();
    }

    @Override
    public Page<VueloRealizado> findAllByUser(User user, Pageable pageable) {
        return this.vueloRealizadoRepository.findAllByUser(user, pageable);
    }


    @Override
    public List<VueloRealizado> findAllByUser(User user) {
        return this.vueloRealizadoRepository.findAllByUser(user);
    }

    private Boolean validarQueElTvNoSeaMayorA20hORAS(VueloRealizado vueloRealizado) throws TiempoVueloMayorA20HoraasException {
        if (this.calcularTiempoTotalVuelo(vueloRealizado.getFechahoraDespegue(), vueloRealizado.getFechahoraAterrizaje()) >= 20.0)

            throw new TiempoVueloMayorA20HoraasException();

        return true;
    }


    private Boolean validarHoraDeAterrizajeSeaPosteriorAlDespegue(VueloRealizado vueloRealizado) throws HoraDespegueException {
        if (vueloRealizado.getFechahoraAterrizaje().isBefore(vueloRealizado.getFechahoraDespegue()))

            throw new HoraDespegueException();

        return true;
    }

    @Override
    public List<VueloRealizado> obtenerVuelosRealizadosEntreDosFecha(LocalDateTime desde, LocalDateTime hasta) {
        return this.vueloRealizadoRepository.findAllByFechahoraDespegueBetween(desde, hasta);

    }


    /*  Logica de foleo de Horas */

    @Override
    public Page<VueloRealizado> buscarHorasfoliadas(User user, Foliado foliado, Pageable pageable) {
        return this.vueloRealizadoRepository.findAllByUserAndFoliado(user, foliado, pageable);
    }

    @Override
    public Double obtenerTotalDeHorasFoliada(User user) {
        List<VueloRealizado> vueloRalizadoFoliado = this.vueloRealizadoRepository.findAllByUserAndFoliadoIsNotNullOrderByFechahoraDespegueAsc(user);
        Double totalFoliado = user.getTvTotalInicial();
        for (VueloRealizado vuelo : vueloRalizadoFoliado) {
            totalFoliado += vuelo.getTotalDeHoras();


        }

        return totalFoliado;
    }

    @Override
    public Double obtenerTotalDeHorasFoliadaNocturna(User user) {
        List<VueloRealizado> vueloRalizadoFoliado = this.vueloRealizadoRepository.findAllByUserAndFoliadoIsNotNullOrderByFechahoraDespegueAsc(user);
        Double totalFoliado = user.getTvNocturnoInicial();
        for (VueloRealizado vuelo : vueloRalizadoFoliado) {
            totalFoliado += vuelo.getHorasNocturnas();


        }

        return totalFoliado;
    }

    @Override
    public Double obtenerTotalDeHorasFoliadaDiurnas(User user) {
        List<VueloRealizado> vueloRalizadoFoliado = this.vueloRealizadoRepository.findAllByUserAndFoliadoIsNotNullOrderByFechahoraDespegueAsc(user);
        Double totalFoliado = user.getTvDiurnoInicial();
        for (VueloRealizado vuelo : vueloRalizadoFoliado) {
            totalFoliado += vuelo.getHorasDiurnas();


        }

        return totalFoliado;
    }

    @Override
    public Integer obtenerTotalDeAterrizajeFoliada(User user) {
        return user.getCantidadAterrizajeInical() + this.vueloRealizadoRepository.findAllByUserAndFoliadoIsNotNullOrderByFechahoraDespegueAsc(user).size();
    }

    @Override
    public Page<VueloRealizado> buscarHorasNofoliadas(User user, PageRequest pageRequest) {
        return this.vueloRealizadoRepository.findAllByUserAndFoliadoIsNull(user, pageRequest);
    }

    @Override
    public void asignarFoliadoAAHorasNoFoliadas(List<VueloRealizado> vuelosNofoliados, Foliado nuevoFoliado) {

        for (VueloRealizado vueloRealizado : vuelosNofoliados) {
            vueloRealizado.setFoliado(nuevoFoliado);
            this.vueloRealizadoRepository.save(vueloRealizado);

        }

    }

    @Override

    public Page<VueloRealizado> findAllByUserAndFoliado(User user, Foliado foliado, PageRequest pageRequest) {
        return this.vueloRealizadoRepository.findAllByUserAndFoliado(user, foliado, pageRequest);
    }

    @Override
    public List<VueloRealizado> findAllByUserAndFoliado(User user, Foliado foliado) {
        return this.vueloRealizadoRepository.findAllByUserAndFoliado(user, foliado);
    }


    public VueloRealizado obtenerVueloRealizadoDesdeNotificacion(User user, Notificacion notificacion) {
        VueloRealizado vueloRealizadoCompartido = vueloRealizadoRepository.findById(notificacion.getIdVueloRealizado()).get();
        VueloRealizado vueloRealizado = new VueloRealizado();
        vueloRealizado.setAeropuertoDestino(vueloRealizadoCompartido.getAeropuertoDestino());
        vueloRealizado.setAeropuertoOrigen(vueloRealizadoCompartido.getAeropuertoOrigen());
        vueloRealizado.setAvion(vueloRealizadoCompartido.getAvion());
        vueloRealizado.setFechahoraAterrizaje(vueloRealizadoCompartido.getFechahoraAterrizaje());
        vueloRealizado.setFechahoraDespegue(vueloRealizadoCompartido.getFechahoraDespegue());
        vueloRealizado.setFinalidadDelVuelo(vueloRealizadoCompartido.getFinalidadDelVuelo());
        vueloRealizado.setFolioRVA(vueloRealizadoCompartido.getFolioRVA());
        vueloRealizado.setHorasDiurnas(vueloRealizadoCompartido.getHorasDiurnas());
        vueloRealizado.setHorasNocturnas(vueloRealizadoCompartido.getHorasNocturnas());
        vueloRealizado.setTotalDeHoras(vueloRealizadoCompartido.getTotalDeHoras());
        vueloRealizado.setInstructorTcp(vueloRealizadoCompartido.getInstructorTcp());
        vueloRealizado.setTipoAeronave(vueloRealizadoCompartido.getTipoAeronave());
        vueloRealizado.setUser(user);
        vueloRealizado.setId(null);
        vueloRealizado.setFoliado(null);
        vueloRealizado.setFechaFoliado(null);
        return vueloRealizado;


    }

    @Override
    public Double obtenerCantidadTotalDeHorasDiurnas(User user) {
        List<VueloRealizado> vuelosRealizados = this.findAllByUser(user);


        return user.getTvDiurnoInicial() + this.obtenerTotalDeHorasDiurnas(vuelosRealizados);
    }

    @Override
    public Double obtenerCantidadTotalDeHorasNocturnas(User user) {

        List<VueloRealizado> vuelosRealizados = this.findAllByUser(user);
        return user.getTvNocturnoInicial() + obtenerTotalDeHorasNocturnas(vuelosRealizados);
    }

    @Override
    public Double obtenerCantidadTotalDeHoras(User user) {

        return obtenerCantidadTotalDeHorasNocturnas(user) + obtenerCantidadTotalDeHorasDiurnas(user);
    }

    @Override
    public Integer obtenerCantidadTotalDeAterrizaje(User user) {
        Integer caantidadDeVuelosRealizado = this.findAllByUser(user).size();
        return user.getCantidadAterrizajeInical() + caantidadDeVuelosRealizado;
    }


}
