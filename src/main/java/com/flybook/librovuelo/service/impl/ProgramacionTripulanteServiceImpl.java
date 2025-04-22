package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.dto.ProgramacionTripulanteDTO;
import com.flybook.librovuelo.model.ProgramacionTripulante;
import com.flybook.librovuelo.model.TipoActividad;
import com.flybook.librovuelo.model.User;
import com.flybook.librovuelo.repository.AeropuertoRepository;
import com.flybook.librovuelo.repository.ProgramacionTripulanteRepository;
import com.flybook.librovuelo.service.ProgramacionTripulanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ProgramacionTripulanteServiceImpl implements ProgramacionTripulanteService {

    @Autowired
    private ProgramacionTripulanteRepository programacionTripulanteRepository;

    @Autowired
    private AeropuertoRepository  aeropuertoRepository;

    @Override
    public List<ProgramacionTripulante> getProgramacionTripulanteByTripulante(User tripulante) {
        return this.programacionTripulanteRepository.findByTripulante(tripulante);
    }

    @Override
    public ProgramacionTripulante getProgramacionTripulanteById(Long id){
        return this.programacionTripulanteRepository.findById(id).get();
    }

    @Override
    public void saveProgramacionTripulante(ProgramacionTripulante programacionTripulante) {
        this.programacionTripulanteRepository.save(programacionTripulante);
    }



    private String convertirFormatoOriginalAFormatoFecha(String fechaOriginal)
    {
        SimpleDateFormat originalFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = "";

        try {
            // Parse the input date string into a Date object
            Date date = originalFormat.parse(fechaOriginal);
            // Format the Date object into the target format
            formattedDate = targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return formattedDate;
    }

    private String extraerAerpuertoCodigo(String codigo){
        int beginIndex =  codigo.indexOf("(");
        int endIndex = codigo.lastIndexOf(")");

        String codigoExtraido = codigo.substring(beginIndex+1, endIndex);
        return codigoExtraido;
    }

    @Override
    public ProgramacionTripulanteDTO convertirProgramacionTripulanteASuDTO(ProgramacionTripulante programacionTripulante){
        ProgramacionTripulanteDTO dto  = new ProgramacionTripulanteDTO();
        dto.id = programacionTripulante.getId();
        dto.tripulante = programacionTripulante.getTripulante();
        dto.aeropuertoOrigen = programacionTripulante.getAeropuertoOrigen();
        dto.aeropuertoDestino = programacionTripulante.getAeropuertoDestino();
        dto.fechaPresentacion = programacionTripulante.getFechaHoraPresentacion().toLocalDate();
        dto.horaPresentacion = programacionTripulante.getFechaHoraPresentacion().toLocalTime();
        dto.fechaDespegue = programacionTripulante.getFechaHoraPresentacion().toLocalDate();
        dto.horaDespegue = programacionTripulante.getFechaHoraDespegue().toLocalTime();
        dto.fechaDestino = programacionTripulante.getFechaHoraPresentacion().toLocalDate();
        dto.horaDestino = programacionTripulante.getFechaHoraDestino().toLocalTime();
        dto.diaActividad = programacionTripulante.getDiaActividad();
        dto.tipoActividad = programacionTripulante.getTipoActividad();
        dto.eventoCalendario = programacionTripulante.getEventoCalendario();
  //      dto.agendarCalendario = programacionTripulante.getEstaEnCalendar();
        return dto;

    }

    @Override
    public ProgramacionTripulante convertirProgramacionTripulanteDTOASuEntidad(ProgramacionTripulanteDTO programacionTripulanteDTO){
        ProgramacionTripulante programacionTripulante = new ProgramacionTripulante();
        programacionTripulante.setId(programacionTripulanteDTO.getId());
        programacionTripulante.setTripulante(programacionTripulanteDTO.getTripulante());
        programacionTripulante.setDiaActividad(programacionTripulanteDTO.getDiaActividad());
        programacionTripulante.setTipoActividad(programacionTripulanteDTO.getTipoActividad());
        programacionTripulante.setAeropuertoOrigen(programacionTripulanteDTO.getAeropuertoOrigen());
        programacionTripulante.setAeropuertoDestino(programacionTripulanteDTO.getAeropuertoDestino());
        programacionTripulante.setFechaHoraPresentacion(LocalDateTime.of(programacionTripulanteDTO.getFechaPresentacion(), programacionTripulanteDTO.getHoraPresentacion()));
        programacionTripulante.setFechaHoraDestino(LocalDateTime.of(programacionTripulanteDTO.getFechaDestino(), programacionTripulanteDTO.getHoraDestino()));
        programacionTripulante.setFechaHoraDespegue(LocalDateTime.of(programacionTripulanteDTO.getFechaDespegue(), programacionTripulanteDTO.getHoraDespegue()));
        programacionTripulante.setEventoCalendario(programacionTripulanteDTO.getEventoCalendario());
        //        programacionTripulante.setEstaEnCalendar(programacionTripulanteDTO.getAgendarCalendario());
        return programacionTripulante;
    }

    @Override
    public ProgramacionTripulanteDTO convertirAProgramacionTripulanteDTO(List<Object> data) {
        ProgramacionTripulanteDTO dto = new ProgramacionTripulanteDTO();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        dto.diaActividad = LocalDate.parse(convertirFormatoOriginalAFormatoFecha(data.get(0).toString()),formatter);
        dto.tipoActividad = devolverTipoActividad(data.get(1).toString());
        dto.aeropuertoOrigen = aeropuertoRepository.findByCodigo(extraerAerpuertoCodigo(data.get(2).toString()));
        dto.aeropuertoDestino = aeropuertoRepository.findByCodigo(extraerAerpuertoCodigo(data.get(3).toString()));
        dto.fechaPresentacion  = LocalDate.parse(convertirFormatoOriginalAFormatoFecha(data.get(4).toString()),formatter);
        dto.horaPresentacion = LocalTime.parse(formatearHora(data.get(5).toString()));
        dto.fechaDestino  = LocalDate.parse(convertirFormatoOriginalAFormatoFecha(data.get(6).toString()),formatter);
        dto.horaDestino = LocalTime.parse(formatearHora(data.get(7).toString()));
        dto.fechaDespegue  = LocalDate.parse(convertirFormatoOriginalAFormatoFecha(data.get(8).toString()),formatter);
        dto.horaDespegue = LocalTime.parse(formatearHora(data.get(9).toString()));
       // dto.agendarCalendario = Boolean.parseBoolean(data.get(10).toString());
        return dto;
    }

    private String formatearHora(String hora){
        LocalTime tiempo = LocalTime.parse(hora, DateTimeFormatter.ofPattern("H:mm:ss"));
        // Format the LocalTime object into the desired format (with two digits for the hour)
        return tiempo.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    @Override
    public List<ProgramacionTripulanteDTO> convertirAListaProgramacionTripulanteDTO(List<List<Object>> data) {
        List<ProgramacionTripulanteDTO> dtos = new ArrayList<>();
        data.forEach(d -> {
            dtos.add(convertirAProgramacionTripulanteDTO(d));
        });

        return dtos;
    }

    @Override
    public ProgramacionTripulante convertirDtoAProgramacionTripulanteConUsuario(ProgramacionTripulanteDTO dto){
        ProgramacionTripulante programacionTripulante = new ProgramacionTripulante();
        programacionTripulante.setTripulante(dto.tripulante);
        programacionTripulante.setAeropuertoOrigen(dto.aeropuertoOrigen);
        programacionTripulante.setAeropuertoDestino(dto.aeropuertoDestino);
        programacionTripulante.setDiaActividad(dto.diaActividad);
        programacionTripulante.setFechaHoraPresentacion(LocalDateTime.of(dto.fechaPresentacion,dto.horaPresentacion));
        programacionTripulante.setFechaHoraDestino(LocalDateTime.of(dto.fechaDestino,dto.horaDestino));
        programacionTripulante.setFechaHoraDespegue(LocalDateTime.of(dto.fechaDespegue,dto.horaDespegue));
        programacionTripulante.setTipoActividad(dto.tipoActividad);
    //    programacionTripulante.setEstaEnCalendar(dto.agendarCalendario);
        return programacionTripulante;
    }

    @Override
    public List<ProgramacionTripulante> convertirListaDtosAListaProgramacionTripulanteConUsuario(List<ProgramacionTripulanteDTO> dtos, User user){
        List<ProgramacionTripulante> listaProgTrip = new ArrayList<>();
        dtos.forEach(dto->{
            dto.setTripulante(user);
            listaProgTrip.add(convertirDtoAProgramacionTripulanteConUsuario(dto));
        });
        return listaProgTrip;

    }

    @Override
    public void saveListaProgramacionTripulantes(List<ProgramacionTripulante> listaProgTrip){
        listaProgTrip.forEach(progTrip->{
            saveProgramacionTripulante(progTrip);
        });
    }

    private TipoActividad devolverTipoActividad(String tipo){
        return switch (tipo) {
            case "NDA" -> TipoActividad.NDA;
            case "OP" -> TipoActividad.OP;
            default -> null;
        };
    }


    @Override
    public void deleteProgramacionTripulantes(Long id){
        this.programacionTripulanteRepository.deleteById(id);
    }

    private Boolean siElCampoEstaVacio(Map<String, Object> params, String campo){
        return params.get(campo) == null || params.get(campo).toString().isEmpty() || params.get(campo).toString().isBlank();
    }

    @Override
    public List<ProgramacionTripulante> filtrarPorFechasProgramacionTripulante(Map<String, Object> params,  User tripulante) {
        LocalDate fechaDesde = !siElCampoEstaVacio(params,"fechaDesde") ? LocalDate.parse((String)params.get("fechaDesde")) : LocalDate.of(1900,1,1);
        LocalDate fechaHasta = !siElCampoEstaVacio(params,"fechaHasta") ? LocalDate.parse((String)params.get("fechaHasta")) : LocalDate.of(2200,1,1);

        List<ProgramacionTripulante> programacionTripulantes = this.programacionTripulanteRepository.findAllByDiaActividadBetweenAndTripulante(fechaDesde, fechaHasta, tripulante);
        return programacionTripulantes;
    }


}
