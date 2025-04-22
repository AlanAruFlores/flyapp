package com.flybook.librovuelo.dto;

import java.util.Comparator;

public class OrdenPeriodoAnioDesendiente implements Comparator <Integer>{
    @Override
    public int compare(Integer o1, Integer o2) {
        return o2.compareTo(o1);
    }
}
