package com.flybook.librovuelo.service;

import com.flybook.librovuelo.dto.ProgramacionTripulanteDTO;
import com.flybook.librovuelo.exceptions.GoogleUserNotAllowedException;
import com.flybook.librovuelo.model.EventoCalendario;
import com.flybook.librovuelo.model.ProgramacionTripulante;
import com.flybook.librovuelo.model.User;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface CalendarService {
    //public Boolean crearEvento(Calendar client, List<ProgramacionTripulanteDTO> programacionTripulanteDTOList);
    Boolean actualizarCalendario(Calendar client, List<ProgramacionTripulante> programacionTripulantes);

    void eliminarEventosProgramacionesTripulantes(Calendar client, List<ProgramacionTripulante> programacionTripulantes);

    void eliminarEventoEnElCalendario(Calendar client, String eventId);

    Set<Event> getEvents() throws IOException;

    void setEvents(Set<Event> events);

    Boolean verificarUsuario(String mail, User user) throws GoogleUserNotAllowedException;
}
