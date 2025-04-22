package com.flybook.librovuelo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flybook.librovuelo.model.Documentacion;
import com.flybook.librovuelo.model.User;
import com.flybook.librovuelo.service.AdjuntoDocumentacionService;
import com.flybook.librovuelo.service.DocumentacionService;
import com.flybook.librovuelo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


// @RunWith(MockitoJUnitRunner.class) // JUnit 4
// @ExtendWith(MockitoExtension.class) for JUnit 5
@SpringBootTest
@AutoConfigureMockMvc
public class DocumentacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DocumentacionService documentacionService;

    @MockBean
    private AdjuntoDocumentacionService adjuntoDocumentacionService;

    @MockBean
    private UserService userService;

    @Autowired
    protected WebApplicationContext wac;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

        private Documentacion crearDoc() {
        User user = new User();
        user.setNombre("diego");
        user.setId(1L);
        Documentacion doc = new Documentacion();
        doc.setId(1L);
        doc.setTitulo("hola");
        doc.setComentario("re");
        doc.setFechaDeCreacion(LocalDate.now());
        doc.setFechaDeVencimiento(LocalDate.of(2023, 05, 04));
        doc.setUser(user);
        return doc;
    }

    private List<Documentacion> crearDocs() {
        User user = new User();
        user.setNombre("diego");

        List<Documentacion> lista = new ArrayList<>();

        Documentacion doc = new Documentacion();
        doc.setTitulo("hola");
        doc.setUser(user);

        Documentacion doc2 = new Documentacion();
        doc2.setTitulo("holaas");
        doc2.setUser(user);

        lista.add(doc);
        lista.add(doc2);

        return lista;
    }

    @Test
    @WithMockUser(username = "username", password = "pass", authorities = {
            "ROLE_TRIPULANTE" })
    public void mostrarDocumentacionesDelTripulante() throws Exception {
            User user = new User();
            user.setId(1L);
            user.setNombre("diego");

        when(documentacionService.findAllByUsername
                (Sort.by(Sort.Direction.DESC, "fechaDeCreacion"), user)) // Collections.singletonList(crearDoc()) se usa para devolver una lista inmutable que contiene solo el objeto especificado.
                .thenReturn(crearDocs());
        this.mockMvc.perform(get("/tripulante/midocumentacion")
                .contentType("application/json") // El cuerpo del tipo de contenido es de tipo JSON.
                .param("params", "true")
                .content(objectMapper.writeValueAsString(crearDoc()))) // Estamos convirtiendo el objeto de cliente de Java a JSON.
                .andDo(print())
                .andExpect(status().isOk()); // Esperamos un código de estado 200 (= OK).


        /*verify(documentacionService, times(1))
                .findAllByUsername(Sort.by(Sort.Direction.DESC, "fechaDeCreacion"), user);*/
        //verifyNoMoreInteractions(documentacionService);
    }

    @Test
    @WithMockUser(username = "username", password = "pass", authorities = {
            "ROLE_LIDER" })
    public void mostrarDocumentacionesDeTripulantesAsociadosAUnLider() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setNombre("diego");

        when(documentacionService.findAllByUsername
                (Sort.by(Sort.Direction.DESC, "fechaDeCreacion"), user)) // Collections.singletonList(crearDoc()) se usa para devolver una lista inmutable que contiene solo el objeto especificado.
                .thenReturn(crearDocs());
        this.mockMvc.perform(get("/lider/documentaciones")
                        .contentType("application/json") // El cuerpo del tipo de contenido es de tipo JSON.
                        .param("params", "true")
                        .content(objectMapper.writeValueAsString(crearDoc()))) // Estamos convirtiendo el objeto de cliente de Java a JSON.
                .andDo(print())
                .andExpect(status().isOk()); // Esperamos un código de estado 200 (= OK).

    }

    @Test
    @WithMockUser(username = "username", password = "pass")
    public void registrarDocumentacion() throws Exception {

        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "photo.jpeg",
                MediaType.IMAGE_JPEG_VALUE,
                "photo".getBytes()
        );
        // Mockito.doThrow(new Exception()).when(instance).methodName();
        doNothing().when(documentacionService).save(crearDoc());
        mockMvc.perform(multipart("/documentacion/registrar-documentacion")
                .file(file)
                .param("file", "photo.jpeg")
                .content(objectMapper.writeValueAsString(crearDoc()))
                .contentType(MediaType.APPLICATION_JSON)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .accept(MediaType.APPLICATION_JSON)
                .accept(MediaType.MULTIPART_FORM_DATA))
                .andDo(print())
                .andExpect(redirectedUrl("/midocumentacion"));

        verify(documentacionService).save(any(Documentacion.class));
    }
}

    // @Bean: No hay una repuesta clara. Pero a grosso modo puede entenderse como una anotación que marca cada uno
    // de los métodos de tal forma que estén disponibles para el framework (en general).

    // @Mock: Solo se usa en clases de prueba. Permite crear un objeto simulado de una clase o interfaz.
    // Eso hace que sea más fácil encontrar el simulacro del problema en caso de que falle, ya que el nombre
    // del campo aparece en el mensaje del fallo de ejecución.

    // @MockBean: Agregar objetos simulados al contexto de la aplicación Spring.
    // El simulacro reemplazará cualquier bean existente del mismo tipo en el contexto de la aplicación.
    // Si no se defino uno del mismo tipo, se agrega uno nuevo. Útil en pruebas de integración en las que es
    // necesario burlar al bean en particular.

    // Conclusión: Aquellas propiedades creadas con @Mock, quiere decir que dentro de su clase
    // NO HABRÁN CAMBIOS EN SU ESTADO. Esto último es muy importante debido a que se instancia a una clase un objeto
    // simulado, esto lleva a que no vas a cambiar algunas de sus propiedades más internas o a servicios externos
    // a ellos (como una base de datos).
    // Y por último el @MockBean generaliza lo anterior, por lo que se inducen cambios en el estado
    // en el interior del objeto o bien en algún servicio externo a este que esté relacionado de forma interna,
    // por ejemplo una base de datos.

// Los ejemplos de código en este tutorial usan la @ExtendWithanotación para decirle a JUnit 5 que habilite la
// compatibilidad con Spring. A partir de Spring Boot 2.1 , ya no es necesario cargar SpringExtension porque se
// incluye como metaanotación en las anotaciones de prueba de Spring Boot como @DataJpaTest, @WebMvcTest y @SpringBootTest.