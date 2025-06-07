package com.grupo2.clinicasalud.model;

public enum Genero {
    masculino,
    femenino,
    otro;

    public String toString(){
        return switch (this) {
            case masculino -> "M";
            case femenino -> "F";
            case otro -> "O";
            default -> "";
        };
    };
}
