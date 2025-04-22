package com.flybook.librovuelo.service;

import com.flybook.librovuelo.model.PedidoDiasLibres;
import com.flybook.librovuelo.model.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface PedidoDiasLibresService {

    void validaryGuardar(PedidoDiasLibres pedido) throws Exception;

    PedidoDiasLibres getById(Long id);
    List<PedidoDiasLibres> findAllOrderAsc();
    List<PedidoDiasLibres> getAllByUser_IdAndComienzo3DiasLibresGreaterThanEqualAndComienzo3DiasLibresLessThanEqual(Long id, LocalDate desde, LocalDate hasta);

    List<PedidoDiasLibres> getAllByUser_IdAndDiaLibreGreaterThanEqualAndDiaLibreLessThanEqual(Long id, LocalDate desde, LocalDate hasta);
    public List<PedidoDiasLibres> buscarDiasLibresDeUnUsuario(User user);

    List<PedidoDiasLibres> buscarDiasLibresDeMisTripulantes(User user, Map<String, Object> params);

    List<PedidoDiasLibres> buscarTodasLosPedidosdeDiasLibres(Map<String, Object> params);
}
