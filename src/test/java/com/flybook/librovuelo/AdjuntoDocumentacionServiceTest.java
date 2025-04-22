package com.flybook.librovuelo;

import com.flybook.librovuelo.repository.AdjuntoDocumentacionRepository;
import com.flybook.librovuelo.service.AdjuntoDocumentacionService;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

//@WebMvcTest
/*@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AdjuntoDocumentacionServiceTest {

    @InjectMocks
    private AdjuntoDocumentacionService adjuntoDocumentacionService;

    @Mock
    private AdjuntoDocumentacionRepository adjuntoDocumentacionRepository;

    /*private AdjuntoDocumentacion crearAdjunto() {
        AdjuntoDocumentacion adj = new AdjuntoDocumentacion();
        adj.setNombre("hola");
        adj.setFecha(LocalDate.now());
        return adj;
    }*/

    /*@Test
    void should_find_and_return_all_student() {
        // Arrange
        when(adjuntoDocumentacionRepository.findAll()).thenReturn(List.of(new AdjuntoDocumentacion(), new AdjuntoDocumentacion()));

        // Act & Assert
        assertThat(adjuntoDocumentacionService.findAll()).
        verify(adjuntoDocumentacionRepository, times(1)).findAll();
        verifyNoMoreInteractions(adjuntoDocumentacionRepository);
    }

    @Test
    void queSePuedaObtenerUnObjetoAdjuntoPorId(){
        Optional<AdjuntoDocumentacion> optAdj = Optional.of(crearAdjunto());

        when(adjuntoDocumentacionRepository.findById(1L)).thenReturn(optAdj);

        assert adjuntoDocumentacionServiceImpl.findById(1L).get().getNombre().contains("hola");
    }
}*/

// SpringBootTest: Nos va a crear un contenedor de nuestra aplicación creando un ApplicationContext.

// ExtendWith para Junit5: Para habilitar el soporte para poder hacer uso de JUnit 5.

// AutoconfigureMockMvc: Nos permitirá hacer uso de MockMvc para nuestros test integrados,
// de manera que podamos realizar peticiones HTTP.

// @WebMvcTest: Incluye tanto el @AutoConfigureWebMvc como el @AutoConfigureMockMvc, entre otras funcionalidades.@Mockbean

// La principal diferencia entre las anotaciones @SpringBootTest y @WebMvcTest es que la anotación @SpringBootTest iniciará
// el contexto completo de la aplicación. Mientras que la anotación @WebMvcTest hará que Spring Framework cree un contexto
// de aplicación con una cantidad limitada de beans (solo aquellos relacionados con la capa web).

// Utilizará la anotación @SpringBootTest para crear pruebas de integración que involucren las tres capas de su aplicación
// (es decir , capa web, de servicio y de datos ). Y utilizará la anotación @WebMvcTest cuando necesite crear pruebas
// de integración o pruebas unitarias de la capa Web MVC únicamente (es decir, controladores). Porque al usar @WebMvcTest ,
// será necesario simular las dependencias de anotación en la capa de servicio o datos.