package com.flybook.librovuelo.web;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DatosTripulanteRecurrent {

    private Long idRecurrent;
    private List<Long> idsTripulantes;

}
