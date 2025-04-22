package com.flybook.librovuelo.service;

import com.flybook.librovuelo.dto.HabilitarVacaciones;
import com.flybook.librovuelo.model.*;

import java.time.LocalDate;
import java.util.List;


// PedidoVacaciones findPedidoVacacionesByCicloVacaciones(CicloVacaciones cicloVacaciones);

// PedidoVacaciones BuscarPedidoVacacionesByUsuarioyCicloVacaciones(User user,CicloVacaciones cicloVacaciones);
//  List<PedidoVacaciones> findAllByUserOrderByFechaDeSolicitudDesc(User user);
// PedidoVacaciones findByUserAndEstadoPedidoAndCicloVacaciones_NumeroDeCiclo(User user, EstadoPedido estadoPedido, Integer numeroDeCiclo);

///  List<PedidoVacaciones> findAllByCicloVacaciones_NumeroDeCicloAndEstadoPedidoAndUser_Lider_Id(Integer anio, EstadoPedido estadoPedido, Long idLider);

// List <PedidoVacaciones> findAllByEstadoPedidoAndUser_Lider_Id(EstadoPedido estadoPedido, Long idLider);
public interface PedidoVacacionesService {
    void save(PedidoVacaciones pedidoVacaciones) throws Exception;

    void guardar(PedidoVacaciones pedidoVacaciones);
    List<PedidoVacaciones> findAll();

    PedidoVacaciones buscarPedidoVacacionesPorId(Long id);


    List<PedidoVacaciones> obtenerPedidosDeVacacionQueTieneUnLiderParaAnalizar(User lider);
    List<PedidoVacaciones> findAllByUserAndEstadoPedidoOrderByFechaDeSolicitudDesc(User user, EstadoPedido estadoPedido);

    List<PedidoVacaciones> findAllByUserAndEstadoPedido_Solicitado(User user) throws Exception;

    List<PedidoVacaciones> findAllByUserAndEstadoPedido_Cerrado(User user) throws Exception;

    List<PedidoVacaciones> findAllByUserAndEstadoPedido_Rechazado(User user) throws Exception;


    List<PedidoVacaciones> findAllByUser(User user);

    List<PedidoVacaciones> obtenerTodosLosPedidosParaUnNumeroDeCiclo(User user, Integer ciclo);

    List<PedidoVacaciones> obtenerTodosLosPedidosPorEstadoYNumeroDeCiclo(User user, EstadoPedido estado, Integer ciclo);

    List<PedidoVacaciones> obtenerPedidosDeVacacionQueTieneUnLiderParaAnalizarParaUnCiclo(User user, CicloVacaciones cicloVacaciones);

    HabilitarVacaciones buscarHabilitarVacaciones();

    boolean sePuedenPedirVacaciones();

    PedidoVacaciones buscarPedidoDeVacacionesPorIdDeCicloYPorUsuario(Long idCiclo, User user);
}