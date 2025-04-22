package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.exceptions.UsuariosNoAsigandosRecurrentException;
import com.flybook.librovuelo.model.*;
import com.flybook.librovuelo.repository.RecurrentRepository;
import com.flybook.librovuelo.repository.TripulanteRecurrentRepository;
import com.flybook.librovuelo.repository.UserRepository;
import com.flybook.librovuelo.service.AusenciaService;
import com.flybook.librovuelo.service.TripulanteRecurrentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class TripulanteRecurrentServiceImpl implements TripulanteRecurrentService {

    @Autowired
    private TripulanteRecurrentRepository tripulanteRecurrentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecurrentRepository recurrentRepository;


    @Autowired
    private AusenciaService ausenciaService;

    @Override
    public void borrarTripulanteDeUnRecurrent(Long idTripulante, Long idRecurrent) throws Exception {
        TripulanteRecurrent tripulanteRecurrent = this.findByTripulanteIdAndAndRecurrentId(idTripulante, idRecurrent);
        this.tripulanteRecurrentRepository.delete(tripulanteRecurrent);
        Ausencia ausenciaaBorrar = this.ausenciaService.obtenerAusenciaRecurrentDeUnUsuarioEntreDosFechas(tripulanteRecurrent.getTripulante(),tripulanteRecurrent.getRecurrent().getFechaDesde(),tripulanteRecurrent.getRecurrent().getFechaHasta());

        this.ausenciaService.borrarAusencia(ausenciaaBorrar);

    }

    @Override
    public void save(List<Long> idsTripulantes, Long idRecurrent) throws UsuariosNoAsigandosRecurrentException {
        Recurrent recurrent = recurrentRepository.findRecurrentById(idRecurrent);

        LocalDate incio=recurrent.getFechaDesde();
        LocalDate hasta=recurrent.getFechaHasta();
        List<Ausencia> asenciasDeUsuariosQueNoPuedenAgregarseAUnRecurrente=new ArrayList<>();



        if (idRecurrent != null && idsTripulantes!=null && idsTripulantes.size()!=0) {
            Iterator<Long> iter = idsTripulantes.iterator();
            while(iter.hasNext()) {

                User tripulante = userRepository.findUserById(iter.next());


                List<Ausencia> ausenciasDeUnUsuario = this.ausenciaService.obtenerTodasLasAusenciasDeUnUsuarioEntreDosFechas(tripulante, incio, hasta);
                if (ausenciasDeUnUsuario.size() == 0) {
                    TripulanteRecurrent tr = tripulanteRecurrentRepository.findByTripulanteIdAndAndRecurrentId
                            (tripulante.getId(), idRecurrent);


                    if (tr == null) {
                        TripulanteRecurrent registro = new TripulanteRecurrent();
                        registro.setRecurrent(recurrent);
                        registro.setTripulante(tripulante);
                        this.tripulanteRecurrentRepository.save(registro);
                        Ausencia ausencia=new Ausencia();
                        ausencia.setUser(tripulante);
                        ausencia.setFechaDesde(recurrent.getFechaDesde());
                        ausencia.setFechaHasta(recurrent.getFechaHasta());
                        ausencia.setTipoAusencia(TipoAusencia.RECURRENT);
                        this.ausenciaService.guardarPedidoDeAusencia(ausencia);
                    }
                }
                else
                    asenciasDeUsuariosQueNoPuedenAgregarseAUnRecurrente.addAll(ausenciasDeUnUsuario);

            }
        }
        if(asenciasDeUsuariosQueNoPuedenAgregarseAUnRecurrente.size()>0)
            throw new UsuariosNoAsigandosRecurrentException(asenciasDeUsuariosQueNoPuedenAgregarseAUnRecurrente);
    }

    @Override
    public void delete(TripulanteRecurrent tripulanteRecurrent) {
        this.tripulanteRecurrentRepository.delete(tripulanteRecurrent);
    }

    @Override
    public TripulanteRecurrent getByTripulante(User tripulante) {
        return this.tripulanteRecurrentRepository.getByTripulante(tripulante);
    }

    @Override
    public List<TripulanteRecurrent> findAll() {
        return this.tripulanteRecurrentRepository.findAll();
    }

    @Override
    public TripulanteRecurrent findByRecurrent(Recurrent recurrent) {
        return this.tripulanteRecurrentRepository.findByRecurrent(recurrent);
    }

    @Override
    public List<TripulanteRecurrent> findTripulanteRecurrentsByTripulante(User tripulante) {
        return this.tripulanteRecurrentRepository.findTripulanteRecurrentsByTripulante(tripulante);
    }

    @Override
    public List<TripulanteRecurrent> findTripulanteRecurrentsByRecurrent(Recurrent recurrent) {
        return this.tripulanteRecurrentRepository.findTripulanteRecurrentsByRecurrent(recurrent);
    }

    @Override
    public TripulanteRecurrent findByRecurrentAndTripulante(Recurrent recurrent, User tripulante) {
        return this.tripulanteRecurrentRepository.findByRecurrentAndTripulante(recurrent, tripulante);
    }

    @Override
    public TripulanteRecurrent findByTripulanteIdAndAndRecurrentId(Long idTripulante, Long idRecurrent) {
        return this.tripulanteRecurrentRepository.findByTripulanteIdAndAndRecurrentId(idTripulante, idRecurrent);
    }

    @Override
    public List<TripulanteRecurrent> findByTripulante(User tripulante) {
        return this.tripulanteRecurrentRepository.findByTripulante(tripulante);
    }

    @Override
    public List<TripulanteRecurrent> findAllByTripulante(User tripulante) {
        return this.tripulanteRecurrentRepository.findAllByTripulante(tripulante);
    }

    @Override
    public List<TripulanteRecurrent> findAllByRecurrent(Recurrent recurrent) {
        return this.tripulanteRecurrentRepository.findAllByRecurrent(recurrent);
    }

    @Override
    public Optional<TripulanteRecurrent> findById(Long id) {
        return this.tripulanteRecurrentRepository.findById(id);
    }

    @Override
    public TripulanteRecurrent getById(Long id) {
        return this.tripulanteRecurrentRepository.getById(id);
    }

    @Override
    public List<TripulanteRecurrent> getByTripulanteIdAndRecurrentId(Long idTripulante, Long idRecurrent) {
        return this.tripulanteRecurrentRepository.getByTripulanteIdAndRecurrentId(idTripulante, idRecurrent);
    }

    @Override
    public TripulanteRecurrent findByTripulanteId(Long id) {
        return this.tripulanteRecurrentRepository.findByTripulanteId(id);
    }

    @Override
    public TripulanteRecurrent findByRecurrentId(Long idRecurrent) {
        return this.tripulanteRecurrentRepository.findByRecurrentId(idRecurrent);
    }
}
