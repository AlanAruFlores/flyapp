package com.flybook.librovuelo.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flybook.librovuelo.dto.ProgramacionTripulanteDTO;
import com.flybook.librovuelo.model.*;
import com.flybook.librovuelo.service.*;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Controller
public class ProgramacionTripulantesController {

    @Autowired
    private ProgramacionTripulanteService programacionTripulanteService;

    @Autowired
    private AeropuertoService aeropuertoService;

    @Autowired
    private VueloService vueloService;

    @Autowired
    private UserService userService;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private JacksonAutoConfiguration jacksonAutoConfiguration;

    @Autowired
    private UserCredentialsService userCredentialsService;

    @Autowired
    private CalendarService calendarService;


    /*
    * Google Calendar
    * */
    private final static Log logger = LogFactory.getLog(ProgramacionTripulantesController.class);
    private static final String APPLICATION_NAME = "";
    //private static HttpTransport httpTransport;
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
   // private static com.google.api.services.calendar.Calendar client;

    GoogleClientSecrets clientSecrets;
    GoogleAuthorizationCodeFlow flow;
    //Credential credential;

    @Value("${google.client.id}")
    private String clientId;
    @Value("${google.client.secret}")
    private String clientSecret;
    @Value("${google.redirect.uri}")
    private String redirectURI;

    /**
     * Ir a la vista
     */
    @GetMapping("/tripulante/programacion-tripulantes")
    public String irAProgramacionTripulantes(Model model, @RequestParam Map<String, Object> params)  {

        /* Si no tiene las credenciales de OAuth le manda a la doble autenticacion de Google
        if(credential == null)
             return "redirect:/login/google";
         */
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User usuario = this.userService.findByUsername(auth.getName());


        List<ProgramacionTripulante> listaProgramacionTripulante;

        if(!params.isEmpty()){
            listaProgramacionTripulante  = this.programacionTripulanteService.filtrarPorFechasProgramacionTripulante(params, usuario);
        }else{
            listaProgramacionTripulante = this.programacionTripulanteService.getProgramacionTripulanteByTripulante(usuario);
        }

        model.addAttribute("listaProgramacionTripulante", listaProgramacionTripulante);
        return "programacion-tripulantes";
    }

    @GetMapping("/tripulante/excel-programacion-tripulantes")
    public String irAExcelProgramacionTripulantes(Model model) throws JsonProcessingException {
        List<Aeropuerto> aeropuertos = aeropuertoService.findAll();
        List<TipoActividad> actividades = List.of(TipoActividad.values());
        List<Vuelo> vuelos = vueloService.findAll();
        ObjectMapper jackson = new ObjectMapper();

        String name  = SecurityContextHolder.getContext().getAuthentication().getName();
        String password = userService.findByUsername(name).getPassword();


        model.addAttribute("vuelos", jackson.writeValueAsString(vuelos));
        model.addAttribute("aeropuertos", jackson.writeValueAsString(aeropuertos));
        model.addAttribute("actividades", jackson.writeValueAsString(actividades));
        model.addAttribute("username", name);
        model.addAttribute("password", password);

        model.addAttribute("actividades", jackson.writeValueAsString(actividades));

        return "excel-programacion-tripulantes";
    }


    @PostMapping("/tripulantes/guardar-cambios")
    public ResponseEntity<String> guardarCambiosExcel(@RequestBody List<List<Object>> datosModificados){
        System.out.println("Datos recibidos: " + datosModificados);


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User usuario = this.userService.findByUsername(auth.getName());

        List<ProgramacionTripulanteDTO> listDtoProgramacion = this.programacionTripulanteService.convertirAListaProgramacionTripulanteDTO(datosModificados);
        List<ProgramacionTripulante> listProgTripulante = this.programacionTripulanteService.convertirListaDtosAListaProgramacionTripulanteConUsuario(listDtoProgramacion,usuario);
        this.programacionTripulanteService.saveListaProgramacionTripulantes(listProgTripulante);

        //Agregamos al calendario
        //this.calendarService.crearEvento(client,listDtoProgramacion);
       // this.calendarService.actualizarCalendario(client,listProgTripulante);
        return ResponseEntity.ok("Recibido");
    }

    //Agregar al calendario
    @GetMapping("/tripulantes/actualizar-calendario")
    public String actualizarProgramacionTripulanteAlCalendario(RedirectAttributes redirectAttributes) throws GeneralSecurityException, IOException {

        //Agregamos al calendario
        User user = this.userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if(this.userCredentialsService.getUserCredentialsByUser(user) == null)
            return "redirect:/login/google";

        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        Credential credential = this.userCredentialsService.retrieveCredential(user);

        if(credential.getExpirationTimeMilliseconds() <= LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond()) {
            this.userCredentialsService.deleteByUser(user);
            return "redirect:/login/google";
        }

        Calendar client = new Calendar.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();

        List<ProgramacionTripulante> programacionTripulantes = this.programacionTripulanteService.getProgramacionTripulanteByTripulante(user);
        this.calendarService.eliminarEventosProgramacionesTripulantes(client, programacionTripulantes);
        this.calendarService.actualizarCalendario(client,programacionTripulantes);
        //programacionTripulanteService.saveProgramacionTripulante(programacionTripulante);
        redirectAttributes.addFlashAttribute("agregadoCalendario", true);
        return "redirect:/tripulante/programacion-tripulantes";
    }



    //Eliminar programacion tripulantes
    @GetMapping("/tripulantes/eliminar-programacion-tripulante/{id}")
    public String eliminarProgramacionTripulantes(@PathVariable("id")Long id, RedirectAttributes redirectAttributes) throws GeneralSecurityException, IOException {
        User user = this.userService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

        if(this.userCredentialsService.getUserCredentialsByUser(user) == null)
            return "redirect:/login/google";

        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        Credential credential = this.userCredentialsService.retrieveCredential(user);

        if(credential.getExpirationTimeMilliseconds() <= LocalDateTime.now().atZone(ZoneId.systemDefault()).toEpochSecond()) {
            this.userCredentialsService.deleteByUser(user);
            return "redirect:/login/google";
        }

        Calendar client = new Calendar.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();

        ProgramacionTripulante programacionTripulante  = programacionTripulanteService.getProgramacionTripulanteById(id);

        //Eliminamos primero el evento
        this.calendarService.eliminarEventoEnElCalendario(client, programacionTripulante.getEventoCalendario().getEventId());

        //Eliminamos la programacionTripulante
        this.programacionTripulanteService.deleteProgramacionTripulantes(id);
        redirectAttributes.addFlashAttribute("eliminado", true);
        return "redirect:/tripulante/programacion-tripulantes";
    }

    //Edicion de programacion tripulantes
    @GetMapping("/tripulantes/edicion-programacion-tripulantes/{id}")
    public String edicionProgramacionTripulantes(@PathVariable("id") Long id, Model model){
        ProgramacionTripulante programacionTripulante = this.programacionTripulanteService.getProgramacionTripulanteById(id);
        ProgramacionTripulanteDTO programacionTripulanteDTO = this.programacionTripulanteService.convertirProgramacionTripulanteASuDTO(programacionTripulante);
        TipoActividad[] tipoActividades = TipoActividad.values();
        List<Aeropuerto> aeropuertos = aeropuertoService.findAll();

        model.addAttribute("programacionTripulante", programacionTripulanteDTO);
        model.addAttribute("tipoActividades", tipoActividades);
        model.addAttribute("aeropuertos", aeropuertos);
        return "edicion-programacion-tripulantes";
    }

    @PostMapping("/tripulantes/editar-programacion-tripulantes")
    public String editarProgramacionTripulantes(@ModelAttribute("programacionTripulante")ProgramacionTripulanteDTO programacionTripulanteDTO, RedirectAttributes redirectAttributes){
        ProgramacionTripulante programacionTripulante = this.programacionTripulanteService.convertirProgramacionTripulanteDTOASuEntidad(programacionTripulanteDTO);
        this.programacionTripulanteService.saveProgramacionTripulante(programacionTripulante);
        redirectAttributes.addFlashAttribute("editado", true);
        return "redirect:/tripulante/programacion-tripulantes";
    }



    /*
        Actions del Google Calendar
     */


    @RequestMapping(value = "/login/google", method = RequestMethod.GET)
    public RedirectView googleConnectionStatus(HttpServletRequest request) throws Exception {
        return new RedirectView(authorize());
    }

    @RequestMapping(value = "/login/oauth2/code/google", method = RequestMethod.GET, params = "code")
    public String oauth2Callback(@RequestParam(value = "code") String code, RedirectAttributes redirectAttributes) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());


        com.google.api.services.calendar.model.Events eventList;
        String message;
        try {
            TokenResponse response = flow.newTokenRequest(code).setRedirectUri(redirectURI).execute();
            //Guardamos las credenciales y el client

            Credential credential = flow.createAndStoreCredential(response, "userID");
            HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

            Calendar client = new com.google.api.services.calendar.Calendar.Builder(httpTransport, JSON_FACTORY, credential)
                    .setApplicationName(APPLICATION_NAME).build();

            //Guardamos las credenciales
            this.userCredentialsService.save(user, credential);

            //Obtenemos los eventos desde la fecha 1 y fecha 2
            final DateTime desde = new DateTime("2017-05-05T16:30:00.000+05:30");
            final DateTime hasta = new DateTime(new Date());

            Calendar.Events events = client.events();
            eventList = events.list("primary").setTimeMin(desde).setTimeMax(hasta).execute();

            //Obtenemos lso eventos en un string
            message = eventList.getItems().toString();
            System.out.println("My:" + eventList.getItems());
        } catch (Exception e) {
            logger.warn("Exception while handling OAuth2 callback (" + e.getMessage() + ")."
                    + " Redirecting to google connection status page.");
            message = "Exception while handling OAuth2 callback (" + e.getMessage() + ")."
                    + " Redirecting to google connection status page.";
        }

        System.out.println("cal message:" + message);
        // return new ResponseEntity<>(message, HttpStatus.OK);

        redirectAttributes.addFlashAttribute("sincronizado", true);
        return "redirect:/tripulante/programacion-tripulantes";
    }

    private String authorize() throws  Exception {
        AuthorizationCodeRequestUrl authorizationUrl;
        if (flow == null) {
            GoogleClientSecrets.Details web = new GoogleClientSecrets.Details();
            web.setClientId(clientId);
            web.setClientSecret(clientSecret);
            clientSecrets = new GoogleClientSecrets().setWeb(web);
            HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
            flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets,
                    Collections.singleton(CalendarScopes.CALENDAR)).build();
        }
        authorizationUrl = flow.newAuthorizationUrl().setRedirectUri(redirectURI);
        System.out.println("cal authorizationUrl->" + authorizationUrl);

        return authorizationUrl.build();

    }





}

