package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.dto.OrdenPedidoDiasLibres;
import com.flybook.librovuelo.model.Ausencia;
import com.flybook.librovuelo.model.PedidoDiasLibres;
import com.flybook.librovuelo.model.User;
import com.flybook.librovuelo.repository.PedidoDiasLibresRepository;
import com.flybook.librovuelo.repository.UserRepository;
import com.flybook.librovuelo.service.PedidoDiasLibresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;


@Service
public class PedidoDiasLibresServiceImpl implements PedidoDiasLibresService {

    @Autowired
    private PedidoDiasLibresRepository pedidoDiasLibresRepository;


    @Autowired
    private UserRepository userRepository;
    public  void validaryGuardar(PedidoDiasLibres pedidoDiasLibres) throws Exception {
        pedidoDiasLibres.setFechaSolicitud(LocalDate.now());
        LocalDate fechaHabilitacion=pedidoDiasLibres.getFechaSolicitud().plusMonths(1L);
        String listaMes[] = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        int mes = fechaHabilitacion.getMonth().getValue();

        LocalDate inciioPeriodo = fechaHabilitacion.with(firstDayOfMonth());
        LocalDate finalPeriodo = fechaHabilitacion.with(lastDayOfMonth());
        pedidoDiasLibres.setPeriodoDelPedido(inciioPeriodo);
        if (pedidoDiasLibres.getId() == null){

            if (pedidoDiasLibresRepository.
                    getAllByUser_IdAndComienzo3DiasLibresGreaterThanEqualAndComienzo3DiasLibresLessThanEqual(
                            pedidoDiasLibres.getUser().getId(),
                            inciioPeriodo,
                            finalPeriodo)
                    .size()>0
            )throw new Exception("Ya existe una fecha(3 dias) para ese periodo");

            if (pedidoDiasLibresRepository.
                    getAllByUser_IdAndDiaLibreGreaterThanEqualAndDiaLibreLessThanEqual(
                            pedidoDiasLibres.getId(),
                            inciioPeriodo,
                            finalPeriodo)
                    .size()>0
            )throw new Exception("Ya existe una fecha(dia libre) para ese periodo");
        }

        if(pedidoDiasLibres.getDiaLibre().isBefore(inciioPeriodo)||
                pedidoDiasLibres.getDiaLibre().isAfter(finalPeriodo)) {
            throw new Exception("El dia libre solo se acepta para fechas en el mes de " + listaMes[mes - 1] + "");
        }
        if(pedidoDiasLibres.getComienzo3DiasLibres().isBefore(inciioPeriodo)||
                pedidoDiasLibres.getComienzo3DiasLibres().isAfter(finalPeriodo)) {
            throw new Exception("Los 3 dias libres se aceptan para fechas en el mes de " + listaMes[mes - 1] + "");
        }

        pedidoDiasLibresRepository.save(pedidoDiasLibres);
    }

    @Override
    public PedidoDiasLibres getById(Long id) {
        return pedidoDiasLibresRepository.findById(id).get();
    }


    @Override
    public List<PedidoDiasLibres> findAllOrderAsc() {
        return  pedidoDiasLibresRepository.findAll(Sort.by(Sort.Direction.ASC, "FechaSolicitud"));
    }

    @Override
    public List<PedidoDiasLibres> buscarDiasLibresDeUnUsuario(User user) {
        return  pedidoDiasLibresRepository.findAllByUserOrderByFechaSolicitud(user);
    }

    @Override
    public List<PedidoDiasLibres> buscarDiasLibresDeMisTripulantes(User lider,Map<String, Object> params) {
//
//        List<PedidoDiasLibres> diasLibresDeMisTripulantes = new ArrayList<>();
//
//        List<User> misTripulantes = this.userRepository.findAllByLider(lider);
//
//        for (User user : misTripulantes){
//            List<PedidoDiasLibres> pedidoDiasLibres= this.buscarDiasLibresDeUnUsuario(user);
//            if (pedidoDiasLibres !=null)
//                diasLibresDeMisTripulantes.addAll(pedidoDiasLibres);
//        }
//
//
//        return diasLibresDeMisTripulantes.stream().sorted(new OrdenPedidoDiasLibres()).toList();

        LocalDate fechaDesde = !siElCampoEstaVacio(params,"fechaDesde") ? LocalDate.parse((String)params.get("fechaDesde")) : LocalDate.of(1900,1,1);
        LocalDate fechaHasta = !siElCampoEstaVacio(params,"fechaHasta") ? LocalDate.parse((String)params.get("fechaHasta")) : LocalDate.of(2200,1,1);

        if(siElCampoEstaVacio(params,"valor"))
            return this.pedidoDiasLibresRepository.findAllByUser_Lider_IdAndFechaSolicitudGreaterThanEqualAndFechaSolicitudLessThanEqualOrderByFechaSolicitudDesc(lider.getId(),fechaDesde,fechaHasta);

        //Si es que manda a filtrar
        String atributoAFiltrar = (String) params.getOrDefault("atributo", "");
        String valorAFiltrar = (String) params.getOrDefault("valor", "");

        List<PedidoDiasLibres> listaPedidosDiasLibres = switch (atributoAFiltrar){
            case "legajo" -> this.pedidoDiasLibresRepository.findAllByUser_Lider_IdAndUser_LegajoAndFechaSolicitudGreaterThanEqualAndFechaSolicitudLessThanEqualOrderByFechaSolicitudDesc(lider.getId(), Integer.parseInt(valorAFiltrar), fechaDesde, fechaHasta);
            case "dni" -> this.pedidoDiasLibresRepository.findAllByUser_Lider_IdAndUser_DniAndFechaSolicitudGreaterThanEqualAndFechaSolicitudLessThanEqualOrderByFechaSolicitudDesc(lider.getId(), Long.parseLong(valorAFiltrar), fechaDesde, fechaHasta);
            case "nombre" -> this.pedidoDiasLibresRepository.findAllByUser_Lider_IdAndUser_NombreAndFechaSolicitudGreaterThanEqualAndFechaSolicitudLessThanEqualOrderByFechaSolicitudDesc(lider.getId(), valorAFiltrar, fechaDesde,fechaHasta);
            case "apellido" -> this.pedidoDiasLibresRepository.findAllByUser_Lider_IdAndUser_ApellidoAndFechaSolicitudGreaterThanEqualAndFechaSolicitudLessThanEqualOrderByFechaSolicitudDesc(lider.getId(), valorAFiltrar, fechaDesde,fechaHasta);
            default-> new ArrayList<>();
        };

        return listaPedidosDiasLibres;
    }

    private Boolean siElCampoEstaVacio(Map<String, Object> params, String campo){
        return params.get(campo) == null || params.get(campo).toString().isEmpty() || params.get(campo).toString().isBlank();
    }

    @Override
    public List<PedidoDiasLibres> buscarTodasLosPedidosdeDiasLibres(Map<String, Object> params) {
        LocalDate fechaDesde = !siElCampoEstaVacio(params,"fechaDesde") ? LocalDate.parse((String)params.get("fechaDesde")) : LocalDate.of(1900,1,1);
        LocalDate fechaHasta = !siElCampoEstaVacio(params,"fechaHasta") ? LocalDate.parse((String)params.get("fechaHasta")) : LocalDate.of(2200,1,1);

        if(siElCampoEstaVacio(params,"valor"))
            return this.pedidoDiasLibresRepository.findAllByFechaSolicitudGreaterThanEqualAndFechaSolicitudLessThanEqualOrderByFechaSolicitudDesc(fechaDesde,fechaHasta);

        //Si es que manda a filtrar
        String atributoAFiltrar = (String) params.getOrDefault("atributo", "");
        String valorAFiltrar = (String) params.getOrDefault("valor", "");

        List<PedidoDiasLibres> listaPedidosDiasLibres = switch (atributoAFiltrar){
            case "legajo" -> this.pedidoDiasLibresRepository.findAllByUser_LegajoAndFechaSolicitudGreaterThanEqualAndFechaSolicitudLessThanEqualOrderByFechaSolicitudDesc(Integer.parseInt(valorAFiltrar), fechaDesde, fechaHasta);
            case "dni" -> this.pedidoDiasLibresRepository.findAllByUser_DniAndFechaSolicitudGreaterThanEqualAndFechaSolicitudLessThanEqualOrderByFechaSolicitudDesc(Long.parseLong(valorAFiltrar), fechaDesde, fechaHasta);
            case "nombre" -> this.pedidoDiasLibresRepository.findAllByUser_NombreAndFechaSolicitudGreaterThanEqualAndFechaSolicitudLessThanEqualOrderByFechaSolicitudDesc(valorAFiltrar, fechaDesde,fechaHasta);
            case "apellido" -> this.pedidoDiasLibresRepository.findAllByUser_ApellidoAndFechaSolicitudGreaterThanEqualAndFechaSolicitudLessThanEqualOrderByFechaSolicitudDesc(valorAFiltrar, fechaDesde,fechaHasta);
            default-> new ArrayList<>();
        };

        return listaPedidosDiasLibres;
    }

    @Override
    public List<PedidoDiasLibres> getAllByUser_IdAndComienzo3DiasLibresGreaterThanEqualAndComienzo3DiasLibresLessThanEqual(Long id, LocalDate desde, LocalDate hasta) {
        return pedidoDiasLibresRepository.getAllByUser_IdAndComienzo3DiasLibresGreaterThanEqualAndComienzo3DiasLibresLessThanEqual(id,desde,hasta);
    }
    @Override
    public List<PedidoDiasLibres> getAllByUser_IdAndDiaLibreGreaterThanEqualAndDiaLibreLessThanEqual(Long id, LocalDate desde, LocalDate hasta){
        return pedidoDiasLibresRepository.getAllByUser_IdAndDiaLibreGreaterThanEqualAndDiaLibreLessThanEqual(id, desde, hasta);
    }

}
