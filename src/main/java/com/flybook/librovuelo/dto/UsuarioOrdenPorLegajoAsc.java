package com.flybook.librovuelo.dto;

import com.flybook.librovuelo.model.User;

import java.util.Comparator;

public class UsuarioOrdenPorLegajoAsc implements Comparator<User> {
    @Override
    public int compare(User o1, User o2) {
        return o1.getLegajo().compareTo(o2.getLegajo());
    }
}