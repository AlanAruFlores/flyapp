package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.dto.EstadoRegistro;
import com.flybook.librovuelo.dto.HabilitarVacaciones;
import com.flybook.librovuelo.model.CicloVacaciones;
import com.flybook.librovuelo.model.EstadoPedido;
import com.flybook.librovuelo.model.PedidoVacaciones;
import com.flybook.librovuelo.model.User;
import com.flybook.librovuelo.repository.HabilitarVacacionesRepository;
import com.flybook.librovuelo.repository.PedidoVacacionesRepository;
import com.flybook.librovuelo.service.AusenciaService;
import com.flybook.librovuelo.service.CicloVacacionesService;
import com.flybook.librovuelo.service.PedidoVacacionesService;
import com.flybook.librovuelo.service.UtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class PedidoVacacionesServiceImpl implements PedidoVacacionesService {

    @Autowired
    private PedidoVacacionesRepository pedidoVacacionesRepository;

    @Autowired
    private HabilitarVacacionesRepository habilitarVacacionesRepository;

    @Autowired
    private CicloVacacionesService cicloVacacionesService;

    @Autowired
    private UtilsService utilsService;


    @Override
    public void save(PedidoVacaciones pedidoVacaciones) throws Exception {
        // que el id del pedido no existe ya
        //
        // que se sume la fecha hasta a la fecha desde
        pedidoVacaciones.setFechaHastaPrimeraQuincenaPlanA(pedidoVacaciones.getFechaDesdePrimeraQuincenaPlanA().plusDays(14));
        pedidoVacaciones.setFechaHastaSegundaQuincenaPlanA(pedidoVacaciones.getFechaDesdeSegundaQuincenaPlanA().plusDays(14));
        pedidoVacaciones.setFechaHastaDiasOpuestosPlanA(pedidoVacaciones.getFechaDesdeDiasOpuestosPlanA().plusDays(10));
        pedidoVacaciones.setFechaHastaPrimeraQuincenaPlanB(pedidoVacaciones.getFechaDesdePrimeraQuincenaPlanB().plusDays(14));
        pedidoVacaciones.setFechaHastaSegundaQuincenaPlanB(pedidoVacaciones.getFechaDesdeSegundaQuincenaPlanB().plusDays(14));
        pedidoVacaciones.setFechaHastaDiasOpuestosPlanB(pedidoVacaciones.getFechaDesdeDiasOpuestosPlanB().plusDays(10));
        if (pedidoVacaciones.getId() == null) {

            if (this.pedidoVacacionesRepository.findPedidoVacacionesByUserAndCicloVacaciones(pedidoVacaciones.getUser(), pedidoVacaciones.getCicloVacaciones()) != null) {
                throw new Exception("Ya existe un pedido para este ciclo");

            }
            // que sea el unico pedido para ese ciclo
            if (this.utilsService.obtenerTodasLasAusenciasDeUnUsuarioEntreDosFechas(pedidoVacaciones.getUser(), pedidoVacaciones.getFechaDesdePrimeraQuincenaPlanA(), pedidoVacaciones.getFechaHastaPrimeraQuincenaPlanA()).size() > 0) {
                throw new Exception("La primer quincena del Plan A se superpone con una licencia solicitada");
            }

            if (this.utilsService.obtenerTodasLasAusenciasDeUnUsuarioEntreDosFechas(pedidoVacaciones.getUser(), pedidoVacaciones.getFechaDesdePrimeraQuincenaPlanB(), pedidoVacaciones.getFechaHastaPrimeraQuincenaPlanB()).size() > 0) {
                throw new Exception("La primer quincena del Plan B se superpone con una licencia solicitada");
            }


            if (this.utilsService.obtenerTodasLasAusenciasDeUnUsuarioEntreDosFechas(pedidoVacaciones.getUser(), pedidoVacaciones.getFechaDesdeSegundaQuincenaPlanA(), pedidoVacaciones.getFechaHastaSegundaQuincenaPlanA()).size() > 0) {
                throw new Exception("La Segunda quincena del Plan A se superpone con una licencia solicitada");
            }

            if (this.utilsService.obtenerTodasLasAusenciasDeUnUsuarioEntreDosFechas(pedidoVacaciones.getUser(), pedidoVacaciones.getFechaDesdeSegundaQuincenaPlanB(), pedidoVacaciones.getFechaHastaSegundaQuincenaPlanB()).size() > 0) {
                throw new Exception("La Segunda quincena del Plan B se superpone con una licencia solicitada");
            }
            if (this.utilsService.obtenerTodasLasAusenciasDeUnUsuarioEntreDosFechas(pedidoVacaciones.getUser(), pedidoVacaciones.getFechaDesdeDiasOpuestosPlanA(), pedidoVacaciones.getFechaHastaDiasOpuestosPlanA()).size() > 0) {
                throw new Exception("Los dias opuestos del  Plan A se superpone con una licencia solicitada");
            }

            if (this.utilsService.obtenerTodasLasAusenciasDeUnUsuarioEntreDosFechas(pedidoVacaciones.getUser(), pedidoVacaciones.getFechaDesdeDiasOpuestosPlanB(), pedidoVacaciones.getFechaHastaDiasOpuestosPlanB()).size() > 0) {
                throw new Exception("Los dias opuestos del  Plan B se superpone con una licencia solicitada");
            }


        }
        // que el periodo esté dentro del ciclo plan a
        if (pedidoVacaciones.getFechaDesdePrimeraQuincenaPlanA().isBefore(pedidoVacaciones.getCicloVacaciones().getComienzoCiclo()) ||
                pedidoVacaciones.getFechaHastaPrimeraQuincenaPlanA().isAfter(pedidoVacaciones.getCicloVacaciones().getFinalizacionCiclo())
        ) {
            throw new Exception("El pedido plan A primara quincena no está dentro del ciclo");
        }

        if (pedidoVacaciones.getFechaDesdeSegundaQuincenaPlanA().isBefore(pedidoVacaciones.getCicloVacaciones().getComienzoCiclo()) || pedidoVacaciones.getFechaHastaSegundaQuincenaPlanA().isAfter(pedidoVacaciones.getCicloVacaciones().getFinalizacionCiclo())) {
            throw new Exception("El pedido plan A segunda quincena no está dentro del ciclo");
        }

        if (pedidoVacaciones.getFechaDesdeDiasOpuestosPlanA().isBefore(pedidoVacaciones.getCicloVacaciones().getComienzoCiclo()) || pedidoVacaciones.getFechaHastaDiasOpuestosPlanA().isAfter(pedidoVacaciones.getCicloVacaciones().getFinalizacionCiclo())) {
            throw new Exception("El pedido plan A dias opuestos no está dentro del ciclo");
        }

        // que el periodo este dentro del ciclo plan b

        if (pedidoVacaciones.getFechaDesdePrimeraQuincenaPlanB().isBefore(pedidoVacaciones.getCicloVacaciones().getComienzoCiclo()) || pedidoVacaciones.getFechaHastaPrimeraQuincenaPlanB().isAfter(pedidoVacaciones.getCicloVacaciones().getFinalizacionCiclo())) {
            throw new Exception("El pedido plan B primara quincena no está dentro del ciclo");
        }

        if (pedidoVacaciones.getFechaDesdeSegundaQuincenaPlanB().isBefore(pedidoVacaciones.getCicloVacaciones().getComienzoCiclo()) || pedidoVacaciones.getFechaHastaSegundaQuincenaPlanB().isAfter(pedidoVacaciones.getCicloVacaciones().getFinalizacionCiclo())) {
            throw new Exception("El pedido plan B segunda quincena no está dentro del ciclo");
        }

        if (pedidoVacaciones.getFechaDesdeDiasOpuestosPlanB().isBefore(pedidoVacaciones.getCicloVacaciones().getComienzoCiclo()) || pedidoVacaciones.getFechaHastaDiasOpuestosPlanB().isAfter(pedidoVacaciones.getCicloVacaciones().getFinalizacionCiclo())) {
            throw new Exception("El pedido plan B dias opuestos no está dentro del ciclo");
        }

        // que la fecha hasta con la fecha desde no coincida plan a
        if (pedidoVacaciones.getFechaDesdePrimeraQuincenaPlanA().equals(pedidoVacaciones.getFechaDesdeSegundaQuincenaPlanA())) {
            throw new Exception("La fecha de la primera quincena Plan A no puede ser igual a la segunda quincena  Plan A ");
        }

        if (pedidoVacaciones.getFechaDesdePrimeraQuincenaPlanA().equals(pedidoVacaciones.getFechaDesdeDiasOpuestosPlanA())) {
            throw new Exception("La fecha de la primera quincena  Plan A  no puede ser igual a los dias opuestos  Plan A ");
        }

        if (pedidoVacaciones.getFechaDesdeSegundaQuincenaPlanA().equals(pedidoVacaciones.getFechaDesdeDiasOpuestosPlanA())) {
            throw new Exception("La fecha de la segunda quincena no puede ser igual a los dias opuestos");
        }

        // que la fecha hasta con la fecha desde no coincida plan b
        if (pedidoVacaciones.getFechaDesdePrimeraQuincenaPlanB().equals(pedidoVacaciones.getFechaDesdeSegundaQuincenaPlanB())) {
            throw new Exception("La fecha de la primera quincena  Plan B no puede ser igual a la segunda quincena  Plan B");
        }

        if (pedidoVacaciones.getFechaDesdePrimeraQuincenaPlanB().equals(pedidoVacaciones.getFechaDesdeDiasOpuestosPlanB())) {
            throw new Exception("La fecha de la primera quincena  Plan B no puede ser igual a los dias opuestos  Plan B");
        }

        if (pedidoVacaciones.getFechaDesdeSegundaQuincenaPlanB().equals(pedidoVacaciones.getFechaDesdeDiasOpuestosPlanB())) {
            throw new Exception("La fecha de la segunda quincena  Plan B no puede ser igual a los dias opuestos  Plan B");
        }

        // no se pueden pedir 2 vacaciones de 15 dias en el mismo mes
        if (pedidoVacaciones.getFechaDesdePrimeraQuincenaPlanA().getMonth().equals(pedidoVacaciones.getFechaDesdeSegundaQuincenaPlanA().getMonth())) {
            throw new Exception("No se pueden pedir dos vacaciones de quincena en el mismo mes");
        }

        if (pedidoVacaciones.getFechaDesdePrimeraQuincenaPlanB().getMonth().equals(pedidoVacaciones.getFechaDesdeSegundaQuincenaPlanB().getMonth())) {
            throw new Exception("No se pueden pedir dos vacaciones de quincena en el mismo mes");
        }

        validaQueLasFechasDeInicioDeLasQuincenasComiencenCorrectamente(pedidoVacaciones.getFechaDesdePrimeraQuincenaPlanA());
        validaQueLasFechasDeInicioDeLasQuincenasComiencenCorrectamente(pedidoVacaciones.getFechaDesdePrimeraQuincenaPlanB());
        validaQueLasFechasDeInicioDeLasQuincenasComiencenCorrectamente(pedidoVacaciones.getFechaDesdeSegundaQuincenaPlanA());
        validaQueLasFechasDeInicioDeLasQuincenasComiencenCorrectamente(pedidoVacaciones.getFechaDesdeSegundaQuincenaPlanB());

        validaQueLasFechasDeInicioDeLosDiasOpuestoComiencenCorrectamente(pedidoVacaciones.getFechaDesdeDiasOpuestosPlanA());
        validaQueLasFechasDeInicioDeLosDiasOpuestoComiencenCorrectamente(pedidoVacaciones.getFechaDesdeDiasOpuestosPlanB());

        pedidoVacacionesRepository.save(pedidoVacaciones);
    }

    private void validaQueLasFechasDeInicioDeLasQuincenasComiencenCorrectamente(LocalDate fecha) throws Exception {
        switch (fecha.getMonth().getValue()) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:

                if ((fecha.getDayOfMonth() != 1) && (fecha.getDayOfMonth() != 16) && (fecha.getDayOfMonth() != 17))
                    throw new Exception("verifique la fecha de la quincena ya que debe comenzar el dia 1 16 o 17 para los mes con 31 dias");
                break;

            case 2:
            case 4:
            case 6:
            case 9:
            case 11:

                if ((fecha.getDayOfMonth() != 1) && (fecha.getDayOfMonth() != 16))
                    throw new Exception("verifique la fecha de la quincena ya que debe comenzar el dia 1  o 16 para los mes con menos de 30 dias");
                break;

        }
    }

    private static void validaQueLasFechasDeInicioDeLosDiasOpuestoComiencenCorrectamente(LocalDate fecha) throws Exception {
        switch (fecha.getMonth().getValue()) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                if ((fecha.getDayOfMonth() >= 6 && fecha.getDayOfMonth() <= 14) || (fecha.getDayOfMonth() >= 18))
                    throw new Exception("la fecha De las quincenas  debe comenzar entre el 1 y el 6 o entre el 16 y 22 para los meses con 31 ");
                break;

            case 2:

                if ((fecha.getDayOfMonth() >= 6 && fecha.getDayOfMonth() <= 14) || (fecha.getDayOfMonth() >= 19))
                    throw new Exception("la fecha De las quincenas  debe comenzar entre el 1 y el 6 o entre el 16 y 19 para el  mes de febrero ");
            case 4:
            case 6:
            case 9:
            case 11:

                if ((fecha.getDayOfMonth() >= 6 && fecha.getDayOfMonth() <= 14) || (fecha.getDayOfMonth() >= 22))
                    throw new Exception("la fecha De las quincenas  debe comenzar entre el 1 y el 6 o entre el 16 y 21 para los meses con 30 ");
                break;

        }
    }

    @Override
    public void guardar(PedidoVacaciones pedidoVacaciones) {
        pedidoVacacionesRepository.save(pedidoVacaciones);
    }

    @Override
    public List<PedidoVacaciones> findAll() {
        return pedidoVacacionesRepository.findAllByOrderByCicloVacacionesDescFechaDeSolicitudDescEstadoPedidoDesc();
    }

    @Override
    public PedidoVacaciones buscarPedidoVacacionesPorId(Long id) {

        return this.pedidoVacacionesRepository.findPedidoVacacionesById(id);


    }


    @Override
    public List<PedidoVacaciones> obtenerPedidosDeVacacionQueTieneUnLiderParaAnalizar(User lider) {
        return this.pedidoVacacionesRepository.findAllByUser_LiderOrderByFechaDeSolicitudDesc(lider);
    }

    @Override
    public List<PedidoVacaciones> findAllByUserAndEstadoPedidoOrderByFechaDeSolicitudDesc(User user, EstadoPedido estadoPedido) {
        return this.pedidoVacacionesRepository.findAllByUserAndEstadoPedidoOrderByFechaDeSolicitudDesc(user, estadoPedido);
    }

    @Override
    public List<PedidoVacaciones> findAllByUserAndEstadoPedido_Solicitado(User user) throws Exception {
        if (user.getId() != null) {
            return findAllByUserAndEstadoPedidoOrderByFechaDeSolicitudDesc(user, EstadoPedido.SOLICITADO);
        } else {
            throw new Exception("No existen pedidos con estado solicitado");
        }
    }

    @Override
    public List<PedidoVacaciones> findAllByUserAndEstadoPedido_Cerrado(User user) throws Exception {
        if (user.getId() != null) {
            return findAllByUserAndEstadoPedidoOrderByFechaDeSolicitudDesc(user, EstadoPedido.CERRADO);
        } else {
            throw new Exception("No existen pedidos con estado cerrado");
        }
    }

    @Override
    public List<PedidoVacaciones> findAllByUserAndEstadoPedido_Rechazado(User user) throws Exception {
        if (user.getId() != null) {
            return findAllByUserAndEstadoPedidoOrderByFechaDeSolicitudDesc(user, EstadoPedido.RECHAZADO);
        } else {
            throw new Exception("No existen pedidos con estado rechazado");
        }
    }

    @Override
    public List<PedidoVacaciones> findAllByUser(User user) {
        return this.pedidoVacacionesRepository.findAllByUser(user);
    }

    @Override
    public List<PedidoVacaciones> obtenerTodosLosPedidosParaUnNumeroDeCiclo(User user, Integer numeroDeCiclo) {
        return this.pedidoVacacionesRepository.findAllByUserAndCicloVacaciones_NumeroDeCiclo(user, numeroDeCiclo);
    }

    @Override
    public List<PedidoVacaciones> obtenerTodosLosPedidosPorEstadoYNumeroDeCiclo(User user, EstadoPedido estado, Integer ciclo) {
        return null;
    }


    @Override
    public List<PedidoVacaciones> obtenerPedidosDeVacacionQueTieneUnLiderParaAnalizarParaUnCiclo(User user, CicloVacaciones cicloVacaciones) {
        return null;
    }

    @Override
    public HabilitarVacaciones buscarHabilitarVacaciones() {
        HabilitarVacaciones habilitarVacacionesDTO = this.habilitarVacacionesRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO);
        return Objects.requireNonNullElseGet(habilitarVacacionesDTO, HabilitarVacaciones::new);
    }

    private boolean sePuedePedirVacaciones(HabilitarVacaciones habilitarVacaciones) {
        LocalDate fechaActual = LocalDate.now();


        LocalDate fechaDesde = habilitarVacaciones.getHabilitarPedidoDeVacacionesDesde();
        LocalDate fechaHasta = habilitarVacaciones.getHabilitarPedidoDeVacacionesHasta();

        return habilitarVacaciones.getHabilitar() &&
                !fechaActual.isBefore(fechaDesde) && !fechaActual.isAfter(fechaHasta);
    }

    @Override
    public boolean sePuedenPedirVacaciones() {
        HabilitarVacaciones habilitarVacacionesDTO = this.habilitarVacacionesRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO);


        return sePuedePedirVacaciones(habilitarVacacionesDTO);
    }

    public PedidoVacaciones buscarPedidoDeVacacionesPorIdDeCicloYPorUsuario(Long idCiclo, User user) {
        CicloVacaciones cicloVacaciones = this.cicloVacacionesService.getById(idCiclo);
        PedidoVacaciones pedidoVacaciones = this.pedidoVacacionesRepository.findPedidoVacacionesByUserAndCicloVacaciones(user, cicloVacaciones);

        return pedidoVacaciones != null ? pedidoVacaciones : buildDefaultPedidoVacaciones(user, cicloVacaciones);
    }


    private PedidoVacaciones buildDefaultPedidoVacaciones(User user, CicloVacaciones cicloVacaciones) {
        PedidoVacaciones pedidoVacaciones = new PedidoVacaciones();

        pedidoVacaciones.setUser(user);
        pedidoVacaciones.setCicloVacaciones(cicloVacaciones);

        pedidoVacaciones.setSeAprobofechaDesdePrimeraQuincenaPlanA(false);
        pedidoVacaciones.setSeAprobofechaDesdeSegundaQuincenaPlanA(false);
        pedidoVacaciones.setSeAprobofechaDesdeDiasOpuestosPlanA(false);

        pedidoVacaciones.setSeAprobofechaDesdePrimeraQuincenaPlanB(false);
        pedidoVacaciones.setSeAprobofechaDesdeSegundaQuincenaPlanB(false);
        pedidoVacaciones.setSeAprobofechaDesdeDiasOpuestosPlanB(false);

        return pedidoVacaciones;
    }

}
