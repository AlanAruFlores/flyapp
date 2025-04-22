package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.model.Foliado;
import com.flybook.librovuelo.model.User;
import com.flybook.librovuelo.model.VueloRealizado;
import com.flybook.librovuelo.repository.FoliadoRepository;
import com.flybook.librovuelo.repository.VueloRealizadoRepository;
import com.flybook.librovuelo.service.FoliadoService;
import com.flybook.librovuelo.service.VueloRealizadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class FoliadoServiceImpl implements FoliadoService {

    @Autowired
    private FoliadoRepository foliadoRepository;

    @Autowired
    private VueloRealizadoService vueloRealizadoService;

    @Autowired
    private VueloRealizadoRepository vueloRealizadoRepository;

    @Override
    public Optional<Foliado> findById(Long id){ return this.foliadoRepository.findById(id);}

    @Override
    public void delete(Foliado foliado) {
        foliadoRepository.delete(foliado);
    }

    @Override
    public Foliado buscarFoliadoPorFecha(LocalDate fechaFoliado) {
        return this.foliadoRepository.findByFechaFoliado(fechaFoliado);
    }


    @Override
    public List<Foliado> obtenerTodosLosFoliados(User user)  {
        return this.foliadoRepository.findAllByUserOrderByFechaFoliadoDesc(user);
    }

    @Override
    public Page<VueloRealizado> buscarHorasfoliadas(User user, Foliado foliado, Pageable pageable) {
        return this.vueloRealizadoRepository.findAllByUserAndFoliado (user,foliado,pageable);
    }

    @Override
    public Page<VueloRealizado> buscarHorasNofoliadas(User user, PageRequest pageRequest) {
        return this.vueloRealizadoRepository.findAllByUserAndFoliadoIsNull(user,pageRequest);
    }

    @Override
    public Foliado buscarFoliadoPorId(Long id) {
        return this.foliadoRepository.findById(id).get();
    }

    @Override
    public Double obtenerTotalDeHorasFoliada(User user) {
        Double totalDeHorasIniciales = user.getTvTotalInicial();
        List <VueloRealizado> vueloRalizadoFoliado = this.vueloRealizadoRepository.findAllByUserAndFoliadoIsNotNullOrderByFechahoraDespegueAsc(user);
        Double totalFoliado=0.0;
        for (VueloRealizado vuelo: vueloRalizadoFoliado) {
            totalFoliado+= vuelo.getTotalDeHoras();


        }



        return totalFoliado +totalDeHorasIniciales;
    }

    @Override
    public Double obtenerTotalDeHorasFoliadaNocturna(User user) {

        Double totalDeHorasNocturnasIniciales = user.getTvNocturnoInicial();
        List <VueloRealizado> vueloRalizadoFoliado = this.vueloRealizadoRepository.findAllByUserAndFoliadoIsNotNullOrderByFechahoraDespegueAsc(user);

        Double totalFoliado=0.0;
        for (VueloRealizado vuelo: vueloRalizadoFoliado) {
            totalFoliado+= vuelo.getHorasNocturnas();


        }

        return totalFoliado + totalDeHorasNocturnasIniciales;
    }

    @Override
    public Double obtenerTotalDeHorasFoliadaDiurnas(User user) {

        Double totalDeHorasDiurnasIniciales = user.getTvDiurnoInicial();
        List <VueloRealizado> vueloRalizadoFoliado = this.vueloRealizadoRepository.findAllByUserAndFoliadoIsNotNullOrderByFechahoraDespegueAsc(user);
        Double totalFoliado=0.0;
        for (VueloRealizado vuelo: vueloRalizadoFoliado) {
            totalFoliado+= vuelo.getHorasDiurnas();


        }

        return totalFoliado +totalDeHorasDiurnasIniciales;
    }

    @Override
    public Integer obtenerTotalDeAterrizajeFoliada(User user) {
        int totalDeAterrizajesIniciales = user.getCantidadAterrizajeInical();
        return this.vueloRealizadoRepository.findAllByUserAndFoliadoIsNotNullOrderByFechahoraDespegueAsc(user).size() +
                totalDeAterrizajesIniciales;
    }




    @Override
    public Foliado generarNuevoFoliado(User user, PageRequest pageRequest) throws Exception {

        Page<VueloRealizado> vuelosAFolear = this.vueloRealizadoService.buscarHorasNofoliadas(user,pageRequest);

        if(vuelosAFolear.isEmpty())
            throw new Exception("No hay vuelos para folear");

        List <VueloRealizado> vuelosNofoliados=vuelosAFolear.getContent();

        LocalDate fechaActual= LocalDate.now();

        Foliado nuevoFoliado =new Foliado();
        nuevoFoliado.setUser(user);


        Double horasDiurnasEnFoliadoPrevio=this.vueloRealizadoService.obtenerTotalDeHorasFoliadaDiurnas(user);
        Double horasNocturnasEnFoliadoPrevio=this.obtenerTotalDeHorasFoliadaNocturna(user);
        Double totalDeHorasEnFoliadoPrevio=horasDiurnasEnFoliadoPrevio+horasNocturnasEnFoliadoPrevio;
        Integer totalAterrizajeEnFoliadoPrevio=this.obtenerTotalDeAterrizajeFoliada(user);

        Double totalHorasDiurnas=this.vueloRealizadoService.obtenerTotalDeHorasDiurnas(vuelosNofoliados);
        Double totalDeHorasNocturnas= this.vueloRealizadoService.obtenerTotalDeHorasNocturnas(vuelosNofoliados);
        Double totalHoras =totalHorasDiurnas+ totalDeHorasNocturnas;
        Integer totalAterrizajes=this.vueloRealizadoService.obtenerTotalAterrizajes(vuelosNofoliados);


        nuevoFoliado.setHorasDiurnas(totalHorasDiurnas);
        nuevoFoliado.setHorasNocturnas(totalDeHorasNocturnas);
        nuevoFoliado.setTotalAterrizaje(totalAterrizajes);
        nuevoFoliado.setTotalDeHoras(totalHoras);


        nuevoFoliado.setFechaFoliado(fechaActual);



        nuevoFoliado.setHorasDiurnasEnFoliadoPrevio(horasDiurnasEnFoliadoPrevio);
         nuevoFoliado.setHorasNocturnasEnFoliadoPrevio(horasNocturnasEnFoliadoPrevio);
         nuevoFoliado.setTotalDeHorasEnFoliadoPrevio(totalDeHorasEnFoliadoPrevio);
         nuevoFoliado.setTotalAterrizajeEnFoliadoPrevio(totalAterrizajeEnFoliadoPrevio);
        this.foliadoRepository.save(nuevoFoliado);

        this.vueloRealizadoService.asignarFoliadoAAHorasNoFoliadas(vuelosNofoliados,nuevoFoliado);


        return nuevoFoliado;
    }


}
