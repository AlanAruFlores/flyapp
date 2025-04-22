package com.flybook.librovuelo;

import com.flybook.librovuelo.model.AdjuntoDocumentacion;
import com.flybook.librovuelo.model.Documentacion;
import com.flybook.librovuelo.repository.AdjuntoDocumentacionRepository;
import com.flybook.librovuelo.repository.DocumentacionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

// @DataJpaTest: Anotación para una prueba de JPA que se enfoca solo en los componentes de JPA.
// El uso de esta anotación deshabilitará la configuración automática completa y, en su lugar,
// aplicará solo la configuración relevante para las pruebas de JPA.
// De forma predeterminada, las pruebas anotadas con @DataJpaTestson transaccionales y retroceden al final
// de cada prueba. También utilizan una base de datos incrustada en la memoria (reemplazando cualquier
// DataSource explícito o generalmente autoconfigurado). La @AutoConfigureTestDatabaseanotación se puede
// utilizar para anular esta configuración.
@DataJpaTest
public class AdjuntoDocumentacionRepositoryTest {

    @Autowired
    private AdjuntoDocumentacionRepository adjuntoDocumentacionRepository;

    @Autowired
    private DocumentacionRepository documentacionRepository;

    @Test
    void queSePuedaObtenerUnAdjuntoPorDocumentacionId() {
        Documentacion doc = new Documentacion();

        AdjuntoDocumentacion ad = new AdjuntoDocumentacion();
        ad.setDocumentacion(doc);
        adjuntoDocumentacionRepository.save(ad);

        Documentacion doc2 = new Documentacion();

        AdjuntoDocumentacion ad2 = new AdjuntoDocumentacion();
        ad2.setDocumentacion(doc2);
        adjuntoDocumentacionRepository.save(ad2);

        AdjuntoDocumentacion adEsperado = adjuntoDocumentacionRepository.findAdjuntoDocumentacionByDocumentacionId(doc.getId());

        assertNotNull(adEsperado);
        assertEquals(adEsperado, ad);
    }

    @Test
    public void queSePuedaGuardarUnAdjunto() {
        AdjuntoDocumentacion ad = new AdjuntoDocumentacion();
        adjuntoDocumentacionRepository.save(ad);
        assertNotNull(ad);
    }
}
