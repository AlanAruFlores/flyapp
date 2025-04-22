package com.flybook.librovuelo.exceptions;

public class HoraDespegueException extends Exception {
    public HoraDespegueException() {
        super("El aterrizaje debe ser posterior al despegue.");
    }
}
