package com.flybook.librovuelo.dto;

import com.flybook.librovuelo.model.PedidoDiasLibres;

import java.util.Comparator;

public class OrdenPedidoDiasLibres implements Comparator<PedidoDiasLibres> {
    @Override
    public int compare(PedidoDiasLibres o1, PedidoDiasLibres o2) {
        return o2.getFechaSolicitud().compareTo(o1.getFechaSolicitud());
    }
}
