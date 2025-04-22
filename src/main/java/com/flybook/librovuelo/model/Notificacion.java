package com.flybook.librovuelo.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensaje;


    @Enumerated(EnumType.STRING)
    private TipoNotificacion tipo;

    @ManyToOne
    private User remitente;

    @ManyToOne
    private User user;

    private String action;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaEnvio;

    private Long idVueloRealizado;

    private Boolean leida;


}
