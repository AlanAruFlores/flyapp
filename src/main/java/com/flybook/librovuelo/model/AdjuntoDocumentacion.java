package com.flybook.librovuelo.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class AdjuntoDocumentacion {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Documentacion documentacion;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDate fecha;

    @Column(length = 500)
    private String  nombre;

    @Column(length = 5)
    private String extension;

    private Long tamanio;

    private String path;

    /*
    @Lob
    @Column(length = 20971520)
    private byte[] data;
*/

    public AdjuntoDocumentacion(){}

}
