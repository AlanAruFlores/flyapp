package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.dto.CicloVacacionesOrdePorAnio;
import com.flybook.librovuelo.dto.ResumenDeVacaciones;
import com.flybook.librovuelo.dto.UsuarioOrdenPorLegajoAsc;
import com.flybook.librovuelo.model.*;
import com.flybook.librovuelo.repository.AusenciaRepository;
import com.flybook.librovuelo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AusenciaServiceImpl implements AusenciaService {

    private final AusenciaRepository ausenciaRepository;

    private final PeriodoService periodoService;

    private final CicloVacacionesService cicloVacacionesService;

    private final UserService userService;
    private final PedidoVacacionesService pedidoVacacionesService;

    @Autowired
    public AusenciaServiceImpl(AusenciaRepository ausenciaRepository, PeriodoService periodoService, CicloVacacionesService cicloVacacionesService, UserService userService, PedidoVacacionesService pedidoVacacionesService) {
        this.ausenciaRepository = ausenciaRepository;
        this.periodoService = periodoService;
        this.cicloVacacionesService = cicloVacacionesService;
        this.userService = userService;
        this.pedidoVacacionesService = pedidoVacacionesService;
    }

    public void guardarPedidoDeAusencia(Ausencia ausencia) {
        if (ausencia.getId() == null) {
            ausencia.setEstadoAusencia(EstadoAusencia.SOLICITADO);
        }
        if (ausencia.getFechaDesde() == null || ausencia.getFechaHasta() == null) {
            throw new IllegalArgumentException("Las fechas desde y hasta no pueden estar vacías.");
        }

        if (ausencia.getFechaHasta().isBefore(ausencia.getFechaDesde())) {
            throw new IllegalArgumentException("La fecha hasta no puede ser anterior a la fecha desde.");
        }

        ausenciaRepository.save(ausencia);
    }

    @Override
    public List<Ausencia> obtenerTodasLasAusenciasDeUnUsuarioEntreDosFechas(User user, LocalDate inicio, LocalDate finalizacion) {
        HashSet<Ausencia> ausencias = new HashSet<>();

        ausencias.addAll(this.ausenciaRepository.findAllByUserAndFechaDesdeIsLessThanAndFechaDesdeIsLessThanEqualAndFechaHastaIsGreaterThanEqualOrderByFechaDesdeAsc(user, inicio, finalizacion, finalizacion));
        ausencias.addAll(this.ausenciaRepository.findAllByUserAndFechaDesdeIsGreaterThanEqualAndFechaDesdeIsLessThanEqualAndFechaHastaIsGreaterThanEqualOrderByFechaDesdeAsc(user, inicio, finalizacion, finalizacion));
        ausencias.addAll(this.ausenciaRepository.findAllByUserAndFechaDesdeIsGreaterThanEqualAndFechaDesdeIsLessThanEqualAndFechaHastaIsLessThanOrderByFechaDesdeAsc(user, inicio, finalizacion, finalizacion));


        return new ArrayList<>(ausencias);

    }


    public Map<CicloVacaciones, List<Ausencia>> obtenerVacionesAgrupadasPorCiclosParaUnUsuario(User user) {

        Map<CicloVacaciones, List<Ausencia>> ausenciasVacacionesPorCicloParaUnUsuario = new TreeMap<>(new CicloVacacionesOrdePorAnio());

        List<CicloVacaciones> ciclosDeVacacionesDeUnUsuario = this.cicloVacacionesService.obtenerTodosLosCiclosParaUnaGeneracion(user.getGeneracion());

        List<TipoAusencia> vac15Op10 = new ArrayList<>();
        vac15Op10.add(TipoAusencia.VAC15);
        vac15Op10.add(TipoAusencia.OPUESTO10);

        for (CicloVacaciones cicloVacaciones : ciclosDeVacacionesDeUnUsuario) {
            List<Ausencia> ausenciasDeUnUsuarioParaUnCiclo = this.ausenciaRepository.findAllByUserAndFechaDesdeIsGreaterThanEqualAndFechaDesdeIsLessThanEqualAndTipoAusenciaInOrderByFechaDesde(user, cicloVacaciones.getComienzoCiclo(), cicloVacaciones.getFinalizacionCiclo(), vac15Op10);

            for (int i = ausenciasDeUnUsuarioParaUnCiclo.size(); i < 3; i++) {
                ausenciasDeUnUsuarioParaUnCiclo.add(new Ausencia());


            }

            ausenciasVacacionesPorCicloParaUnUsuario.put(cicloVacaciones, ausenciasDeUnUsuarioParaUnCiclo);

        }
        return ausenciasVacacionesPorCicloParaUnUsuario;
    }


    @Override
    public Map<User, Map<CicloVacaciones, List<Ausencia>>> obtenerVacionesAgrupadasPorCiclosParaVariosUsuarios(List<User> users) {

        Map<User, Map<CicloVacaciones, List<Ausencia>>> ausenciasVacacionesAgrupadasPorUsuarioYPorCicloVacaciones = new TreeMap<>(new UsuarioOrdenPorLegajoAsc());

        for (User user : users) {
            Map<CicloVacaciones, List<Ausencia>> ciclosVacaciones = this.obtenerVacionesAgrupadasPorCiclosParaUnUsuario(user);
            ausenciasVacacionesAgrupadasPorUsuarioYPorCicloVacaciones.put(user, ciclosVacaciones);
        }
        return ausenciasVacacionesAgrupadasPorUsuarioYPorCicloVacaciones;
    }

    @Override
    public Map<User, Map<CicloVacaciones, List<Ausencia>>> obtenerVacacionesDeUnUsuarioAgrupadasCiclos(User user) {

        Map<User, Map<CicloVacaciones, List<Ausencia>>> ausenciasVacacionesAgrupadasPorUsuarioYPorCicloVacaciones = new TreeMap<>(new UsuarioOrdenPorLegajoAsc());


        Map<CicloVacaciones, List<Ausencia>> ciclosVacaciones = this.obtenerVacionesAgrupadasPorCiclosParaUnUsuario(user);
        ausenciasVacacionesAgrupadasPorUsuarioYPorCicloVacaciones.put(user, ciclosVacaciones);

        return ausenciasVacacionesAgrupadasPorUsuarioYPorCicloVacaciones;
    }

    @Override
    public List<Ausencia> todasLasVacacionesentreFechas(LocalDate fechaDesde, LocalDate fechaHasta) {
        List<TipoAusencia> ausenciasDeVacaciones = new ArrayList<>();
        ausenciasDeVacaciones.add(TipoAusencia.VAC15);
        ausenciasDeVacaciones.add(TipoAusencia.OPUESTO10);

        return this.ausenciaRepository.findAllByFechaDesdeIsGreaterThanEqualAndFechaDesdeIsLessThanEqualAndTipoAusenciaInOrderByFechaDesde(fechaDesde, fechaHasta, ausenciasDeVacaciones);

    }


    @Override
    public void validarVacacion(Ausencia vacacion) throws Exception {
        ValidarQueNoSupereLaCantidadDeVacaciooesPermitida(vacacion);
        ValidarSiYaexisteVAcacionParaElPeridoDeLaVacaciom(vacacion);
        validarQueNoSupereLaCantidadDeCuposMaximosEnUnPeriodoSegunCargo(vacacion);
        validarQueSIUnaVcacionParaELMismoMes(vacacion);
        validarQueNoTengaUnaLicenciaAprobada(vacacion);

    }

    private void validarQueNoTengaUnaLicenciaAprobada(Ausencia vacacion) throws Exception {
        if (this.obtenerTodasLasAusenciasDeUnUsuarioEntreDosFechas(vacacion.getUser(), vacacion.getFechaDesde(), vacacion.getFechaHasta()).size() > 0)

            throw new Exception("No se puede asignar la Vacacion Ya que exise una ausencia aprobada para las fechas  " + vacacion.getFechaDesde() + " hasta  " + vacacion.getFechaHasta());

    }

    private void validarQueSIUnaVcacionParaELMismoMes(Ausencia vacacion) throws Exception {

        LocalDate PrimerDiaDelMes = vacacion.getFechaDesde().with(TemporalAdjusters.firstDayOfMonth());
        LocalDate ultimoDiaDelMes = vacacion.getFechaHasta().with(TemporalAdjusters.lastDayOfMonth());
        List<Ausencia> vacacionesYaAsiganada = this.obtenerVacacionesDeUnUsuarioPAraUnRangoDeFecha(vacacion.getUser(), PrimerDiaDelMes, ultimoDiaDelMes);
        if (vacacionesYaAsiganada.size() > 0)
            throw new Exception("No se puede asignar la Vacacion Ya que exise una vacacion para el mes  " + vacacion.getFechaDesde().getMonth().getValue() + " y anio " + vacacion.getFechaHasta().getYear());

    }

    private void validarQueNoSupereLaCantidadDeCuposMaximosEnUnPeriodoSegunCargo(Ausencia vacacion) throws Exception {
        Periodo periodo = this.periodoService.obtenerElPeriodoParaUnaFecha(vacacion.getFechaDesde());
        // Integer cantidadDeVacacionesYaAsignadasParaElPeriodo =obtenerCantidadDeVacionesAsiganadasENUnpeRiodoParaUnaFecha(periodo.getFechaInicio());
        Integer cantidadDeVacacionesYaAsignadasParaElPeriodo = obtenerCantidadDeVacionesAsiganadasENUnpeRiodoParaUnaFechayUnCargo(periodo.getFechaInicio(), vacacion.getUser().getTipoCargo());

        if (periodo.getCantidadCuposPorCargo(vacacion.getUser().getTipoCargo()).equals(cantidadDeVacacionesYaAsignadasParaElPeriodo))

            throw new Exception("No se puede asignarVacacioes Ya que Supera el cupo maximo permitido para la fecha " + vacacion.getFechaDesde().toString() + " a " + vacacion.getFechaHasta().toString());

    }

    public Integer obtenerCantidadDeVacionesAsiganadasENUnpeRiodoParaUnaFechayUnCargo(LocalDate fecha, TipoCargo tipoCargo) {

        Periodo periodo = this.periodoService.obtenerElPeriodoParaUnaFecha(fecha);
        List<Ausencia> ausencias = this.todasLasVacacionesentreFechas(periodo.getFechaInicio(), periodo.getFechaInicio());
//        return ausencias.size();
        List<TipoAusencia> ausenciasDeVacaciones = new ArrayList<>();
        ausenciasDeVacaciones.add(TipoAusencia.VAC15);
        ausenciasDeVacaciones.add(TipoAusencia.OPUESTO10);

        Integer opcion1 = this.ausenciaRepository.findAllByFechaDesdeIsGreaterThanEqualAndFechaDesdeIsLessThanEqualAndTipoAusenciaInAndUser_TipoCargoOrderByFechaDesde(periodo.getFechaInicio(), periodo.getFechaInicio(), ausenciasDeVacaciones, tipoCargo).size();
        Integer opcion2 = ausencias.stream().filter(t -> t.getUser().getTipoCargo().equals(tipoCargo)).collect(Collectors.toList()).size();

        return ausencias.stream().filter(t -> t.getUser().getTipoCargo().equals(tipoCargo)).collect(Collectors.toList()).size();


    }


    @Override
    public Integer obtenerCantidadDeVacionesAsiganadasENUnpeRiodoParaUnaFecha(LocalDate fecha) {

        Periodo periodo = this.periodoService.obtenerElPeriodoParaUnaFecha(fecha);
        List<Ausencia> ausencias = this.todasLasVacacionesentreFechas(periodo.getFechaInicio(), periodo.getFechaInicio());
        return ausencias.size();
    }

    @Override
    public ResumenDeVacaciones generarResumenAusencia(PedidoVacaciones pedido, LocalDate fecha_inicio, LocalDate fecha_final, String tipoAusencia) throws Exception {

        ResumenDeVacaciones resumenDeVacaciones = new ResumenDeVacaciones();

        resumenDeVacaciones.setUserId(pedido.getUser().getId());
        LocalDate fechaDesde = obtenerFechaDesde(fecha_inicio, pedido);
        resumenDeVacaciones.setFechaInicioAusencia(fechaDesde);
        LocalDate fechaHasta = obtenerFechaHasta(fecha_final, pedido);
        resumenDeVacaciones.setFechaFinalAusencia(fechaHasta);
        resumenDeVacaciones.setTipoAusencia(TipoAusencia.valueOf(tipoAusencia));
        resumenDeVacaciones.setLegajo(pedido.getUser().getLegajo());
        resumenDeVacaciones.setNombre(pedido.getUser().getNombre());
        resumenDeVacaciones.setApellido(pedido.getUser().getApellido());
        resumenDeVacaciones.setPedidoId(pedido.getId());
        return resumenDeVacaciones;
    }

    @Override
    public ResumenDeVacaciones generarResumenAusenciaDesdesugerencia(Periodo periodo, PedidoVacaciones pedido, LocalDate fecha_inicio, LocalDate fecha_final, String tipoAusencia) {

        ResumenDeVacaciones resumenDeVacaciones = new ResumenDeVacaciones();
        resumenDeVacaciones.setUserId(pedido.getUser().getId());
        resumenDeVacaciones.setFechaInicioAusencia(fecha_inicio);
        resumenDeVacaciones.setFechaFinalAusencia(fecha_final);
        resumenDeVacaciones.setTipoAusencia(TipoAusencia.valueOf(tipoAusencia));
        resumenDeVacaciones.setLegajo(pedido.getUser().getLegajo());
        resumenDeVacaciones.setNombre(pedido.getUser().getNombre());
        resumenDeVacaciones.setApellido(pedido.getUser().getApellido());
        resumenDeVacaciones.setPedidoId(pedido.getId());


        return resumenDeVacaciones;
    }

    @Override
    public Ausencia obtenerAusenciaRecurrentDeUnUsuarioEntreDosFechas(User user, LocalDate fechaDesde, LocalDate fechaHasta) {
        TipoAusencia tipoAusencia = TipoAusencia.RECURRENT;
        return this.ausenciaRepository.findByUserAndFechaDesdeAndFechaHastaAndTipoAusencia(user, fechaDesde, fechaHasta, tipoAusencia);
    }

    @Override
    public void borrarAusencia(Ausencia ausenciaaBorrar) {
        this.ausenciaRepository.delete(ausenciaaBorrar);
    }

    @Override
    public void guardarVacaciones(ResumenDeVacaciones resumenDeVacaciones) throws Exception {
        Ausencia ausencia = new Ausencia();

        User user = this.userService.getById(resumenDeVacaciones.getUserId());

        ausencia.setUser(user);
        ausencia.setTipoAusencia(resumenDeVacaciones.getTipoAusencia());
        ausencia.setFechaDesde(resumenDeVacaciones.getFechaInicioAusencia());
        ausencia.setFechaHasta(resumenDeVacaciones.getFechaFinalAusencia());
        ausencia.setEstadoAusencia(EstadoAusencia.SOLICITADO);
        aprobarPlanDeVacacion(resumenDeVacaciones);
        this.ausenciaRepository.save(ausencia);

    }

    @Override
    public List<Ausencia> buscarTodasLasAusenciasOperacionesParams(Map<String, Object> params) {
        LocalDate fechaDesde = !siElCampoEstaVacio(params,"fechaDesde") ? LocalDate.parse((String)params.get("fechaDesde")) : LocalDate.of(1900,1,1);
        LocalDate fechaHasta = !siElCampoEstaVacio(params,"fechaHasta") ? LocalDate.parse((String)params.get("fechaHasta")) : LocalDate.of(2200,1,1);

        //Si no me manda nada , entonces:
        if(siElCampoEstaVacio(params,"valor")  && siElCampoEstaVacio(params, "tipoAusencia"))
            return this.buscarTodasLasAusencias(fechaDesde,fechaHasta);

        TipoAusencia tipoAusencia = (params.get("tipoAusencia")) != "" ? TipoAusencia.valueOf(params.get("tipoAusencia").toString()): null;

        if(siElCampoEstaVacio(params,"valor")  && !siElCampoEstaVacio(params, "tipoAusencia"))
            return this.ausenciaRepository.findAllByTipoAusenciaAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(tipoAusencia, fechaDesde, fechaHasta);

        //Si es que manda a filtrar
        String atributoAFiltrar = (String) params.getOrDefault("atributo", "");
        String valorAFiltrar = (String) params.getOrDefault("valor", "");

        List<Ausencia> listaAusencias = switch (atributoAFiltrar){
            case "legajo" -> (tipoAusencia == null) ?
                    this.ausenciaRepository.findAllByUser_LegajoAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(Integer.parseInt(valorAFiltrar),fechaDesde,fechaHasta):
                    this.ausenciaRepository.findAllByUser_LegajoAndTipoAusenciaAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(Integer.parseInt(valorAFiltrar), tipoAusencia,fechaDesde, fechaHasta);

            case "dni" -> (tipoAusencia == null) ?
                    this.ausenciaRepository.findAllByUser_DniAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(Long.parseLong(valorAFiltrar),fechaDesde, fechaHasta):
                    this.ausenciaRepository.findAllByUser_DniAndTipoAusenciaAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(Long.parseLong(valorAFiltrar), tipoAusencia,fechaDesde, fechaHasta);


            case "nombre" -> (tipoAusencia==null)?
                    this.ausenciaRepository.findAllByUser_NombreAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(valorAFiltrar,fechaDesde, fechaHasta):
                    this.ausenciaRepository.findAllByUser_NombreAndTipoAusenciaAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(valorAFiltrar, tipoAusencia,fechaDesde, fechaHasta);


            case "generacion" -> (tipoAusencia == null) ?
                    this.ausenciaRepository.findAllByUser_Generacion_NumeroAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(Integer.parseInt(valorAFiltrar),fechaDesde,fechaHasta):
                    this.ausenciaRepository.findAllByUser_Generacion_NumeroAndTipoAusenciaAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(Integer.parseInt(valorAFiltrar), tipoAusencia,fechaDesde,fechaHasta);

            default-> new ArrayList<>();
        };

        return listaAusencias;
    }


    private Boolean siElCampoEstaVacio(Map<String, Object> params, String campo){
        return params.get(campo) == null || params.get(campo).toString().isEmpty() || params.get(campo).toString().isBlank();
    }

    @Override
    public List<Ausencia> buscarTodasLasAusenciasACargoDelLideroParams(User lider, Map<String, Object> params) {

        LocalDate fechaDesde = !siElCampoEstaVacio(params,"fechaDesde") ? LocalDate.parse((String)params.get("fechaDesde")) : LocalDate.of(1900,1,1);
        LocalDate fechaHasta = !siElCampoEstaVacio(params,"fechaHasta") ? LocalDate.parse((String)params.get("fechaHasta")) : LocalDate.of(2200,1,1);

        //Si no me manda nada , entonces:
        if(siElCampoEstaVacio(params,"valor")  && siElCampoEstaVacio(params, "tipoAusencia"))
            return this.buscarTodasLasAusenciasACargoDelLider(lider,fechaDesde,fechaHasta);

        TipoAusencia tipoAusencia = (params.get("tipoAusencia")) != "" ? TipoAusencia.valueOf(params.get("tipoAusencia").toString()): null;

        if(siElCampoEstaVacio(params,"valor")  && !siElCampoEstaVacio(params, "tipoAusencia"))
            return this.ausenciaRepository.findAllByUser_Lider_IdAndTipoAusenciaAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(lider.getId(), tipoAusencia,fechaDesde,fechaHasta);


        //Si es que manda a filtrar
        String atributoAFiltrar = (String) params.getOrDefault("atributo", "");
        String valorAFiltrar = (String) params.getOrDefault("valor", "");

        List<Ausencia> listaAusencias = switch (atributoAFiltrar){
            case "legajo" -> (tipoAusencia == null) ?
                    this.ausenciaRepository.findAllByUser_Lider_IdAndUser_LegajoAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(lider.getId(), Integer.parseInt(valorAFiltrar),fechaDesde, fechaHasta):
                    this.ausenciaRepository.findAllByUser_Lider_IdAndUser_LegajoAndTipoAusenciaAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(lider.getId(), Integer.parseInt(valorAFiltrar), tipoAusencia, fechaDesde,fechaHasta);

            case "dni" -> (tipoAusencia == null) ?
                    this.ausenciaRepository.findAllByUser_Lider_IdAndUser_DniAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(lider.getId(), Long.parseLong(valorAFiltrar), fechaDesde, fechaHasta) :
                    this.ausenciaRepository.findAllByUser_Lider_IdAndUser_DniAndTipoAusenciaAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(lider.getId(), Long.parseLong(valorAFiltrar), tipoAusencia,fechaDesde, fechaHasta);

            case "nombre" -> (tipoAusencia == null) ?
                    this.ausenciaRepository.findAllByUser_Lider_IdAndUser_NombreAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(lider.getId(), valorAFiltrar,fechaDesde, fechaHasta):
                    this.ausenciaRepository.findAllByUser_Lider_IdAndUser_NombreAndTipoAusenciaAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(lider.getId(), valorAFiltrar, tipoAusencia,fechaDesde,fechaHasta);


            case "generacion" -> (tipoAusencia == null) ?
                    this.ausenciaRepository.findAllByUser_Lider_IdAndUser_Generacion_NumeroAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(lider.getId(), Integer.parseInt(valorAFiltrar),fechaDesde,fechaHasta) :
                    this.ausenciaRepository.findAllByUser_Lider_IdAndUser_Generacion_NumeroAndTipoAusenciaAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(lider.getId(), Integer.parseInt(valorAFiltrar), tipoAusencia,fechaDesde, fechaHasta);

            default-> new ArrayList<>();
        };

        return listaAusencias;
    }

    @Override
    public List<Ausencia> buscarTodasLasAusenciasDeUnUsuario(User user) {
        return this.ausenciaRepository.findAllByUser(user);
    }

    @Override
    public List<Ausencia> buscarTodasLasAusenciasDeUnUsuarioPorTipoAusencia(User user, TipoAusencia tipoAusencia) {
        return this.ausenciaRepository.findAllByUserAndTipoAusencia(user,tipoAusencia);
    }

    @Override
    public List<Ausencia> filtrarAusenciasPorTipoParaUnUsuario(User user, Map<String, Object> params) {
        List<Ausencia> ausencias;
        String value = (String) params.getOrDefault("tipoVacacion", "todos");

        ausencias = switch (value) {
            case "VAC15" -> buscarTodasLasAusenciasDeUnUsuarioPorTipoAusencia(user, TipoAusencia.VAC15);
            case "OPUESTO10" -> buscarTodasLasAusenciasDeUnUsuarioPorTipoAusencia(user, TipoAusencia.OPUESTO10);
            case "ENFERMEDAD" -> buscarTodasLasAusenciasDeUnUsuarioPorTipoAusencia(user, TipoAusencia.ENFERMEDAD);
            case "ACCIDENTE" -> buscarTodasLasAusenciasDeUnUsuarioPorTipoAusencia(user, TipoAusencia.ACCIDENTE);
            case "EMBARAZO" -> buscarTodasLasAusenciasDeUnUsuarioPorTipoAusencia(user, TipoAusencia.EMBARAZO);
            case "RECURRENT" -> buscarTodasLasAusenciasDeUnUsuarioPorTipoAusencia(user, TipoAusencia.RECURRENT);
            case "TODOS" -> buscarTodasLasAusenciasDeUnUsuario(user);
            default -> buscarTodasLasAusenciasDeUnUsuario(user);
        };

        return ausencias;
    }

    @Override
    public Ausencia buscarAusenciaPorId(Long id) {
      //  findPedidoVacacionesById(id)
        return this.ausenciaRepository.findAusenciaById(id);
//        return this.ausenciaRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Ausencia> buscarTodasLasAusencias() {
        return this.ausenciaRepository.findAll();
    }

    @Override
    public List<Ausencia> buscarTodasLasAusencias(LocalDate desde, LocalDate hasta) {
        return this.ausenciaRepository.findAllByFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(desde,hasta);
    }

    @Override
    public List<Ausencia> buscarTodasLasAusenciasPorLider(User user) {
        User lider = this.userService.findUserById(user.getLider().getId());
        return ausenciaRepository.findByUserLider(lider);
    }

    @Override
    public List<Ausencia> buscarTodasLasAusenciasACargoDelLider(User lider, LocalDate fechaDesde, LocalDate fechaHasta){
        return this.ausenciaRepository.findAllByUser_Lider_IdAndFechaDesdeGreaterThanEqualAndFechaDesdeLessThanEqual(lider.getId(), fechaDesde, fechaHasta);
    }

    @Override
    public void aprobarAusencia(Ausencia ausencia) {
        ausencia.setEstadoAusencia(EstadoAusencia.APROBADA);
        this.ausenciaRepository.save(ausencia);
    }

    @Override
    public void desaprobarAusencia(Ausencia ausencia) {
        ausencia.setEstadoAusencia(EstadoAusencia.CANCELADA);
        this.ausenciaRepository.save(ausencia);
    }


    @Override
    public void cambiarEstadoDeAusenciaPorDecisionDelLider(EstadoAusencia estadoAusencia, Ausencia ausencia) {
        if (estadoAusencia.equals(EstadoAusencia.APROBADA)) {
            aprobarAusencia(ausencia);
        }
        if (estadoAusencia.equals(EstadoAusencia.CANCELADA)) {
            desaprobarAusencia(ausencia);
        }
    }

    private void aprobarPlanDeVacacion(ResumenDeVacaciones resumenDeVacaciones) throws Exception {
        PedidoVacaciones pedidoVacaciones = this.pedidoVacacionesService.buscarPedidoVacacionesPorId(resumenDeVacaciones.getPedidoId());

        if (pedidoVacaciones.getFechaDesdePrimeraQuincenaPlanA().equals(resumenDeVacaciones.getFechaInicioAusencia())) {
            pedidoVacaciones.setSeAprobofechaDesdePrimeraQuincenaPlanA(true);
        }

        if (pedidoVacaciones.getFechaDesdeSegundaQuincenaPlanA().equals(resumenDeVacaciones.getFechaInicioAusencia())) {
            pedidoVacaciones.setSeAprobofechaDesdeSegundaQuincenaPlanA(true);
        }

        if (pedidoVacaciones.getFechaDesdeDiasOpuestosPlanA().equals(resumenDeVacaciones.getFechaInicioAusencia())) {
            pedidoVacaciones.setSeAprobofechaDesdeDiasOpuestosPlanA(true);
        }

        if (pedidoVacaciones.getFechaDesdePrimeraQuincenaPlanB().equals(resumenDeVacaciones.getFechaInicioAusencia())) {
            pedidoVacaciones.setSeAprobofechaDesdePrimeraQuincenaPlanB(true);
        }

        if (pedidoVacaciones.getFechaDesdeSegundaQuincenaPlanB().equals(resumenDeVacaciones.getFechaInicioAusencia())) {
            pedidoVacaciones.setSeAprobofechaDesdeSegundaQuincenaPlanB(true);
        }

        if (pedidoVacaciones.getFechaDesdeDiasOpuestosPlanB().equals(resumenDeVacaciones.getFechaInicioAusencia())) {
            pedidoVacaciones.setSeAprobofechaDesdeDiasOpuestosPlanB(true);
        }

        this.pedidoVacacionesService.save(pedidoVacaciones);
    }


    private void ValidarSiYaexisteVAcacionParaElPeridoDeLaVacaciom(Ausencia vacacion) throws Exception {
        List<Ausencia> vacacionesYaAsiganada = this.obtenerVacacionesDeUnUsuarioPAraUnRangoDeFecha(vacacion.getUser(), vacacion.getFechaDesde(), vacacion.getFechaHasta());
        if (vacacionesYaAsiganada.size() > 0)

            throw new Exception("Ya tiene una vacacion asiganda para la fecha " + vacacion.getFechaDesde().toString() + " a " + vacacion.getFechaHasta().toString());
    }


    @Override
    public List<Ausencia> obtenerVacacionesDeUnUsuarioPAraUnRangoDeFecha(User user, LocalDate fechaDesde, LocalDate fechaHasta) {
        List<TipoAusencia> tipoAusencias = new ArrayList<>();
        tipoAusencias.add(TipoAusencia.VAC15);
        tipoAusencias.add(TipoAusencia.OPUESTO10);
        return ausenciaRepository.findAllByUserAndFechaDesdeIsGreaterThanEqualAndFechaDesdeIsLessThanEqualAndTipoAusenciaInOrderByFechaDesde(user, fechaDesde, fechaHasta, tipoAusencias);
    }

    private void ValidarQueNoSupereLaCantidadDeVacaciooesPermitida(Ausencia vacacion) throws Exception {

        CicloVacaciones cicloVacaciones = this.cicloVacacionesService.obtenerCicloDeVacacionesParaUnageneracionYUnaFechaDada(vacacion.getUser().getGeneracion(), vacacion.getFechaDesde());


        int cantidadDeVaca15 = 0;
        if (vacacion.getTipoAusencia().equals(TipoAusencia.VAC15)) {
            cantidadDeVaca15 = this.obtenerVacacionesDeUnUsuarioPAraUnRangoDeFechaPorTipoVacaciones(vacacion.getUser(), cicloVacaciones.getComienzoCiclo(), cicloVacaciones.getFinalizacionCiclo(), TipoAusencia.VAC15).size();

            if (cantidadDeVaca15 >= 2)
                throw new Exception("Ya Posee 2Vacaciones de 15 dias para el ciclo " + cicloVacaciones.getNumeroDeCiclo() + " " + cicloVacaciones.getNumeroDeCiclo().toString() + "-" + cicloVacaciones.getFinalizacionCiclo().toString());

        }

        int cantidadDeOpuesto10 = 0;
        if (vacacion.getTipoAusencia().equals(TipoAusencia.OPUESTO10)) {
            cantidadDeOpuesto10 = this.obtenerVacacionesDeUnUsuarioPAraUnRangoDeFechaPorTipoVacaciones(vacacion.getUser(), cicloVacaciones.getComienzoCiclo(), cicloVacaciones.getFinalizacionCiclo(), TipoAusencia.OPUESTO10).size();

            if (cantidadDeOpuesto10 >= 1)
                throw new Exception("Ya Posee Los dias opuestos  para el ciclo " + cicloVacaciones.getNumeroDeCiclo() + " " + cicloVacaciones.getNumeroDeCiclo().toString() + "-" + cicloVacaciones.getFinalizacionCiclo().toString());

        }

    }


    @Override
    public List<Ausencia> obtenerVacacionesDeUnUsuarioPAraUnRangoDeFechaPorTipoVacaciones(User user, LocalDate fechaDesde, LocalDate fechaHasta, TipoAusencia tipoAusencia) {
        return this.ausenciaRepository.findAllByUserAndFechaDesdeIsGreaterThanEqualAndFechaHastaIsLessThanEqualAndTipoAusenciaOrderByFechaDesde(user, fechaDesde, fechaHasta, tipoAusencia);
    }

    private LocalDate obtenerFechaDesde(LocalDate fecha_inicio, PedidoVacaciones pedido) throws Exception {
        LocalDate date = fecha_inicio;
        if (pedido.getFechaDesdePrimeraQuincenaPlanA().equals(date)) {
            return date;
        }
        if (pedido.getFechaDesdeSegundaQuincenaPlanA().equals(date)) {
            return date;
        }
        if (pedido.getFechaDesdeDiasOpuestosPlanA().equals(date)) {
            return date;
        }
        if (pedido.getFechaDesdePrimeraQuincenaPlanB().equals(date)) {
            return date;
        }
        if (pedido.getFechaDesdeSegundaQuincenaPlanB().equals(date)) {
            return date;
        }
        if (pedido.getFechaDesdeDiasOpuestosPlanB().equals(date)) {
            return date;
        }
        throw new Exception("La fecha inicio no existe en el pedido");
    }

    private LocalDate obtenerFechaHasta(LocalDate fecha_final, PedidoVacaciones pedido) throws Exception {
        LocalDate date = fecha_final;
        if (pedido.getFechaHastaPrimeraQuincenaPlanA().equals(date)) {
            return date;
        }
        if (pedido.getFechaHastaSegundaQuincenaPlanA().equals(date)) {
            return date;
        }
        if (pedido.getFechaHastaDiasOpuestosPlanA().equals(date)) {
            return date;
        }
        if (pedido.getFechaHastaPrimeraQuincenaPlanB().equals(date)) {
            return date;
        }
        if (pedido.getFechaHastaSegundaQuincenaPlanB().equals(date)) {
            return date;
        }
        if (pedido.getFechaHastaDiasOpuestosPlanB().equals(date)) {
            return date;
        }
        throw new Exception("La fecha fin no existe en el pedido");
    }


}
