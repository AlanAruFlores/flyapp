package com.flybook.librovuelo.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
public class TripulanteRecurrent {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Recurrent recurrent;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User tripulante;

    private Boolean asignado=true;

}
