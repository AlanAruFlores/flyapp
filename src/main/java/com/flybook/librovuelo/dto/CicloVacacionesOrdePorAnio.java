package com.flybook.librovuelo.dto;

import com.flybook.librovuelo.model.CicloVacaciones;

import java.util.Comparator;

public class CicloVacacionesOrdePorAnio implements Comparator<CicloVacaciones> {
    @Override
    public int compare(CicloVacaciones o1, CicloVacaciones o2) {
        return o2.getNumeroDeCiclo().compareTo(o1.getNumeroDeCiclo());

    }
}