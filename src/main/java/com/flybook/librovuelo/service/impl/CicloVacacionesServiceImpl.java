package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.model.CicloVacaciones;
import com.flybook.librovuelo.model.Generacion;
import com.flybook.librovuelo.repository.CicloVacacionesRepository;
import com.flybook.librovuelo.service.CicloVacacionesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CicloVacacionesServiceImpl implements CicloVacacionesService {


    @Autowired
    private CicloVacacionesRepository cicloVacacionesRepository;

    @Override
    public List<CicloVacaciones> obtenerTodosLosCiclos() {
        return cicloVacacionesRepository.findAll();
    }


    @Override
    public void save(CicloVacaciones cicloVacaciones) throws Exception {


        if (cicloVacaciones.getId()==null) {
            if (this.cicloVacacionesRepository.findCicloVacacionesByNumeroDeCicloAndGeneracion(cicloVacaciones.getNumeroDeCiclo(), cicloVacaciones.getGeneracion()) != null)
                throw new Exception("Ya existe el numero de ciclo " + cicloVacaciones.getNumeroDeCiclo() + " para la generacion " + cicloVacaciones.getGeneracion().getNumero());
        }


        else {

                CicloVacaciones cicloVacacionesAntesDeUpdate = this.cicloVacacionesRepository.getById(cicloVacaciones.getId());
                if (cicloVacaciones.getGeneracion().getNumero().compareTo(cicloVacacionesAntesDeUpdate.getGeneracion().getNumero())!=0
                    || cicloVacaciones.getNumeroDeCiclo().compareTo(cicloVacacionesAntesDeUpdate.getNumeroDeCiclo())!=0) {
                    if (this.cicloVacacionesRepository.findCicloVacacionesByNumeroDeCicloAndGeneracion(cicloVacaciones.getNumeroDeCiclo(), cicloVacaciones.getGeneracion()) != null)
                        throw new Exception("Ya existe el numero de ciclo " + cicloVacaciones.getNumeroDeCiclo() + " para la generacion " + cicloVacaciones.getGeneracion().getNumero());
                }


            }
        cicloVacacionesRepository.save(cicloVacaciones);
    }

    @Override
    public void update(CicloVacaciones cicloVacaciones) {
        this.cicloVacacionesRepository.save(cicloVacaciones);

    }

    @Override
    public void deleteId(Long id) {
        cicloVacacionesRepository.deleteById(id);
    }

    @Override
    public CicloVacaciones getById(Long id) {
        return  cicloVacacionesRepository.getById(id);
    }

    @Override
    public List<CicloVacaciones> findAll() {
        return cicloVacacionesRepository.findAll();
    }

    @Override
    public List<CicloVacaciones> obtenerTodosLosCiclosParaUnaGeneracion(Generacion generacion){
        return this.cicloVacacionesRepository.findAllByGeneracion(generacion);
    }

    @Override
    public List<CicloVacaciones> findAllByGeneracionOrderByNumeroDeCicloDesc(Generacion generacion) {
        return this.cicloVacacionesRepository.findAllByGeneracion(generacion);
    }

    @Override
    public List<CicloVacaciones> findAllByGeneracion(Generacion generacion) {
        return this.cicloVacacionesRepository.findAllByGeneracion(generacion);
    }

    @Override
    public List<CicloVacaciones> obtenerTodosLosPedidoParaUnNumeroDeCiclo(Integer numeroDeCiclo) {
        return this.cicloVacacionesRepository.findAllByNumeroDeCiclo(numeroDeCiclo);
    }

    @Override
    public Page<CicloVacaciones> findAll(Pageable pageable) {
        return this.cicloVacacionesRepository.findAll(pageable);
    }

    @Override
    public CicloVacaciones obtenerCicloDeVacacionesParaUnageneracionYUnaFechaDada(Generacion generacion, LocalDate FechaDesde) {
        return this.cicloVacacionesRepository.findByGeneracionAndComienzoCicloIsLessThanEqualAndFinalizacionCicloIsGreaterThanEqual(generacion,
                FechaDesde,FechaDesde);
    }

    public CicloVacaciones findById(Long id) {
        return cicloVacacionesRepository.getById(id);
    }

 /*   public Integer obtenerElCicloParaUnPeriodo(Periodo periodo, Generacion generacion){
        return cicloVacacionesRepository.findCicloVacacionesByNumeroDeCicloAndGeneracion(periodo, generacion);
    }
*/
}
