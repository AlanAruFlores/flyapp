package com.flybook.librovuelo.service;

import com.flybook.librovuelo.dto.ProgramacionTripulanteDTO;
import com.flybook.librovuelo.model.ProgramacionTripulante;
import com.flybook.librovuelo.model.User;

import java.util.List;
import java.util.Map;

public interface ProgramacionTripulanteService {
    List<ProgramacionTripulante> getProgramacionTripulanteByTripulante(User tripulante);

    ProgramacionTripulante getProgramacionTripulanteById(Long id);

    void saveProgramacionTripulante(ProgramacionTripulante programacionTripulante);

    ProgramacionTripulanteDTO convertirProgramacionTripulanteASuDTO(ProgramacionTripulante programacionTripulante);

    ProgramacionTripulante convertirProgramacionTripulanteDTOASuEntidad(ProgramacionTripulanteDTO programacionTripulanteDTO);

    ProgramacionTripulanteDTO  convertirAProgramacionTripulanteDTO(List<Object> data);

    List<ProgramacionTripulanteDTO> convertirAListaProgramacionTripulanteDTO(List<List<Object>> data);

    ProgramacionTripulante convertirDtoAProgramacionTripulanteConUsuario(ProgramacionTripulanteDTO dto);

    List<ProgramacionTripulante> convertirListaDtosAListaProgramacionTripulanteConUsuario(List<ProgramacionTripulanteDTO> dtos, User user);

    void saveListaProgramacionTripulantes(List<ProgramacionTripulante> listaProgTrip);

    void deleteProgramacionTripulantes(Long id);

    List<ProgramacionTripulante> filtrarPorFechasProgramacionTripulante(Map<String, Object> params, User tripulante);
}
