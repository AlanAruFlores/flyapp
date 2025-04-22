package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.model.Generacion;
import com.flybook.librovuelo.repository.GeneracionRepository;
import com.flybook.librovuelo.service.GeneracionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GeneracionServiceImpl implements GeneracionService {

    @Autowired
    private GeneracionRepository generacionRepository;

    @Override
    public void save(Generacion generacion) throws Exception {


        //Si el registro es Nuevo
        if (generacion.getId() == null) {

            //Valido que no se repita el numero de generacion
            if (this.generacionRepository.findByNumero(generacion.getNumero()) != null)
                throw new Exception("El número de la generacion ya existe");
        }
        //Si es un update
        else {
            //Veriico si se cambio el numero de generation
            Optional<Generacion> generacionAnterior = this.generacionRepository.findById(generacion.getId());
            Integer numeroGeneracionAnterior = generacionAnterior.get().getNumero();

            if (numeroGeneracionAnterior.compareTo(generacion.getNumero()) != 0)
                if (this.generacionRepository.findByNumero(generacion.getNumero()) != null)
                    throw new Exception("El número de la generacion ya existe");
        }
        generacionRepository.save(generacion);
    }

    @Override
    public void delete(Generacion generacion) {
        generacionRepository.delete(generacion);
    }

    @Override
    public List<Generacion> findAll() {
        return generacionRepository.findAllByOrderByNumeroAsc();
    }

    @Override
    public Generacion getById(Long id) {
        return generacionRepository.getById(id);
    }

    @Override
    public Generacion findByNumero(Integer numero) {
        return generacionRepository.findByNumero(numero);
    }


}
