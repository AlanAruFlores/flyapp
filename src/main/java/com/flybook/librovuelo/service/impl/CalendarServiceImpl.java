package com.flybook.librovuelo.service.impl;

import com.flybook.librovuelo.dto.ProgramacionTripulanteDTO;
import com.flybook.librovuelo.exceptions.GoogleUserNotAllowedException;
import com.flybook.librovuelo.model.EventoCalendario;
import com.flybook.librovuelo.model.ProgramacionTripulante;
import com.flybook.librovuelo.model.User;
import com.flybook.librovuelo.repository.EventoCalendarioRepository;
import com.flybook.librovuelo.repository.ProgramacionTripulanteRepository;
import com.flybook.librovuelo.repository.UserRepository;
import com.flybook.librovuelo.service.CalendarService;
import com.flybook.librovuelo.service.ProgramacionTripulanteService;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.EventReminder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CalendarServiceImpl implements CalendarService {
    private final static Log logger = LogFactory.getLog(CalendarServiceImpl.class);
    private Set<Event> events = new HashSet<>();

    private ProgramacionTripulanteService programacionTripulanteService;

    private EventoCalendarioRepository eventoCalendarioRepository;
    private ProgramacionTripulanteRepository programacionTripulanteRepository;
    private UserRepository userRepository;


    @Autowired
    public CalendarServiceImpl(EventoCalendarioRepository eventoCalendarioRepository, ProgramacionTripulanteService programacionTripulanteService, ProgramacionTripulanteRepository programacionTripulanteRepository, UserRepository userRepository){
        this.eventoCalendarioRepository = eventoCalendarioRepository;
        this.programacionTripulanteService =programacionTripulanteService;
        this.programacionTripulanteRepository = programacionTripulanteRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Boolean actualizarCalendario(Calendar client, List<ProgramacionTripulante> programacionTripulantes) {
       // EventoCalendario nuevoEventoCalendario = null;

        /*
        List<ProgramacionTripulante> programacionesAAgendar = programacionTripulantes.stream()
                .filter(pr-> pr.getEstaEnCalendar())
                .toList();
        */

         for(ProgramacionTripulante programacionTripulante : programacionTripulantes){
            ProgramacionTripulanteDTO programacionTripulanteDTO = this.programacionTripulanteService.convertirProgramacionTripulanteASuDTO(programacionTripulante);
            try{
                Event nuevoEvento = new Event()
                        .setSummary("Programacion: "+programacionTripulanteDTO.getAeropuertoOrigen().getNombre()+" - "+programacionTripulanteDTO.getAeropuertoDestino().getNombre())
                        .setLocation("Argentina")
                        .setDescription("No description");

                java.util.Calendar calendarInicio = java.util.Calendar.getInstance();
                calendarInicio.set(programacionTripulanteDTO.getFechaPresentacion().getYear(), programacionTripulanteDTO.getFechaPresentacion().getMonthValue()-1,
                        programacionTripulanteDTO.getFechaPresentacion().getDayOfMonth(), programacionTripulanteDTO.getHoraPresentacion().getHour(),
                        programacionTripulanteDTO.getHoraPresentacion().getMinute(),programacionTripulanteDTO.getHoraPresentacion().getSecond());
                DateTime inicio = new DateTime(calendarInicio.getTime());


                java.util.Calendar calendarFin = java.util.Calendar.getInstance();
                calendarFin.set(programacionTripulanteDTO.getFechaPresentacion().getYear(), programacionTripulanteDTO.getFechaPresentacion().getMonthValue()-1,
                        programacionTripulanteDTO.getFechaPresentacion().getDayOfMonth(), programacionTripulanteDTO.getHoraPresentacion().getHour() + 1,
                        programacionTripulanteDTO.getHoraPresentacion().getMinute(),programacionTripulanteDTO.getHoraPresentacion().getSecond());
                DateTime fin = new DateTime(calendarFin.getTime());

                nuevoEvento.setStart(new EventDateTime().setDateTime(inicio).setTimeZone("America/Argentina/Buenos_Aires"));
                nuevoEvento.setEnd(new EventDateTime().setDateTime(fin).setTimeZone("America/Argentina/Buenos_Aires"));

                //Creamos los recordatorios
                Event.Reminders reminders = new Event.Reminders();
                List<EventReminder> eventsReminders = new ArrayList<>();
                eventsReminders.add(new EventReminder().setMethod("popup").setMinutes(1440));
                reminders.setUseDefault(false);
                reminders.setOverrides(eventsReminders);

                nuevoEvento.setReminders(reminders);

                //Creamos el evento
                Calendar.Events eventsService = client.events();
                Event eventoCreado = eventsService.insert("primary", nuevoEvento).execute();
                programacionTripulante.setEventoCalendario(guardarNuevoEvento(eventoCreado));

                programacionTripulanteRepository.save(programacionTripulante);
                logger.info("Evento creado: " + eventoCreado.getHtmlLink());
            }catch(Exception ex){
                logger.error("Error al crear evento: ", ex);
            }
        }
        return true;
    }

    private EventoCalendario guardarNuevoEvento(Event nuevoEvento) {
        EventoCalendario eventoCalendario  = EventoCalendario.builder()
                .eventId(nuevoEvento.getId())
                .summary(nuevoEvento.getSummary())
                .fechaInicio(nuevoEvento.getStart().getDateTime().toString())
                .fechaFin(nuevoEvento.getEnd().getDateTime().toString())
                .build();

        return this.eventoCalendarioRepository.save(eventoCalendario);
    }

    @Override
    public void eliminarEventosProgramacionesTripulantes(Calendar client, List<ProgramacionTripulante> programacionTripulantes){
        EventoCalendario eventoActual = null;
        for(ProgramacionTripulante pr : programacionTripulantes) {
            if (pr.getEventoCalendario() != null) {
                eliminarEventoEnElCalendario(client, pr.getEventoCalendario().getEventId());
                this.eventoCalendarioRepository.deleteById(pr.getEventoCalendario().getId());

            }
        }
    }

    @Override
    public void eliminarEventoEnElCalendario(Calendar client, String eventId){
        try {
            client.events().delete("primary",eventId).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Event deleted successfully");
    }

    @Override
    public Set<Event> getEvents() throws IOException {
        return this.events;
    }

    @Override
    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    @Override
    public Boolean verificarUsuario(String mail, User user) throws GoogleUserNotAllowedException {
        if(!user.getMail().equalsIgnoreCase(mail))
            throw new GoogleUserNotAllowedException("Error: this user is not valid for your current user");

        return true;
    }
}
