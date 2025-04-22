package com.flybook.librovuelo.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
public class Vestimenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    private User user;
    private Integer talleZapatos;
    private Integer talleChaqueta;
    private Integer talleCamisa;
    private Integer tallePantalon;
    private Integer talleFalda;
    private Integer talleMedias;

    private String comentario;


}
