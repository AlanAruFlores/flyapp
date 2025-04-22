package com.flybook.librovuelo.repository;
import com.flybook.librovuelo.model.CicloVacaciones;
import com.flybook.librovuelo.model.EstadoPedido;
import com.flybook.librovuelo.model.PedidoVacaciones;
import com.flybook.librovuelo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoVacacionesRepository extends JpaRepository<PedidoVacaciones, Long> {

   // encontrar si el ciclo ya existen para un pedido
    PedidoVacaciones findPedidoVacacionesByCicloVacaciones(CicloVacaciones cicloVacaciones);

    // sort by ciclo desc and estado solicitado
    List<PedidoVacaciones> findAllByOrderByCicloVacacionesDescFechaDeSolicitudDescEstadoPedidoDesc();

    List<PedidoVacaciones> findAllByUserOrderByFechaDeSolicitudDesc(User user);

    List<PedidoVacaciones> findAllByUserAndEstadoPedidoOrderByFechaDeSolicitudDesc(User user, EstadoPedido estadoPedido);

    List<PedidoVacaciones> findAllByUser(User user);

    List<PedidoVacaciones> findAllByUserAndCicloVacaciones_NumeroDeCiclo(User user, Integer numeroDeCiclo);

    List <PedidoVacaciones> findAllByCicloVacaciones_NumeroDeCicloAndEstadoPedidoAndUser_Lider_Id(Integer anio,EstadoPedido estadoPedido, Long idLider);
 List <PedidoVacaciones> findAllByEstadoPedidoAndUser_Lider_Id(EstadoPedido estadoPedido, Long idLider);

    List<PedidoVacaciones> findPedidoVacacionesByCicloVacacionesAndUser(Integer cicloVacaciones, User user);
    PedidoVacaciones findPedidoVacacionesByUserAndCicloVacaciones( User user,CicloVacaciones cicloVacaciones);
    PedidoVacaciones findByUserAndEstadoPedidoAndCicloVacaciones_NumeroDeCiclo(User user, EstadoPedido estadoPedido, Integer numeroDeCiclo);


    List<PedidoVacaciones> findAllByUser_LiderOrderByFechaDeSolicitudDesc(User lider);

    PedidoVacaciones findPedidoVacacionesById(Long id);
}
