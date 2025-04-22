package com.flybook.librovuelo.service;

import com.flybook.librovuelo.dto.ResumenDeVacaciones;
import com.flybook.librovuelo.model.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface AusenciaService {
    void guardarPedidoDeAusencia(Ausencia ausencia);

    List<Ausencia> obtenerTodasLasAusenciasDeUnUsuarioEntreDosFechas(User user,LocalDate inicio, LocalDate finalizacion);
    List<Ausencia> todasLasVacacionesentreFechas(LocalDate start, LocalDate end);

    void cambiarEstadoDeAusenciaPorDecisionDelLider(EstadoAusencia decision, Ausencia ausencia);

    List<Ausencia> obtenerVacacionesDeUnUsuarioPAraUnRangoDeFecha(User user, LocalDate fechaDesde, LocalDate fechaHasta);
//    List<Ausencia> obtnerAusenciasParaUnUsuarioEntreDosFechasParaUnaAusencia(LocalDate start, LocalDate end, TipoAusencia ausencia);
   // Map<CicloVacaciones, List<Ausencia> >obtenerVacionesAgrupadasPorCiclosParaUnUsuario(User user);
    Map<User, Map<CicloVacaciones, List<Ausencia>>> obtenerVacionesAgrupadasPorCiclosParaVariosUsuarios(List<User> users) ;
    Map<User, Map<CicloVacaciones, List<Ausencia>>> obtenerVacacionesDeUnUsuarioAgrupadasCiclos(User user);
    List<Ausencia> obtenerVacacionesDeUnUsuarioPAraUnRangoDeFechaPorTipoVacaciones(User user, LocalDate fechaDesde, LocalDate fechaHasta, TipoAusencia tipoAusencia);
   // Integer cantidadDeVacacionesAsignadasEntreDosFechas(LocalDate fechaDesde, LocalDate fechaHasta);
    void validarVacacion(Ausencia ausencia) throws Exception;
    Integer obtenerCantidadDeVacionesAsiganadasENUnpeRiodoParaUnaFecha(LocalDate fecha);

    Integer obtenerCantidadDeVacionesAsiganadasENUnpeRiodoParaUnaFechayUnCargo(LocalDate fecha, TipoCargo tipoCargo);
    ResumenDeVacaciones generarResumenAusencia(PedidoVacaciones pedido, LocalDate fecha_inicio, LocalDate fecha_final, String tipoAusencia) throws Exception;
    ResumenDeVacaciones generarResumenAusenciaDesdesugerencia(Periodo periodo, PedidoVacaciones pedido, LocalDate fecha_inicio, LocalDate fecha_final,String tipoAusencia);


    Ausencia obtenerAusenciaRecurrentDeUnUsuarioEntreDosFechas(User user, LocalDate fechaDesde, LocalDate fechaHasta);

    void borrarAusencia(Ausencia ausenciaaBorrar);
    void guardarVacaciones(ResumenDeVacaciones resumenDeVacaciones) throws Exception;


    List<Ausencia> buscarTodasLasAusenciasOperacionesParams(Map<String, Object> params);

    List<Ausencia> buscarTodasLasAusenciasACargoDelLideroParams(User user, Map<String, Object> params);

    List<Ausencia> buscarTodasLasAusenciasDeUnUsuario(User user);

    List<Ausencia> buscarTodasLasAusenciasDeUnUsuarioPorTipoAusencia(User user, TipoAusencia tipoAusencia);

    List<Ausencia> filtrarAusenciasPorTipoParaUnUsuario(User user, Map<String, Object> params);

    Ausencia buscarAusenciaPorId(Long id);

    List<Ausencia> buscarTodasLasAusencias();

    List<Ausencia> buscarTodasLasAusencias(LocalDate desde, LocalDate hasta);

    List<Ausencia> buscarTodasLasAusenciasPorLider(User user);

    List<Ausencia> buscarTodasLasAusenciasACargoDelLider(User lider, LocalDate fechaDesde, LocalDate fechaHasta);

    void aprobarAusencia(Ausencia ausencia);

    void desaprobarAusencia(Ausencia ausencia);


    // List <Ausencia> cantidadDeAusenciasTipoVAC15ParaUnMismoCiclo(Long userId, TipoAusencia tipoAusencia, LocalDate fechaDesde);

}
