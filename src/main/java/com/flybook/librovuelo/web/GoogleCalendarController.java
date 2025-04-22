package com.flybook.librovuelo.web;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;


import com.flybook.librovuelo.model.User;
import com.flybook.librovuelo.model.UserCredentials;
import com.flybook.librovuelo.service.UserCredentialsService;
import com.flybook.librovuelo.service.UserService;
import com.google.api.services.calendar.model.EventDateTime;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets.Details;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar.Events;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;

@Controller
public class GoogleCalendarController {
        /*
        @Autowired
        private UserCredentialsService userCredentialsService;

        @Autowired
        private UserService userService;


        private final static Log logger = LogFactory.getLog(GoogleCalendarController.class);
        private static final String APPLICATION_NAME = "";
        private static HttpTransport httpTransport;
        private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
        private static com.google.api.services.calendar.Calendar client;

        GoogleClientSecrets clientSecrets;
        GoogleAuthorizationCodeFlow flow;
        Credential credential;

        @Value("${google.client.id}")
        private String clientId;
        @Value("${google.client.secret}")
        private String clientSecret;
        @Value("${google.redirect.uri}")
        private String redirectURI;

        private Set<Event> events = new HashSet<>();

        final DateTime date1 = new DateTime("2017-05-05T16:30:00.000+05:30");
        final DateTime date2 = new DateTime(new Date());

        public void setEvents(Set<Event> events) {
            this.events = events;
        }

        @RequestMapping(value = "/login/google", method = RequestMethod.GET)
        public RedirectView googleConnectionStatus(HttpServletRequest request) throws Exception {
            return new RedirectView(authorize());
        }

        @RequestMapping(value = "/login/oauth2/code/google", method = RequestMethod.GET, params = "code")
        public String oauth2Callback(@RequestParam(value = "code") String code) {
            com.google.api.services.calendar.model.Events eventList;
            String message;
            try {
                TokenResponse response = flow.newTokenRequest(code).setRedirectUri(redirectURI).execute();
                //Guardamos las credenciales y el client
                credential = flow.createAndStoreCredential(response, "userID");
                client = new com.google.api.services.calendar.Calendar.Builder(httpTransport, JSON_FACTORY, credential)
                        .setApplicationName(APPLICATION_NAME).build();

                //Guardamos las credenciales
                guardarCrendencial();

                //Obtenemos los eventos desde la fecha 1 y fecha 2
                Events events = client.events();
                eventList = events.list("primary").setTimeMin(date1).setTimeMax(date2).execute();

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

            return "redirect:/tripulante/programacion-tripulantes";
        }


    /**
     * Crear el evento
     * @return

    @RequestMapping(value="/crear-evento", method = RequestMethod.GET)
        public ResponseEntity<String> crearEvento(){
            try{
                Event nuevoEvento = new Event()
                        .setSummary("Nuevo evento")
                        .setLocation("Argentina")
                        .setDescription("Hola este es un evento");
                Calendar calendarInicio = Calendar.getInstance();
                calendarInicio.set(2025, Calendar.FEBRUARY, 11, 9, 0, 0);
                DateTime inicio = new DateTime(calendarInicio.getTime());

                Calendar calendarFin = Calendar.getInstance();
                calendarFin.set(2025, Calendar.FEBRUARY, 11, 17, 0, 0);
                DateTime fin = new DateTime(calendarFin.getTime());

                nuevoEvento.setStart(new EventDateTime().setDateTime(inicio).setTimeZone("America/Argentina/Buenos_Aires"));
                nuevoEvento.setEnd(new EventDateTime().setDateTime(fin).setTimeZone("America/Argentina/Buenos_Aires"));

                Events eventsService = client.events();
                Event eventoCreado = eventsService.insert("primary", nuevoEvento).execute();

                return new ResponseEntity<>("Evento creado: " + eventoCreado.getHtmlLink(), HttpStatus.OK);
            }catch(Exception ex){
                logger.error("Error al crear evento: ", ex);
                return new ResponseEntity<>("Error al crear el evento", HttpStatus.INTERNAL_SERVER_ERROR);

            }
        }
        private void guardarCrendencial(){

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findByUsername(auth.getName());

        // Extraer información del credential
            String accessToken = credential.getAccessToken();
            String refreshToken = credential.getRefreshToken();  // Puede ser null si no se proporciona un refresh token
            LocalDate tokenExpiration = credential.getExpirationTimeMilliseconds() != null ?
                    new Date(credential.getExpirationTimeMilliseconds()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate(): null;

            UserCredentials userCredentials = UserCredentials.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .tokenExpiration(tokenExpiration)
                    .user(user)
                    .createDate(LocalDate.now())
                    .build();
            this.userCredentialsService.save(userCredentials);

        }

        /*
        private void recuperarCredenciales(){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findByUsername(auth.getName());

            UserCredentials userCredentials = userCredentialsService.getUserCredentialsByUser(user);
            if(userCredentials != null){
                this.credential = new Credential.Builder()
                        .setAccessToken(userCredentials.getAccessToken())
                        .setRefreshToken(userCredentials.getRefreshToken())
                        .setExpirationTimeMilliseconds(userCredentials.getTokenExpiration().atStartOfDay(
                                ZoneId.systemDefault()
                        )
                        .toInstant()
                        .toEpochMilli());
                        .build();
            }
        }

        public Set<Event> getEvents() throws IOException {
            return this.events;
        }

        private String authorize() throws Exception {
            AuthorizationCodeRequestUrl authorizationUrl;
            if (flow == null) {
                Details web = new Details();
                web.setClientId(clientId);
                web.setClientSecret(clientSecret);
                clientSecrets = new GoogleClientSecrets().setWeb(web);
                httpTransport = GoogleNetHttpTransport.newTrustedTransport();
                flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets,
                        Collections.singleton(CalendarScopes.CALENDAR)).build();
            }
            authorizationUrl = flow.newAuthorizationUrl().setRedirectUri(redirectURI);
            System.out.println("cal authorizationUrl->" + authorizationUrl);

            return authorizationUrl.build();

        }*/

}
