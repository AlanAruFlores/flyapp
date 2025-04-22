package com.flybook.librovuelo.exceptions;

public class TiempoVueloMayorA20HoraasException extends Exception {
    public TiempoVueloMayorA20HoraasException() {
        super("El tiempo de vuelo excede las 20 hs.");
    }
}
